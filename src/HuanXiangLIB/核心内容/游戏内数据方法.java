package HuanXiangLIB.核心内容;

import mindustry.Vars;

public class 游戏内数据方法 {








    public static void 设置(String st, String se) {
        Vars.state.rules.tags.put(st, se);
    }
    public static String 获取(String st) {
        if (Vars.state.rules.tags.get(st) == null) {
            设置(st, "无");
        }
        return Vars.state.rules.tags.get(st);
    }

    public static void 设置数值(String st, double 值) {
        Vars.state.rules.tags.put(st, String.valueOf(值));
    }
    public static double 获取数值(String st) {
        if (Vars.state.rules.tags.get(st) == null) {
            设置数值(st, 0);
        }
        return Double.parseDouble(获取(st));
    }
    public static void 数值增减(String st, double 值) {
        设置数值(st,获取数值(st)+值);
    }
    public static void 数值改变(String st, double 值) {
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






}
