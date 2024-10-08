package MODS.一些方法.工具;

import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import mindustry.core.UI;
import mindustry.type.LiquidStack;
import mindustry.ui.Styles;

public class 动态流体图像 extends Stack {
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
