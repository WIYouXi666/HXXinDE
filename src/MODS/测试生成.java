package MODS;

import MODS.内容.HXBlock;
import MODS.内容.核心工厂;
import mindustry.type.Category;
import mindustry.world.meta.BuildVisibility;

public class 测试生成 {
    public static HXBlock BHDFB ;
    // public static 配方 占位;

    public static void load() {
        BHDFB = new 核心工厂("fhfggf") {{
            限制数量 = 5;
            category = Category.distribution;
            buildVisibility = BuildVisibility.shown;
        }};
        new 核心工厂("sdfdsfds") {{
            前置建筑.add(BHDFB);
            category = Category.distribution;
            buildVisibility = BuildVisibility.shown;
        }};
    }
}
