package MODS.内容;

import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

public class 统计信息 {
    public static StatCat 多合成组;
    public static Stat 多合成;

    public static void load() {
        多合成组 = new StatCat("多合成组");
        多合成 = new Stat("多合成", 多合成组);
    }
}
