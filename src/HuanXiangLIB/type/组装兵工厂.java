package HuanXiangLIB.type;

import HuanXiangLIB.单位实体改.实体武器能力记录绑定Unit;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.io.TypeIO;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.net;

public class 组装兵工厂 extends Block {
    public UnitType unitType = UnitTypes.quad;
    public float buildTime = 60f * 8f;

    public float polyStroke = 1.8f, polyRadius = 8f;
    public int polySides = 6;
    public float polyRotateSpeed = 1f;
    public Color polyColor = Pal.accent;

    public 组装兵工厂(String name) {
        super(name);

        solid = true;
        update = true;
        hasItems = true;
        itemCapacity = 200;
        ambientSound = Sounds.respawning;
        configurable = true;
    }

    @Override
    public boolean outputsItems() {
        return false;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("units", (zzzz e) ->
                new Bar(
                        () ->
                                Core.bundle.format("bar.unitcap",
                                        Fonts.getUnicodeStr(unitType.name),
                                        e.team.data().countType(unitType),
                                        Units.getStringCap(e.team)
                                ),
                        () -> Pal.power,
                        () -> (float) e.team.data().countType(unitType) / Units.getCap(e.team)
                ));
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return super.canPlaceOn(tile, team, rotation) && Units.canCreate(team, unitType);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        if (!Units.canCreate(Vars.player.team(), unitType)) {
            drawPlaceText(Core.bundle.get("bar.cargounitcap"), x, y, valid);
        }
    }

//    @Remote(called = Loc.server)
//    public static void unitTetherBlockSpawned(Tile tile, int id){
//        if(tile == null || !(tile.build instanceof UnitTetherBlock build)) return;
//        build.spawned(id);
//    }

    public class zzzz extends Building {
        public transient Float[] 能力z = new Float[256];
        public transient Float[] 武器z = new Float[256];

        public Ability[] 能力 = new Ability[0];
        public Weapon[] 武器 = new Weapon[0];
        public WeaponMount[] 武器3 = new WeaponMount[0];
        //needs to be "unboxed" after reading, since units are read after buildings.
        public int readUnitId = -1;
        public float buildProgress, totalProgress;
        public float warmup, readyness;
        public @Nullable Unit unit;
        public boolean 刚开始 = false;
        public boolean 改变的 = true;
        public boolean 开始 = false;
        public boolean z = true;
        //配置
        @Override
        public void buildConfiguration(Table table) {
            table.button("开始t",()->{
                开始 = true;
            }).row();
            table.button("开始f",()->{
                开始 = false;
            });
        }

        @Override
        public void updateTile() {
            if(开始){
//                if (z){  加能力(new ForceFieldAbility(100, 1, 10000, 0),
//                        new UnitSpawnAbility(UnitTypes.dagger, 600, 0, 0));
//                    z =false;}
                if (刚开始 && unit != null){
                    刚开始 = false;
                    改变的 = false;
                    if (unit.mounts().length != 武器.length) {
                        unit.mounts = new WeaponMount[武器.length];

                        for (int i = 0; i < unit.mounts.length; ++i) {
                            unit.mounts[i] = 武器[i].mountType.get(武器[i]);
                        }
                        武器3 = new WeaponMount[unit.mounts().length];
                        System.arraycopy(unit.mounts, 0, 武器3, 0, unit.mounts.length);
                    }


                    if (unit.abilities.length != 能力.length) {
                        unit.abilities = new Ability[能力.length];

                        for (int i = 0; i < 能力.length; ++i) {
                            unit.abilities[i] = 能力[i].copy();
                        }
                    }
                }

                //unit was lost/destroyed
                if (unit != null && (unit.dead || !unit.isAdded())) {
                    unit = null;
                }

                if (readUnitId != -1) {
                    unit = Groups.unit.getByID(readUnitId);
                    if (unit != null || !net.client()) {
                        readUnitId = -1;
                    }
                }

                warmup = Mathf.approachDelta(warmup, efficiency, 1f / 60f);
                readyness = Mathf.approachDelta(readyness, unit != null ? 1f : 0f, 1f / 60f);

                  if (unit == null && Units.canCreate(team, unitType)) {

                    buildProgress += edelta() / buildTime;
                    totalProgress += edelta();

                    if (buildProgress >= 1f) {
                        if (!net.client()) {
                            unit = unitType.create(team);
//                            if (unit instanceof BuildingTetherc bt) {
//                                bt.building(this);
//                            }
                            unit.set(x, y);
                            unit.rotation = 90f;
                            if (unit.mounts().length != 武器.length) {
                                unit.mounts = new WeaponMount[武器.length];

                                for (int i = 0; i < unit.mounts.length; ++i) {
                                    unit.mounts[i] = 武器[i].mountType.get(武器[i]);
                                }
                                武器3 = new WeaponMount[unit.mounts().length];
                                System.arraycopy(unit.mounts, 0, 武器3, 0, unit.mounts.length);
                            }


                            if (unit.abilities.length != 能力.length) {
                                unit.abilities = new Ability[能力.length];

                                for (int i = 0; i < 能力.length; ++i) {
                                    unit.abilities[i] = 能力[i].copy();
                                }
                            }
                            unit.add();
                            Call.unitTetherBlockSpawned(tile, unit.id);
                            实体武器能力记录绑定Unit v = new 实体武器能力记录绑定Unit();
                            v.加能力("测试1");
                            v.set绑定unitid(unit.id());
                            v.yici();
                            v.add();
                        }
                        buildProgress = 0;
                    }
                }
            }
        }


//        public void spawned(int id) {
//            Fx.spawn.at(x, y);
//            buildProgress = 0f;
//            if (net.client()) {
//                readUnitId = id;
//            }
//        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.total() < itemCapacity;
        }

        @Override
        public boolean shouldConsume() {
            return unit == null;
        }

        @Override
        public boolean shouldActiveSound() {
            return shouldConsume() && warmup > 0.01f;
        }

        @Override
        public void draw() {
            Draw.rect(block.region, x, y);
            if (unit == null) {
                Draw.draw(Layer.blockOver, () -> {
                    //TODO make sure it looks proper
                    Drawf.construct(this, unitType.fullIcon, 0f, buildProgress, warmup, totalProgress);
                });
            } else {
                Draw.z(Layer.bullet - 0.01f);
                Draw.color(polyColor);
                Lines.stroke(polyStroke * readyness);
                Lines.poly(x, y, polySides, polyRadius, Time.time * polyRotateSpeed);
                Draw.reset();
                Draw.z(Layer.block);
            }
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        @Override
        public float progress() {
            return buildProgress;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
//            write.f(能力z.length);
//            for (Float v : 能力z) {
//                write.f(v);
//            }

            write.bool(开始);
            write.bool(true);
            TypeIO.writeAbilities(write, 能力);
            TypeIO.writeMounts(write, 武器3);
            write.i(unit == null ? -1 : unit.id);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            z= false;
            开始 = read.bool();
            刚开始 = read.bool();
            能力 = TypeIO.readAbilities(read, 能力);
            武器3 = TypeIO.readMounts(read, 武器3);
            for (int i = 0; i < 武器3.length; ++i) {
                武器[i] = 武器3[i].weapon;
            }
            readUnitId = read.i();

        }
        public void 加能力(Ability... a) {
            if (a == null) return;
            能力 = new Ability[a.length];
            System.arraycopy(a, 0, 能力, 0, a.length);
        }

        public void 加武器(WeaponMount... a) {
            if (a == null) return;
            武器3 = new WeaponMount[a.length];
            System.arraycopy(a, 0, 武器3, 0, a.length);
        }
    }
}
