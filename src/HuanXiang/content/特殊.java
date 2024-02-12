package HuanXiang.content;

import mindustry.gen.Iconc;
import mindustry.world.meta.*;

public class 特殊 {
    public static final StatCat
            类型 = new StatCat("类型"),
            幻想特殊 = new StatCat("幻想特殊");
    public static final StatUnit
            单位 = new StatUnit("单位", "[accent]" +Iconc.power + "[]"),
    个 = new StatUnit("个");
    public static final Stat
            属性 = new Stat("属性"),
            防御效果 = new Stat("防御效果", 幻想特殊),
    限制数量 = new Stat("限制数量", 幻想特殊);

}
