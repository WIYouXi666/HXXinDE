package HuanXiangLIB.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.IntSeq;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.core.Renderer;
import mindustry.entities.TargetPriority;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.input.Placement;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class 物品节点 extends Block {

    private static BuildPlan otherReq;

    public final int timerCheckMoved = timers++;

    public int range;
    public float transportTime = 2f;
    public TextureRegion endRegion;
    public TextureRegion bridgeRegion;
    public TextureRegion arrowRegion;

    public boolean fadeIn = true;
    public boolean pulse = false;
    public float arrowTimeScl = 6.2f;
    public float bridgeWidth = 6.5f;

    //for autolink
    public @Nullable sdfsf lastBuild;

    public 物品节点(String name) {
        super(name);
        update = true;
        solid = true;
        underBullets = true;
        hasPower = true;
        itemCapacity = 10;
        configurable = true;
        hasItems = true;
        unloadable = false;
        group = BlockGroup.transportation;
        noUpdateDisabled = true;
        copyConfig = false;
        //disabled as to not be annoying
        allowConfigInventory = false;
        priority = TargetPriority.transport;

        //point2 config is relative
        config(Point2.class, (sdfsf tile, Point2 i) -> tile.link = Point2.pack(i.x + tile.tileX(), i.y + tile.tileY()));
        //integer is not
        config(Integer.class, (sdfsf tile, Integer i) -> tile.link = i);
    }

    @Override
    public void load() {
        super.load();
        this.endRegion = Core.atlas.find(this.getContentType().name() + "-" + this.name + "-end");
        this.bridgeRegion = Core.atlas.find(this.getContentType().name() + "-" + this.name + "-bridge");
        this.arrowRegion = Core.atlas.find(this.getContentType().name() + "-" + this.name + "-arrow");
    }


    @Override
    public void drawPlanConfigTop(BuildPlan plan, Eachable<BuildPlan> list) {
        otherReq = null;
        list.each(other -> {
            if (other.block == this && plan != other && plan.config instanceof Point2 p && p.equals(other.x - plan.x, other.y - plan.y)) {
                otherReq = other;
            }
        });

        if (otherReq != null) {
            drawBridge(plan, otherReq.drawx(), otherReq.drawy(), 0);
        }
    }

    public void drawBridge(BuildPlan req, float ox, float oy, float flip) {
        if (Mathf.zero(Renderer.bridgeOpacity)) return;
        Draw.alpha(Renderer.bridgeOpacity);

        Lines.stroke(bridgeWidth);

        Tmp.v1.set(ox, oy).sub(req.drawx(), req.drawy()).setLength(tilesize / 2f);

        Lines.line(bridgeRegion, req.drawx() + Tmp.v1.x, req.drawy() + Tmp.v1.y, ox - Tmp.v1.x, oy - Tmp.v1.y, false);

        Draw.rect(arrowRegion, (req.drawx() + ox) / 2f, (req.drawy() + oy) / 2f, Angles.angle(req.drawx(), req.drawy(), ox, oy) + flip);


        //修改他的连接方式变成范围连接

    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        Drawf.dashCircle(x * Vars.tilesize, y * Vars.tilesize, (range) * Vars.tilesize, Pal.accent);

//        super.drawPlace(x, y, rotation, valid);
//
//        Tile link = findLink(x, y);
//
//        for (int i = 0; i < 4; i++) {
//            Drawf.dashLine(Pal.placing,
//                    x * tilesize + Geometry.d4[i].x * (tilesize / 2f + 2),
//                    y * tilesize + Geometry.d4[i].y * (tilesize / 2f + 2),
//                    x * tilesize + Geometry.d4[i].x * (range) * tilesize,
//                    y * tilesize + Geometry.d4[i].y * (range) * tilesize);
//        }
//
//        Draw.reset();
//        Draw.color(Pal.placing);
//        Lines.stroke(1f);
//        if (link != null && Math.abs(link.x - x) + Math.abs(link.y - y) > 1) {
//            int rot = link.absoluteRelativeTo(x, y);
//            float w = (link.x == x ? tilesize : Math.abs(link.x - x) * tilesize - tilesize);
//            float h = (link.y == y ? tilesize : Math.abs(link.y - y) * tilesize - tilesize);
//            Lines.rect((x + link.x) / 2f * tilesize - w / 2f, (y + link.y) / 2f * tilesize - h / 2f, w, h);
//
//            Draw.rect("bridge-arrow", link.x * tilesize + Geometry.d4(rot).x * tilesize, link.y * tilesize + Geometry.d4(rot).y * tilesize, link.absoluteRelativeTo(x, y) * 90);
//        }
//        Draw.reset();
    }

    public boolean linkValid(Tile tile, Tile other) {
        return linkValid(tile, other, true);
    }

    public boolean linkValid(Tile tile, Tile other, boolean checkDouble) {
        if (other == null || tile == null || other == tile) return false;
        if (Math.pow(other.x - tile.x, 2) + Math.pow(other.y - tile.y, 2) > Math.pow(range + 0.5, 2)) return false;
        return ((other.block() == tile.block() && tile.block() == this) || (!(tile.block() instanceof 物品节点) && other.block() == this)) && (other.build.team == tile.build.team || tile.block() != this) && (!checkDouble || ((sdfsf) other.build).link != tile.pos());
//        if (other == null || tile == null || !positionsValid(tile.x, tile.y, other.x, other.y)) return false;
//
//        return ((other.block() == tile.block() && tile.block() == this) || (!(tile.block() instanceof ItemBridge) && other.block() == this))
//                && (other.team() == tile.team() || tile.block() != this)
//                && (!checkDouble || ((ItemBridge.ItemBridgeBuild) other.build).link != tile.pos());
    }

    public boolean positionsValid(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return Math.abs(y1 - y2) <= range;
        } else if (y1 == y2) {
            return Math.abs(x1 - x2) <= range;
        } else {
            return false;
        }
    }

    public Tile findLink(int x, int y) {
        Tile tile = world.tile(x, y);
        if (tile != null && lastBuild != null && linkValid(tile, lastBuild.tile) && lastBuild.tile != tile && lastBuild.link == -1) {
            return lastBuild.tile;
        }
        return null;
    }

    @Override
    public void init() {
        super.init();
        updateClipRadius((range + 0.5f) * tilesize);
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans) {
        for (int i = 0; i < plans.size - 1; i++) {
            var cur = plans.get(i);
            var next = plans.get(i + 1);
            if (positionsValid(cur.x, cur.y, next.x, next.y)) {
                cur.config = new Point2(next.x - cur.x, next.y - cur.y);
            }
        }
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation) {
        Placement.calculateNodes(points, this, rotation, (point, other) -> Math.max(Math.abs(point.x - other.x), Math.abs(point.y - other.y)) <= range);
    }

    public class sdfsf extends Building {
        public int link = -1;
        public IntSeq incoming = new IntSeq(false, 4);
        public float warmup;
        public float time = -8f, timeSpeed;
        public boolean wasMoved, moved;
        public float transportCounter;

        @Override
        public void pickedUp() {
            link = -1;
        }

        @Override
        public void playerPlaced(Object config) {
            super.playerPlaced(config);

            Tile link = findLink(tile.x, tile.y);
            if (linkValid(tile, link) && this.link != link.pos() && !proximity.contains(link.build)) {
                link.build.configure(tile.pos());
            }

            lastBuild = this;
        }

        @Override
        public void drawSelect() {
            if (linkValid(tile, world.tile(link))) {
                drawInput(world.tile(link));
            }

            incoming.each(pos -> drawInput(world.tile(pos)));

            Draw.reset();
        }

        private void drawInput(Tile other) {
            if (!linkValid(tile, other, false)) return;
            boolean linked = other.pos() == link;

            Tmp.v2.trns(tile.angleTo(other), 2f);
            float tx = tile.drawx(), ty = tile.drawy();
            float ox = other.drawx(), oy = other.drawy();
            float alpha = Math.abs((linked ? 100 : 0) - (Time.time * 2f) % 100f) / 100f;
            float x = Mathf.lerp(ox, tx, alpha);
            float y = Mathf.lerp(oy, ty, alpha);

            Tile otherLink = linked ? other : tile;
            int rel = (linked ? tile : other).absoluteRelativeTo(otherLink.x, otherLink.y);

            //draw "background"
            Draw.color(Pal.gray);
            Lines.stroke(2.5f);
            Lines.square(ox, oy, 2f, 45f);
            Lines.stroke(2.5f);
            Lines.line(tx + Tmp.v2.x, ty + Tmp.v2.y, ox - Tmp.v2.x, oy - Tmp.v2.y);

            //draw foreground colors
            Draw.color(linked ? Pal.place : Pal.accent);
            Lines.stroke(1f);
            Lines.line(tx + Tmp.v2.x, ty + Tmp.v2.y, ox - Tmp.v2.x, oy - Tmp.v2.y);

            Lines.square(ox, oy, 2f, 45f);
            Draw.mixcol(Draw.getColor(), 1f);
            Draw.color();
            Draw.rect(arrowRegion, x, y, rel * 90);
            Draw.mixcol();
        }

        @Override
        public void drawConfigure() {
            float sin = Mathf.absin(Time.time, 6, 1);
            Draw.color(Pal.accent);
            Lines.stroke(1);
            Drawf.circles(this.x, this.y, (block.size / 2f + 1) * Vars.tilesize + sin - 2, Pal.accent);
            Building other = world.build(this.link);
            if (other != null) {
                Drawf.circles(other.x, other.y, (block.size / 3f + 1) * Vars.tilesize + sin - 2, Pal.place);
                Drawf.arrow(this.x, this.y, other.x, other.y, block.size * Vars.tilesize + sin, 4 + sin, Pal.accent);
            }
            Drawf.dashCircle(this.x, this.y, range * Vars.tilesize, Pal.accent);

        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            //reverse connection
            if (other instanceof sdfsf b && b.link == pos()) {
                configure(other.pos());
                other.configure(-1);
                return true;
            }

            if (linkValid(tile, other.tile)) {
                if (link == other.pos()) {
                    configure(-1);
                } else {
                    configure(other.pos());
                }
                return false;
            }
            return true;
        }

        public void checkIncoming() {
            int idx = 0;
            while (idx < incoming.size) {
                int i = incoming.items[idx];
                Tile other = world.tile(i);
                if (!linkValid(tile, other, false) || ((sdfsf) other.build).link != tile.pos()) {
                    incoming.removeIndex(idx);
                    idx--;
                }
                idx++;
            }
        }

        @Override
        public void updateTile() {
            Building aa = Vars.world.build(this.link);
            if (aa != null) {
                if (!((物品节点) this.block).linkValid(this.tile, aa.tile)) {
                    this.link = -1;
                    //return;
                }
            }


            if (timer(timerCheckMoved, 30f)) {
                wasMoved = moved;
                moved = false;
            }

            //smooth out animation, so it doesn't stop/start immediately
            timeSpeed = Mathf.approachDelta(timeSpeed, wasMoved ? 1f : 0f, 1f / 60f);

            time += timeSpeed * delta();

            checkIncoming();

            Tile other = world.tile(link);
            if (!linkValid(tile, other)) {
                doDump();
                warmup = 0f;
            } else {
                var inc = ((sdfsf) other.build).incoming;
                int pos = tile.pos();
                if (!inc.contains(pos)) {
                    inc.add(pos);
                }

                warmup = Mathf.approachDelta(warmup, efficiency, 1f / 30f);
                updateTransport(other.build);
            }
        }

        public void doDump() {
            //allow dumping multiple times per frame
            dumpAccumulate();
        }

        public void updateTransport(Building other) {
            transportCounter += edelta();
            while (transportCounter >= transportTime) {
                Item item = items.take();
                if (item != null && other.acceptItem(this, item)) {
                    other.handleItem(this, item);
                    moved = true;
                } else if (item != null) {
                    items.add(item, 1);
                    items.undoFlow(item);
                }
                transportCounter -= transportTime;
            }
        }

        @Override
        public void draw() {
            Draw.rect(fullIcon, this.x, this.y);
            Draw.z(Layer.power);
            Building other = world.build(this.link);
            if (other == null) return;
            if(Mathf.zero(Renderer.bridgeOpacity)) return;
            if(pulse){
                Draw.color(Color.white, Color.black, Mathf.absin(Time.time, 6f, 0.07f));
            }
            Draw.color(Color.white);
            Draw.alpha((fadeIn ? Math.max(this.power.status, 0.25f):1f) * Renderer.bridgeOpacity);

            Draw.rect(endRegion, this.x, this.y);
            Draw.rect(endRegion, other.x, other.y);

            Lines.stroke(8);

            Tmp.v1.set(this.x, this.y).sub(other.x, other.y).setLength(Vars.tilesize / 2f).scl(-1);

            Lines.line(bridgeRegion, this.x, this.y, other.x, other.y, false);
            Draw.reset();
        }


        @Override
        public boolean acceptItem(Building source, Item item) {
            if (this.team != source.team || !block.hasItems) return false;
            //var other = Vars.world.tile(this.link);
            return /*other != null && this.block.linkValid(this.tile, other) && */this.items.total() < block.itemCapacity;
            // return hasItems && team == source.team && items.total() < itemCapacity && checkAccept(source, world.tile(link));
        }

        @Override
        public boolean canDumpLiquid(Building to, Liquid liquid) {
            return true;
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return hasLiquids && team == source.team && (liquids.current() == liquid || liquids.get(liquids.current()) < 0.2f) && checkAccept(source, world.tile(link));
        }

        protected boolean checkAccept(Building source, Tile other) {
            if (tile == null || linked(source)) return true;

            if (linkValid(tile, other)) {
                int rel = relativeTo(other);
                var facing = Edges.getFacingEdge(source, this);
                int rel2 = facing == null ? -1 : relativeTo(facing);

                return rel != rel2;
            }

            return false;
        }

        protected boolean linked(Building source) {
            return source instanceof sdfsf && linkValid(source.tile, tile) && ((sdfsf) source).link == pos();
        }

        @Override
        public boolean canDump(Building to, Item item) {
            return true;
        }


        @Override
        public boolean shouldConsume() {
            return linkValid(tile, world.tile(link)) && enabled;
        }

        @Override
        public Point2 config() {
            return Point2.unpack(link).sub(tile.x, tile.y);
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(link);
            write.f(warmup);
            write.b(incoming.size);

            for (int i = 0; i < incoming.size; i++) {
                write.i(incoming.items[i]);
            }

            write.bool(wasMoved || moved);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            link = read.i();
            warmup = read.f();
            byte links = read.b();
            for (int i = 0; i < links; i++) {
                incoming.add(read.i());
            }

            if (revision >= 1) {
                wasMoved = moved = read.bool();
            }
        }
    }
}
