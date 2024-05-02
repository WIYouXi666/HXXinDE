package HuanXiangLIB.ui类.对话框;

import arc.Core;
import arc.func.Cons;
import arc.scene.ui.Button;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.util.Nullable;
import arc.util.Scaling;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.maps.Map;
import mindustry.ui.BorderImage;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import java.util.Iterator;

public class 地图加载对话框 extends BaseDialog {
    @Nullable
    private Map selected = null;

    public 地图加载对话框(Cons<Map> loader) {
        super("@editor.loadmap");
        this.shown(this::rebuild);
        this.hidden(() -> {
            this.selected = null;
        });
        this.onResize(this::rebuild);
        this.buttons.defaults().size(210.0F, 64.0F);
        this.buttons.button("@cancel", Icon.cancel, this::hide);
        this.buttons.button("@load", Icon.ok, () -> {
            if (this.selected != null) {
                loader.get(this.selected);
                this.hide();
            }

        }).disabled((b) -> {
            return this.selected == null;
        });
        this.addCloseListener();
        this.makeButtonOverlay();
    }

    public void rebuild() {
        this.cont.clear();
        ButtonGroup<Button> group = new ButtonGroup();
        int i = 0;
        int cols = Math.max((int)((float) Core.graphics.getWidth() / Scl.scl(250.0F)), 1);
        Table table = new Table();
        table.defaults().size(250.0F, 90.0F).pad(4.0F);
        table.margin(10.0F);
        ScrollPane pane = new ScrollPane(table);
        pane.setFadeScrollBars(false);
        pane.setScrollingDisabledX(true);
        Iterator var6 = Vars.maps.all().iterator();

        while(var6.hasNext()) {
            Map map = (Map)var6.next();
            table.button((b) -> {
                b.add((new BorderImage(map.safeTexture(), 2.0F)).setScaling(Scaling.fit)).padLeft(5.0F).size(64.0F);
                b.add(map.name()).wrap().grow().labelAlign(1).padLeft(5.0F);
            }, Styles.squareTogglet, () -> {
                this.selected = map;
            }).group(group).checked((b) -> {
                return this.selected == map;
            });
            ++i;
            if (i % cols == 0) {
                table.row();
            }
        }

        group.uncheckAll();
        if (Vars.maps.all().isEmpty()) {
            table.add("@maps.none").center();
        } else {
            this.cont.add("@editor.selectmap");
        }

        this.cont.row();
        this.cont.add(pane);
    }
}
