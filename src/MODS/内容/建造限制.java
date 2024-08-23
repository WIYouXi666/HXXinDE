package MODS.内容;

import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;

import java.util.ArrayList;

import static MODS.HuanXiangMOD.getModName;

public class 建造限制 {

    public static boolean 新放置规则(Block block,ArrayList<HXBlock> 前置建筑, float 限制数量, Tile tile, Team team, int rotation) {
        if (限制数量 != -1) {
            return 主动构建(建筑块字符串(block,team),限制数量);
        }
        if(!前置建筑.isEmpty()){
            for (Block s : 前置建筑) {
                if(!前置构建(建筑块字符串(s,team), 1)) {
                   return false;
                }
            }
        }
        return true;
    }
//    public static void 放置规则显示(Block block , ArrayList<HXBlock> 前置建筑, int x, int y, boolean valid,float 限制数量, Tile tile, Team team, int rotation) {
//        if (world.tile(x, y) == null) return;
//
//
//        int B = 0;
//
//        boolean disabled = true;
//
//        if(!前置建筑.isEmpty()){
//            for (HXBlock hxBlock : 前置建筑) {
//                if (!CanBuild(Vars.player.team(), sp.前置建筑组.Name(i), sp.前置建筑组.Int(i))) {
//                    String Disabled = Core.bundle.format("block.breakingdawnmod-BarFrontBlocks", sp.前置建筑组.Name(i), sp.前置建筑组.Int(i));
//                    sp.block.drawPlaceText(Disabled, x, y + B, valid);
//                    sp.显示建造要求 = Disabled;
//                    disabled = false;
//                    B += 1;
//                }
//            }
//
//
//        }
//
//        if(sp.前置建筑标记 != null){
//            if (!CanBuild(Vars.player.team(), sp.前置建筑标记)) {
//                String Disabled = Core.bundle.format("block.breakingdawnmod-BarFrontBlock", sp.前置建筑标记);
//                sp.block.drawPlaceText(Disabled, x, y + B, valid);
//                sp.显示建造要求 = Disabled;
//                disabled = false;
//                B += 1;
//            }
//        }
//
//        if (sp.限制建造数量 != 0) {
//            if (!CanBuild(Vars.player.team(), sp.block, sp.限制建造数量)) {
//                if (sp.限制建造提示) {
//                    String Disabled = Core.bundle.format(sp.block.getContentType() + "." + sp.block.name + ".limit", String.valueOf(sp.限制建造数量));
//                    sp.block.drawPlaceText(Disabled, x, y + B, valid);
//                    sp.显示建造要求 = Disabled;
//                    disabled = false;
//                } else {
//                    String Disabled = Core.bundle.format("block.breakingdawnmod-BarLimitBlock", sp.限制建造数量);
//                    sp.block.drawPlaceText(Disabled, x, y + B, valid);
//                    sp.显示建造要求 = Disabled;
//                    disabled = false;
//                }
//                B += 1;
//            }
//        }
//
//        if(sp.MultiBlockStruc != null){
//            if(!sp.MultiBlockStruc.canPlaceOn(world.tile(x, y))){
//                String A = "范围内存在相同的结构核心方块";
//                sp.block.drawPlaceText(A, x, y + B, valid);
//                sp.显示建造要求 = A;
//                disabled = false;
//            }
//        }
//
//        if(disabled){
//            sp.显示建造要求 = "";
//        }
//    }















    public static String 建筑块字符串(Block b,Team team) {
        return getModName()+team.id+b.name+"限制";

    }
    public static String 建筑实体字符串(Building b,Team team) {
        return getModName()+b.team().id+b.block().name+"限制";

    }
    public static String 建筑标记字符串(Building b,Team team,String 标记) {
        return getModName()+b.team().id+b.block().name+标记+"标记";

    }

    public static void 加建造数量(String a) {
         数值增减(a,1f);
    }
    public static void 减建造数量(String a) {
        数值增减(a,-1f);
    }
    public static boolean 超过极限(String a,float m) {
        return 获取数值(a)>m;
    }
    public static boolean 主动构建(String a,float m) {
        return 获取数值(a)<m;
    }
    public static boolean 前置构建(String a,float m) {
        return 获取数值(a)>=m;
    }







        public static void 设置(String st, String se) {
            Vars.state.rules.tags.put(st, se);
        }
        public static String 获取(String st) {
            if (Vars.state.rules.tags.get(st) == null) {
                return "0";
            }
            return Vars.state.rules.tags.get(st);
        }

        public static void 设置数值(String st, float 值) {
            Vars.state.rules.tags.put(st, String.valueOf(值));
        }
        public static float 获取数值(String st) {
//            if (Vars.state.rules.tags.get(st) == null) {
//               return 0;
//            }
            return Float.parseFloat(获取(st));
        }
        public static void 数值增减(String st, float 值) {
            设置数值(st,获取数值(st)+值);
        }
        public static void 数值改变(String st, float 值) {
            设置数值(st,值);
        }



        public static void 设置布尔属性(String st,boolean b) {
            Vars.state.rules.tags.put(st, String.valueOf(b));
        }

        public static boolean 读取布尔属性(String st) {
            if (Vars.state.rules.tags.get(st) == null) {
                设置布尔属性(st, false);
            }
            return Boolean.parseBoolean(Vars.state.rules.tags.get(st));
        }
        public static void 改变布尔属性(String st,boolean b) {
            if (Vars.state.rules.tags.get(st) == null) {
                设置布尔属性(st, false);
            }else {设置布尔属性(st, b);}
        }
        public static void 改变布尔属性(String st) {
            改变布尔属性(st,!读取布尔属性(st));
        }
        public static void 删除属性(String st) {
            Vars.state.rules.tags.remove(st);
        }
    public static boolean 限制canPlaceOn(HXBlock a,boolean canPlaceOn) {

        return canPlaceOn;
    }

}
