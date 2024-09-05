package MODS.内容.适配类;

import arc.scene.ui.layout.Table;
import mindustry.world.blocks.production.AttributeCrafter;

import static MODS.内容.切换方块类.切换方法.*;

public class 改实体 {


        public static class 工厂改 extends AttributeCrafter.AttributeCrafterBuild {
            public 工厂改(AttributeCrafter h) {
                h.super();

            }
            @Override
            public void buildConfiguration(Table table) {
               super.buildConfiguration(table);
                构建配置(this,table);
            }

        }

}
