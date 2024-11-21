package MODS.core内容;

import MODS.HX_外_Building;
import arc.Events;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.core.GameState;
import mindustry.entities.units.BuildPlan;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.ConstructBlock;

import static MODS.HX_Vars.*;
import static MODS.core内容.建造限制.*;
import static MODS.core内容.绘制draw.cursorX;
import static MODS.core内容.绘制draw.cursorY;
import static mindustry.Vars.*;

public class 事件逻辑 {


    public static void load() {
        //块无了
        Events.on(EventType.BlockDestroyEvent.class, e -> {

            if (e.tile.build != null) {
                Building a = e.tile.build;
                if (a.block() != null) {
                    Block b = a.block();
                    if (WAI_Building.containsKey(b)) {
                        HX_外_Building.HX_外_Building_限制 c = WAI_Building.get(b).限制;
                        Seq<String> 建筑标签 = c.建筑标签;

                        减建造数量(建筑实体字符串(a, a.team()));

                        for (String s : 建筑标签) {

                            减建造数量(建筑标记字符串(a.team(), s));

                        }
                        //c.建筑地块.remove(e.tile);
                    }

                }
            }

        });
        //构建结束
        Events.on(EventType.BlockBuildEndEvent.class, e -> {

            BuildPlan a = e.unit.buildPlan();
            Block b = content.block(a.block.id);
//            Log.info("块构建结束事件" + b);

            if (WAI_Building.containsKey(b)) {

                HX_外_Building.HX_外_Building_限制 c = WAI_Building.get(b).限制;
                Seq<String> 建筑标签 = c.建筑标签;


                if (e.breaking) {
                    减建造数量(建筑块字符串(b, e.team));
                    for (String s : 建筑标签) {
                        减建造数量(建筑标记字符串(e.team, s));
                    }
//c.建筑地块.remove(e.tile);

                } else {
                    加建造数量(建筑块字符串(b, e.team));
                    for (String s : 建筑标签) {
                        加建造数量(建筑标记字符串(e.team, s));

                    }
                    //Log.info("块构建结束事件  加");
                    // c.建筑地块.add(e.tile);
                }


            }


        });
        //构建开始
        Events.on(EventType.BlockBuildBeginEvent.class, e -> {


            BuildPlan b = e.unit.buildPlan();
            Block type = content.block(b.block.id);
            Tile tile = Vars.world.tile(cursorX(), cursorY());
            HX_外_Building c = WAI_Building.get(type);
            Seq<Building> iii = ((ConstructBlock.ConstructBuild) e.tile.build).prevBuild;
            Block previous = ((ConstructBlock.ConstructBuild) e.tile.build).previous;

            //Log.info(获取(建筑块字符串(type, e.team)));
            // Log.info("块构建开始事件");
            if (!e.breaking) {
                if (WAI_Building.containsKey(type)) {
                    if (!限制判断(c, type, e.team)) {
//                        Queue<BuildPlan> h = e.unit.plans();
                        e.unit.clearBuilding();
                        world.tile(b.tile().pos()).setBlock(Blocks.air, e.team, 0);
//                        h.remove(b);
//                        for (BuildPlan hh : h) {
//                            e.unit.addBuild(hh);
//                        }
                        if (iii.size != 0) {
                            for (Building building : iii) {
                                world.tile(building.pos()).setBlock(building.block(), e.team, building.rotation());
                            }
                        }
                        ui.announce("限制信息");
                    }


                }
                if (iii.size != 0) {
                    for (Building building : iii) {
                        Block kkk = building.block();
                        if (WAI_Building.containsKey(kkk)) {
                            HX_外_Building.HX_外_Building_限制 lklkk = WAI_Building.get(kkk).限制;
                            减建造数量(建筑块字符串(kkk, e.team));
                            for (String s : lklkk.建筑标签) {
                                减建造数量(建筑标记字符串(e.team, s));
                            }
                        }


                    }
                }

            }

        });
        //拾取
        Events.on(EventType.PickupEvent.class, e -> {

            Block b = e.build.block();
            if (WAI_Building.containsKey(b)) {
                HX_外_Building.HX_外_Building_限制 c = WAI_Building.get(b).限制;
                Seq<String> 建筑标签 = c.建筑标签;
                减建造数量(建筑实体字符串(e.build, e.build.team()));
                for (String s : 建筑标签) {
                    减建造数量(建筑标记字符串(e.build.team(), s));
                }
            }

        });
        //放下
        Events.on(EventType.PayloadDropEvent.class, e -> {

            Block b = e.build.block();
            if (WAI_Building.containsKey(b)) {
                if (限制判断(WAI_Building.get(b), b, e.build.team)) {
                    HX_外_Building.HX_外_Building_限制 c = WAI_Building.get(b).限制;
                    Seq<String> 建筑标签 = c.建筑标签;
                    加建造数量(建筑实体字符串(e.build, e.build.team()));
                    for (String s : 建筑标签) {
                        加建造数量(建筑标记字符串(e.build.team(), s));

                    }
                } else {
                    world.tile(e.build.tile().pos()).setBlock(Blocks.air, e.build.team, 0);
                    ui.announce("限制信息");
                }
            }
        });


        //保存地图
        Events.on(EventType.SaveWriteEvent.class, e -> {
            保存s.保存();
        });
        //加载地图
        Events.on(EventType.SaveLoadEvent.class, e -> {
            Time.runTask(1f, 保存s.加载());
        });

        //状态改变
        Events.on(EventType.StateChangeEvent.class, event -> {
            if (event.to == GameState.State.menu) {
                保存s.clear();
                点击的Tile = new Tile(0, 0);
            }


        });

    }


    public static void 转换建筑load() {

//        //Events.on(新事件.转换建筑.class, e -> {
//
//            Tile AS = tileAt(点击位置.x, 点击位置.y);
//            Block a = e.a;
//            超block b = 超block表.getNull(a);
//            Team team = player.team();
//            if (b.has转换材料()) {
//                world.tile(AS.pos()).setBlock(a, team);
//                b.remove转换材料();
//
//
//                减建造数量(建筑块字符串(e.b.block, team));
//                for (String s : e.b.建筑标签) {
//                    减建造数量(建筑标记字符串(team, s));
//                }
//
//
//                加建造数量(建筑块字符串(b.block, team));
//                for (String s : b.建筑标签) {
//                    加建造数量(建筑标记字符串(team, s));
//
//                }
//
//
//            }
//        });
//
//
    }

}
