package HuanXiangLIB.核心内容;

import HuanXiangDIY.type.能量;
import HuanXiangDIY.主要.配方;
import HuanXiangLIB.GongJu.图像显示;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.ui.ItemDisplay;
import mindustry.ui.LiquidDisplay;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

import static HuanXiangLIB.工具方法.infoStyle;


public class UI {



    public static void 多合成统计显示(Table table, Block block, Seq<配方> s) {
        table.row();


        for (配方 rec : s) {
            ItemStack[] inputItems = rec.输入.items;
            ItemStack[] outputItems = rec.输出.items;
            LiquidStack[] inputLiquids = rec.输入.liquids;
            LiquidStack[] outputLiquids = rec.输出.liquids;

            table.table(infoStyle.up, part -> {
                part.add("[accent]" + Stat.input.localized()).expandX().left().row();
                part.table(row -> {
                    for (ItemStack inputItem : inputItems)
                        row.add(new ItemDisplay(inputItem.item, inputItem.amount, true)).padRight(5f);
                }).left().row();
                part.table(row -> {
                    for (LiquidStack inputLiquid : inputLiquids)
                        row.add(new LiquidDisplay(inputLiquid.liquid, inputLiquid.amount, false));
                }).left().row();
                part.table(infoStyle.up,row -> {
                    for (ObjectMap.Entry<能量, Float> 能量FloatEntry : rec.输入.能量) {
                        row.add(new 图像显示(能量FloatEntry.key,能量FloatEntry.value));
                    }

                }).color(Pal.accent).left().growX().row();
                part.add("[accent]" + Stat.output.localized()).left().row();
                part.table(row -> {
                    for (ItemStack item : outputItems)
                        row.add(new ItemDisplay(item.item, item.amount, true)).padRight(5f);
                }).left().row();
                part.table(row -> {
                    for (LiquidStack liquid : outputLiquids)
                        row.add(new LiquidDisplay(liquid.liquid, liquid.amount, false));
                }).left().row();
                part.table(infoStyle.up,row -> {
                    //row.add("[lightgray]消耗:[]").padRight(4);
                    for (ObjectMap.Entry<能量, Float> 能量FloatEntry : rec.输出.能量) {
                        row.add(new 图像显示(能量FloatEntry.key,能量FloatEntry.value));
//                        Float value = 能量FloatEntry.value;
//                        // row.add( new 能量Display(能量FloatEntry.key,value,false));
//                        row.image(能量FloatEntry.key.uiIcon);
//                        row.add("[lightgray]" + 能量FloatEntry.key.localized() + ":[]").padRight(4);
//                        int precision = Math.abs(((double) value - value) <= 0.001f ? 0 : Math.abs((int) (value * 10) - value * 10) <= 0.001f ? 1 : 2);
//                        row.add(Strings.fixed(value, precision) + "/s ");
                    }

                }).color(Pal.accent).left().growX().row();
                part.table(row ->{
                        row.add("[lightgray]" + Stat.productionTime.localized() + ":[]").padRight(4f);
                    StatValues.number(600f, StatUnit.seconds).display(row);
                }).left().row();
            }).color(Pal.accent).left().growX();
            table.add().size(18f).row();
        }
    };
    /** An ItemDisplay, but for liquids. */

}
