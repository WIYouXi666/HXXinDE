package 实际内容.核心内容;

import 实际内容.type.能量;
import 实际内容.主要.配方;
import arc.graphics.Color;
import arc.scene.ui.Button;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.ui.ItemDisplay;
import mindustry.ui.LiquidDisplay;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

import static arc.Core.scene;
import static mindustry.Vars.iconMed;

public class UI {

    private static final Button.ButtonStyle infoStyle = scene.getStyle(Button.ButtonStyle.class);

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
                        row.add(new 能量显示(能量FloatEntry.key,能量FloatEntry.value,false));
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
                        row.add(new 能量显示(能量FloatEntry.key,能量FloatEntry.value,false));
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
    public static class 能量显示 extends Table{
        public final 能量 liquid;
        public final float amount;
        public final boolean perSecond;

        public 能量显示(能量 liquid, float amount, boolean perSecond){
            this.liquid = liquid;
            this.amount = amount;
            this.perSecond = perSecond;

            add(new Stack(){{
                add(new Image(liquid.uiIcon).setScaling(Scaling.fit));

                if(amount != 0){
                    Table t = new Table().left().bottom();
                    t.add(Strings.autoFixed(amount, 2)).style(Styles.outlineLabel);
                    add(t);
                }
            }}).size(iconMed).padRight(3  + (amount != 0 && Strings.autoFixed(amount, 2).length() > 2 ? 8 : 0));

            if(perSecond){
                add(StatUnit.perSecond.localized()).padLeft(2).padRight(5).color(Color.lightGray).style(Styles.outlineLabel);
            }

            add(liquid.localizedName);
        }
    }
}
