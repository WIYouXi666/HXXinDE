package HuanXiangLIB;

import HuanXiangLIB.GongJu.兼容对话框拖动模块;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Tex;

public class 新部件 extends Table {
    public 新部件() {
        super();
        clear();
        visibility = () -> Vars.ui.hudfrag.shown && !Vars.ui.minimapfrag.shown();
        defaults().left();
        //background(Styles.black6);
        background(Tex.buttonTrans);
        add().size(128, 32);
        button("关闭", this::remove).size(128, 32).row();
        add().size(128, 32);

        margin(0.0F).defaults().pad(3.0F).padLeft(5.0F).growX().left();

        addListener(new 兼容对话框拖动模块() {{
            t = xx();
        }});

    }

    public 新部件 xx() {
        return this;
    }

    public static 新部件 添加(Table a) {
        新部件 table = new 新部件();
        table.setFillParent(true);
        table.add(a).row();
        return table;
    }
}
