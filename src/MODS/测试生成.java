package MODS;

import MODS.内容.HXBlock;
import arc.Core;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitFactory;

import static MODS.内容.切换方块类.切换方法.建筑表add;
import static MODS.内容.切换方块类.切换方法.设置标记映射;
import static mindustry.type.ItemStack.with;

public class 测试生成 {
    public static HXBlock BHDFB;

    public static Block dfv;
    // public static 配方 占位;
    public static HXBlock HuanXiangMODair = new HXBlock(" HuanXiangMODair") {

        @Override
        public boolean isHidden() {
            return Core.settings.getBool("测试");
        }


    };
    public static void load() {


        dfv = new UnitFactory("ground-dfgbgdfg"){{
            建筑表add(this);
            设置标记映射(this,"切换方块") ;
            requirements(Category.units, with(Items.copper, 50, Items.lead, 120, Items.silicon, 80));
            plans = Seq.with(
                    new UnitPlan(UnitTypes.dagger, 60f * 15, with(Items.silicon, 10, Items.lead, 10)),
                    new UnitPlan(UnitTypes.crawler, 60f * 10, with(Items.silicon, 8, Items.coal, 10)),
                    new UnitPlan(UnitTypes.nova, 60f * 40, with(Items.silicon, 30, Items.lead, 20, Items.titanium, 20))
            );
            size = 3;
            consumePower(1.2f);
        }};

    }
}
