package MODS.重要内容;

import arc.func.Func;
import arc.graphics.g2d.TextureRegion;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.scene.Element;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import arc.util.Strings;
import arc.util.Tmp;
import mindustry.core.UI;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.ui.ItemDisplay;
import mindustry.ui.LiquidDisplay;
import mindustry.ui.ReqImage;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import mindustry.world.modules.LiquidModule;

import static MODS.HX_Vars.infoStyle;
import static mindustry.Vars.iconMed;

public class 工具类 {

    /**
     * UnlockableContent 内容显示下标
     */
    public static Table 图像显示(UnlockableContent type, float amount,boolean showName) {
        Table kk = new Table();
        kk.add(new Stack() {{
            add(new Image(type.uiIcon).setScaling(Scaling.fit));

            if (amount != 0) {
                Table t = new Table().left().bottom();
                t.add(Strings.autoFixed(amount, 2)).style(Styles.outlineLabel);
                add(t);
            }
        }}).size(iconMed).padRight(3 + (amount != 0 && Strings.autoFixed(amount, 2).length() > 2 ? 8 : 0));
        if(showName) kk.add(type.localizedName).padLeft(4 + amount > 99 ? 4 : 0);
        kk.add(type.localizedName);
        return kk;
    }


    public static void 多合成统计显示(Table table, Block block, ItemStack[] inputItems, ItemStack[] outputItems, LiquidStack[] inputLiquids, LiquidStack[] outputLiquids) {
        table.row();
        table.table(infoStyle.up, part -> {
            part.add("[accent]" + Stat.input.localized()).expandX().left().row();
            if (inputItems.length >1) {
                part.table(row -> {
                    for (ItemStack inputItem : inputItems)
                        row.add(new ItemDisplay(inputItem.item, inputItem.amount, true)).padRight(5f);
                }).left().row();

            }
            if (inputLiquids.length >1) {
                part.table(row -> {
                    for (LiquidStack inputLiquid : inputLiquids)
                        row.add(new LiquidDisplay(inputLiquid.liquid, inputLiquid.amount, false));
                }).left().row();
            }

            if (outputItems.length >1) {
                part.add("[accent]" + Stat.output.localized()).left().row();
                part.table(row -> {
                    for (ItemStack item : outputItems)
                        row.add(new ItemDisplay(item.item, item.amount, true)).padRight(5f);
                }).left().row();
            }
            if (outputLiquids.length >1) {
                part.table(row -> {
                    for (LiquidStack liquid : outputLiquids)
                        row.add(new LiquidDisplay(liquid.liquid, liquid.amount, false));
                }).left().row();
            }

            part.table(row -> {
                row.add("[lightgray]" + Stat.productionTime.localized() + ":[]").padRight(4f);
                StatValues.number(600f, StatUnit.seconds).display(row);
            }).left().row();
        }).color(Pal.accent).left().growX();
        table.add().size(18f).row();


    }

    public static class 动态流体图像 extends Stack {
        /**
         * 在使用时显示 动态图像
         * No amount text
         *
         * @param region the fluid icon
         */
        public 动态流体图像(TextureRegion region) {

            add(new Table(o -> {
                o.left();
                o.add(new Image(region)).size(32f);
            }));
        }

        public 动态流体图像(TextureRegion region, float amount) {

            add(new Table(o -> {
                o.left();
                o.add(new Image(region)).size(32f);
            }));

            if (amount != 0) {
                add(new Table(t -> {
                    t.left().bottom();
                    t.add(amount >= 1000 ?
                                    UI.formatAmount((long) amount) :
                                    Mathf.round(amount) + "").fontScale(0.9f)
                            .style(Styles.outlineLabel);
                    t.pack();
                }));
            }
        }

        public 动态流体图像(LiquidStack stack) {
            this(stack.liquid.uiIcon, stack.amount);
        }
    }

    public static class 动态流体消耗 extends Consume {
        public final Func<Building, LiquidStack[]> fluids;

        @SuppressWarnings("unchecked")
        public <T extends Building> 动态流体消耗(Func<T, LiquidStack[]> fluids) {
            this.fluids = (Func<Building, LiquidStack[]>) fluids;
        }

        public boolean has(LiquidModule fluids, LiquidStack[] reqs) {
            for (LiquidStack req : reqs) {
                if (fluids.get(req.liquid) < req.amount)
                    return false;
            }
            return true;
        }

        public void remove(LiquidModule fluids, LiquidStack[] reqs, float multiplier) {
            for (LiquidStack req : reqs) {
                fluids.remove(req.liquid, req.amount * multiplier);
            }
        }

        @Override
        public void apply(Block block) {
            block.hasLiquids = true;
        }

        @Override
        public void update(Building build) {
            LiquidStack[] fluids = this.fluids.get(build);
            remove(build.liquids, fluids, build.edelta());
        }

        @Override
        public void build(Building build, Table table) {
            final LiquidStack[][] current = {fluids.get(build)};

            table.table(cont -> {
                table.update(() -> {
                    LiquidStack[] newFluids = fluids.get(build);
                    if (current[0] != newFluids) {
                        rebuild(build, cont);
                        current[0] = newFluids;
                    }
                });

                rebuild(build, cont);
            });
        }

        private void rebuild(Building tile, Table table) {
            table.clear();
            int i = 0;

            LiquidStack[] fluids = this.fluids.get(tile);
            for (LiquidStack stack : fluids) {
                table.add(new ReqImage(new 动态流体图像(stack.liquid.uiIcon),
                        () -> tile.liquids != null && tile.liquids.get(stack.liquid) >= stack.amount)).padRight(8).left();
                if (++i % 4 == 0) table.row();
            }
        }

        @Override
        public float efficiency(Building build) {
            LiquidStack[] fluids = this.fluids.get(build);
            return build.consumeTriggerValid() || has(build.liquids, fluids) ? 1f : 0f;
        }

    }

    public static class 兼容对话框拖动模块 extends InputListener {
        public Element 元素;
        public float X = 400.0f;
        public float lasty = 1.0f;


        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
            var v = 元素.localToParentCoordinates(Tmp.v1.set(x, y));
            this.X = v.x;
            this.lasty = v.y;
            return true;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            var v = 元素.localToParentCoordinates(Tmp.v1.set(x, y));
            元素.translation.add(v.x - this.X, v.y - this.lasty);
            this.X = v.x;
            this.lasty = v.y;
        }
    }
}
