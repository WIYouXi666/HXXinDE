package MODS.内容;

import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;

import static MODS.HuanXiangMOD.getModName;

public class 建造限制 {

    public static String 建筑块字符串(Block b , Team team) {
        return getModName()+team.id+b.name+"限制";

    }
    public static String 建筑实体字符串(Building b,Team team) {
        return getModName()+b.team().id+b.block().name+"限制";

    }
    public static String 建筑标记字符串(Team team, String 标记) {
        return getModName()+team.id+标记+"标记";

    }
//    public static String 建筑块极限字符串(Team team, String 标记) {
//        return getModName()+team.id+标记+"极限";
//
//    }
//
//    public static void 设置数值建造数量(String a,float f) {
//        设置数值(a,f);
//    }

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
    public static boolean 超过极限(String a,String m) {
        return 获取数值(a)>获取数值(m);
    }
    public static boolean 主动构建(String a,String m) {
        return 获取数值(a)<获取数值(m);
    }
    public static boolean 前置构建(String a,String m) {
        return 获取数值(a)>=获取数值(m);
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
