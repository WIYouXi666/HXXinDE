package MODS.树;

import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.world;

public class 树根 extends Block {

    public int 主枝干数 = 4;
    public int 枝干总数 = 25;
    public Block 主干;
    public Block 枝干;
    public Block 附属物;
    public float 生长速度 = 80;
    public float 弯 = 0.25f;
    public DrawBlock drawer = new DrawDefault();
    public Effect updateEffect = Fx.none;
    public Effect craftEffect = Fx.rocketSmokeLarge;
    public float updateEffectChance = 0.04f;
    public float warmupSpeed = 0.019f;
    public float 生长几率 = 0.25f;

    public 树根(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        ambientSound = Sounds.machine;
        sync = true;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.factory);
        drawArrow = false;
    }

    @Override
    public void setStats() {
        stats.timePeriod = 生长速度;
        super.setStats();
        if ((hasItems && itemCapacity > 0)) {
            stats.add(Stat.productionTime, 生长速度 / 60f, StatUnit.seconds);
        }

    }

    @Override
    public void setBars() {
        super.setBars();

    }

    @Override
    public boolean rotatedOutput(int x, int y) {
        return false;
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void init() {
        主枝干数 = Math.max(Math.min(主枝干数, 4 * size + 4), 0);

        super.init();
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons() {
        return drawer.finalIcons(this);
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out) {
        drawer.getRegionsToOutline(this, out);
    }


    public class GenericCrafterBuild extends Building {
        public float progress;
        public float totalProgress;
        public float warmup;
        Seq<Tile> 主干表 = new Seq<>();
        Seq<Tile> 枝干表 = new Seq<>();
        float 生长几率s = 生长几率;
        int offsetx = -(size + 1) / 2;
        int offsety = -(size + 1) / 2;

        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            drawer.drawLight(this);
        }


        @Override
        public void updateTile() {


            if (efficiency > 0) {

                progress += getProgressIncrease(生长速度);
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);


                if (wasVisible && Mathf.chanceDelta(updateEffectChance)) {
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            } else {
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * Time.delta;

            if (progress >= 1f) {


                progress %= 1f;
                consume();
                if (wasVisible) {
                    craftEffect.at(x, y);
                }


                if (主干表.size < 主枝干数) {
                    主干();
                } else {
                    枝干();

                }
            }

        }


        public float warmupTarget() {
            return 1f;
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        public void 主干() {
            oo:
            for (int dx = 0; dx < size + 2; dx++) {
                for (int dy = 0; dy < size + 2; dy++) {
                    生长几率s = 生长几率s * 0.5f * (dx + dy) + 0.1f;
                    {
                        if (Mathf.chanceDelta(Math.min(生长几率s, 1))) {
                            int wx = dx + offsetx + tile.x, wy = dy + offsety + tile.y;
                            Tile check = world.tile(wx, wy);
                            if (check.build == null && Build.validPlace(枝干, Vars.player.team(), check.x, check.y, rotation)) {
                                world.tile(check.pos()).setBlock(主干, team, 0);
                                主干表.add(check);
                                枝干表.add(check);
                                break oo;
                            }
                        }
                    }
                }
            }
        }

        public void 枝干() {
            if (枝干表.isEmpty()) return;


            for (Tile tile1 : 枝干表) {
                if (枝干表.size > 枝干总数) {
                    break;
                }
                if (tile1.build != null) {

                    if (tile1.x == this.x && tile1.y > this.y) {
                        Tile check = world.tile(tile1.x, tile1.y + 1);
                        fghfhgfh(check);
                    }
                    if (tile1.x == this.x && tile1.y < this.y) {
                        Tile check = world.tile(tile1.x, tile1.y - 1);
                        fghfhgfh(check);
                    }
                    if (tile1.x > this.x && tile1.y == this.y) {
                        Tile check = world.tile(tile1.x + 1, tile1.y);
                        fghfhgfh(check);
                    }
                    if (tile1.x < this.x && tile1.y == this.y) {
                        Tile check = world.tile(tile1.x - 1, tile1.y);
                        fghfhgfh(check);
                    }
//
//
//
//                    float size = tile1.block().size + 2;
//                    for (int dx = 0; dx < size; dx++) {
//                        for (int dy = 0; dy < size; dy++) {
//                            int wx = dx + offsetx + tile1.x, wy = dy + offsety + tile1.y;
//                            Tile check = world.tile(wx, wy);
////                            if (主干表.contains(check)) {
////
////
////
////
////
////                            }
//
////
////                            if (Mathf.chanceDelta(生长几率s)) {
////                            } else 生长几率s *= 2;
//
//
////                                if (check.build == null) {
////                                    world.tile(check.pos()).setBlock(枝干, team, 0);
////                                    craftEffect.at(check.x, check.y);
////                                    枝干表.add(check);
////                                    生长几率s = 生长几率;
////                                    break 枝干表;
////                                }
//
//
//                        }
                }


            }

        }

        public void fghfhgfh(Tile check) {
            if (check.build == null) {
                world.tile(check.pos()).setBlock(枝干, team, 0);
                craftEffect.at(check.x, check.y);
                枝干表.add(check);
            }
        }

        public void removejjj(Tile Tile) {
            枝干表.remove(Tile);
//            if (主干表.contains(Tile)) {
//                主干表.remove(Tile);
//            }
        }


        @Override
        public double sense(LAccess sensor) {
            if (sensor == LAccess.progress) return progress();
            return super.sense(sensor);
        }

        @Override
        public float progress() {
            return Mathf.clamp(progress);
        }

        @Override
        public int getMaximumAccepted(Item item) {
            return itemCapacity;
        }

        @Override
        public boolean shouldAmbientSound() {
            return efficiency > 0;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(progress);
            write.f(warmup);

        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            progress = read.f();
            warmup = read.f();
        }
    }
}
