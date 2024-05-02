package HuanXiangDIY.内容.局内ui内容;

import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.ui.Styles;

import java.util.concurrent.atomic.AtomicInteger;


import static HuanXiangLIB.ui类.局内ui主体.项目列数;
import static HuanXiangLIB.工具方法.TRD;

public class 物品 extends uilib{
    public 物品() {
        super("物品");
        设置禁用 = Vars.state.rules.infiniteResources || Vars.state.rules.editor;
    }
    @Override
    public void 加载(Table ie) {
        ie.pane(e -> {
            AtomicInteger i = new AtomicInteger();
            Vars.content.items().each(item -> {
                e.button(TRD(item.uiIcon), Styles.flati, 40, () -> {
                    int fire = Vars.state.rules.coreIncinerates ? 20 : 1;
                    Vars.player.core().items.set(item, Vars.player.core().getMaximumAccepted(item) / fire);
                }).size(45);
                i.getAndIncrement();
                if (i.get() % 项目列数 == 0) e.row();
            });
        });
    }



}
