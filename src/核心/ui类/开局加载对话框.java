package 核心.ui类;

import arc.Core;
import arc.scene.ui.Dialog;
import arc.scene.ui.ImageButton;
import arc.struct.Seq;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import 核心.加载方法;

import static mindustry.Vars.ui;

public class 开局加载对话框 {


    public static void 异步(){
        BDCoreRun.add(加载方法::异步加载);
    }
    private static final Seq<Runnable> BDCoreRun = new Seq<>();
    private static boolean 加载 = false, tt, ttt, tttt;

    private static void 加载() {
        if (!加载) {
            ui.loadfrag.show("正在加载内容");//加载
            加载 = true;
            for (Runnable a : BDCoreRun) {
                a.run();
            }
            ui.loadfrag.hide();//关闭
        }
    }

    public static Dialog.DialogStyle DawnDialog = new Dialog.DialogStyle() {{
        titleFont = Fonts.def;
        background = Tex.windowEmpty;
        titleFontColor = Pal.accent;
    }};

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
            t.button("" +加载, ()->{}).size(420.0F, 64.0F).update(u -> {
                    开局加载对话框.加载();
                    if(加载){
                        u.setText("额外内容加载完成");
                    }
                u.setDisabled(加载);
            });
            t.row();




        }).grow().center().maxWidth(700);
        dialog.buttons.button("@close", dialog::hide).size(210.0F, 64.0F).update(u -> {
            u.setDisabled(!加载);
        });
        dialog.show();
    }


}
