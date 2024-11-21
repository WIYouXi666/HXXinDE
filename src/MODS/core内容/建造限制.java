package MODS.core内容;

import MODS.HX_外_Building;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;

import static MODS.HX_Vars.保存s;
import static MODS.HX_Vars.保存键表;
import static java.lang.Math.max;

public class 建造限制 {
    static String 限制 = 保存键表.get(2);
    static String 标签 = 保存键表.get(3);

    public static String 建筑块字符串(Block b, Team team) {
        return 限制 + "." + b.localizedName + "." + team.id;

    }

    public static String 建筑实体字符串(Building b, Team team) {
        return 限制 + "." + b.block().localizedName + "." + team.id;

    }

    public static String 建筑标记字符串(Team team, String 标签z) {
        return 标签 + "." + 标签z + "." + team.id;

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
        数值增减(a, 1f);
    }

    public static void 减建造数量(String a) {
        数值增减(a, -1f);
    }

    public static boolean 超过极限(String a, float m) {
        return 获取数值(a) > m;
    }

    public static boolean 主动构建(String a, float m) {
        return 获取数值(a) < m;
    }

    public static boolean 前置构建(String a, float m) {
        return 获取数值(a) >= m;
    }

    public static boolean 超过极限(String a, String m) {
        return 获取数值(a) > 获取数值(m);
    }

    public static boolean 主动构建(String a, String m) {
        return 获取数值(a) < 获取数值(m);
    }

    public static boolean 前置构建(String a, String m) {
        return 获取数值(a) >= 获取数值(m);
    }


    public static void 设置(String st, String se) {
        保存s.保存的信息.add(st, se);
    }

    public static String 获取(String st) {

        if (保存s.保存的信息.get(st) == null) {
            return "0";
        }
        return 保存s.保存的信息.get(st);
    }

    public static void 设置数值(String st, float 值) {
        设置(st, String.valueOf(值));
    }

    public static float 获取数值(String st) {
        return Float.parseFloat(获取(st));
    }

    public static void 数值增减(String st, float 值) {
        设置数值(st, max(获取数值(st) + 值, 0));//todo
    }

    public static void 数值改变(String st, float 值) {
        设置数值(st, 值);
    }


    public static void 设置布尔属性(String st, boolean b) {
        设置(st, String.valueOf(b));
    }

    public static boolean 读取布尔属性(String st) {
        if (获取(st) == null) {
            设置布尔属性(st, false);
        }
        return Boolean.parseBoolean(获取(st));
    }

    public static void 改变布尔属性(String st, boolean b) {
        if (获取(st) == null) {
            设置布尔属性(st, false);
        } else {
            设置布尔属性(st, b);
        }
    }

    public static void 改变布尔属性(String st) {
        改变布尔属性(st, !读取布尔属性(st));
    }


    public static boolean 限制判断(HX_外_Building l, Block d, Team team) {
        boolean ppo = true;
        HX_外_Building.HX_外_Building_限制 c = l.限制;
        if (l.标签限制 && c.限制数量 > 0) {
            ppo = 主动构建(建筑标记字符串(team, l.主建筑标签), c.限制数量);
        }
        if (c.限制数量 > 0) {
            ppo = ppo && 主动构建(建筑块字符串(d, team), c.限制数量);
        }
        if (!c.前置建筑.isEmpty()) {
            int jjj = 0;
            for (Block block : c.前置建筑) {
                boolean kkk = 前置构建(建筑块字符串(block, team), c.建筑低数);
                if (kkk) jjj++;
                ppo = ppo && kkk && jjj < c.建筑类数;
                if (!ppo) break;
            }
        }
        if (!c.前置标签.isEmpty()) {
            int jjj = 0;
            for (String block : c.前置标签) {
                boolean kkk = 前置构建(建筑标记字符串(team, block), c.标签低数);
                if (kkk) jjj++;
                ppo = ppo && kkk && jjj < c.标签类数;
                if (!ppo) break;
            }
        }
        return ppo;
    }
}
