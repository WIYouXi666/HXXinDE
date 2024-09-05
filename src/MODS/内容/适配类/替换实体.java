package MODS.内容.适配类;

import mindustry.Vars;
import mindustry.world.blocks.production.AttributeCrafter;

import static MODS.内容.切换方块类.切换方法.*;

public class 替换实体 {


    public static void load() {
        Vars.content.blocks().each(i -> {

            if (i instanceof AttributeCrafter o) {
                if (包含(o)) return;
                o.configurable = true;
                建筑表add(o);
                设置标记映射(o,"属性生产工厂");
                o.buildType = () -> new 改实体.工厂改(o);
            }
        });

    }


}
