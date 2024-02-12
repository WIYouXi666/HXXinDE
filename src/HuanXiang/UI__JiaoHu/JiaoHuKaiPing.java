package HuanXiang.UI__JiaoHu;

import arc.Core;
import arc.Events;
import arc.scene.ui.Dialog;
import arc.scene.ui.ImageButton;
import arc.scene.ui.TextButton;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import java.io.IOException;

import static mindustry.Vars.ui;
import static HuanXiang.HuanXiangMOD.A;


public class JiaoHuKaiPing {
    public static int tt = 0;
    public static boolean DFSAF = Vars.mods.locateMod("hx-fy") == null;
    //public static TextureRegionDrawable whiteui = (TextureRegionDrawable) Tex.whiteui;
    public static Dialog.DialogStyle DawnDialog = new Dialog.DialogStyle() {{
       // stageBackground = ((TextureRegionDrawable)whiteui).tint(0.0F, 0.0F, 0.0F, 0F);
        titleFont = Fonts.def;
        background = Tex.windowEmpty;
        titleFontColor = Pal.accent;
    }};

    public static void load() {

        Events.on(EventType.ClientLoadEvent.class, i -> {
            if (Vars.ui != null) {
                if (DFSAF) {
                    翻译模组创建文件.load();
                }
            }
        });
        JiaoHuJuNei.load();
    }
    public static void show() {
        BaseDialog dialog = new BaseDialog("---");
        ImageButton.ImageButtonStyle style = Styles.cleari;
        if (Core.scene.hasDialog()) {
            dialog.setStyle(Styles.fullDialog);
        } else {
            dialog.titleTable.remove();
            dialog.setStyle(DawnDialog);
        }
        dialog.cont.pane(t -> {
            t.button("加载翻译", JiaoHuKaiPing::加载).size(210.0F, 64.0F).update(u -> u.setDisabled(Vars.mods.locateMod("hx-fy") != null));
            t.row();
            t.button("重载翻译", JiaoHuKaiPing::重载).size(210.0F, 64.0F).update(JiaoHuKaiPing::TS1);
            t.row();
            t.button("删除翻译", JiaoHuKaiPing::删除).size(210.0F, 64.0F).update(u -> u.setDisabled(Vars.mods.locateMod("hx-fy") == null || tt >= 2));
            t.row();
        }).grow().center().maxWidth(700);
        dialog.buttons.button("@close", dialog::hide).size(210.0F, 64.0F).update(u -> {
            u.setDisabled(tt == 2);
        });
        dialog.show();
    }
    public static void 加载() {

        ui.loadfrag.show("加载翻译mod");
        Time.runTask(5f, () -> {
            try {
                Vars.mods.importMod(A);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ui.loadfrag.hide();
        });
    }

    public static void 重载() {
        tt++;
        ui.loadfrag.show("重载翻译mod");
        Time.runTask(10f, () -> {
            try {
                Vars.mods.removeMod(Vars.mods.locateMod("hx-fy"));
                翻译模组创建文件.load();
                Vars.mods.importMod(A);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ui.loadfrag.hide();
        });
    }

    public static void 删除() {
        tt = 2;
        ui.loadfrag.show("翻译mod");
        if (Vars.mods.locateMod("hx-fy") != null) {
            ui.loadfrag.show("翻译mod");
            Time.runTask(10f, () -> {
                Vars.mods.removeMod(Vars.mods.locateMod("hx-fy"));
                ui.loadfrag.hide();
            });
        }
    }

    public static void TS1(TextButton u) {
        u.setDisabled(Vars.mods.locateMod("hx-fy") == null || tt >= 3);
        if (tt == 1) {
            u.setText("赶紧的，重启游戏");
        }
        if (tt == 2) {
            u.setText("关闭游戏加载mod");
        }
        if (tt == 3) {
            ui.loadfrag.show("即将关闭游戏");
            Time.runTask(60f, () -> {
                Core.app.exit();
                //ui.loadfrag.hide();
            });
            tt++;
        }
    }


}
