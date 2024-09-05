package MODS.内容.ui;

import MODS.实现;
import arc.struct.Seq;
import mindustry.graphics.Pal;
import mindustry.ui.dialogs.BaseDialog;

import static MODS.一些方法.方法.infoStyle;
import static mindustry.Vars.ui;

public class 开局加载对话框 {
    private static final Seq<Runnable> 异步Run = new Seq<>();


    public static void 初始化() {
        异步Run.add(实现.异步实现::异步);
    }

    private static boolean 加载 = false;
    private static void 异步加载() {
        if (!加载) {
            ui.loadfrag.show("正在加载内容");//加载
            加载 = true;
            for (Runnable a : 异步Run) {
                a.run();
            }
            ui.loadfrag.hide();
        }
    }





    public static void show() {

        BaseDialog dialog = new BaseDialog("---");


        dialog.cont.pane(t -> {

            t.table(infoStyle.up, a -> {
                a.button("" + 加载, 开局加载对话框::异步加载).size(196, 64.0F).update(u -> {
//                    开局加载对话框.加载();
//                    if (加载) {
//                        u.setText("额外内容加载完成");
//                    }
                    u.setDisabled(加载);
                   // dialog.hide();
                });
//                a.button("" + 加载, 开局加载对话框::加载).size(196, 64.0F).update(u -> {
//                    开局加载对话框.加载();
//                    if (加载) {
//                        u.setText("额外内容加载完成");
//                    }
//                    u.setDisabled(加载);
//                });
//                a.button("替换核心数据库", () -> {
//                    替换原版ui.核心数据库(!数据库);
//                    数据库 = !数据库;
//                }).size(196, 64.0F).update(u -> {
//
//                    if (数据库) {
//                        u.setText("核心数据库已替换");
//                    }
//                    u.setDisabled(数据库);
//                });
//                a.button("核心数据库改回来", () -> {
//                    替换原版ui.核心数据库(!数据库);
//                    数据库 = !数据库;
//                }).size(196, 64.0F).update(u -> {
//                    u.setDisabled(!数据库);
//                });


            }).color(Pal.accent).left().growX();


        }).grow().center().maxWidth(700);
        dialog.buttons.button("@close", dialog::hide).size(210.0F, 64.0F).update(u -> {
            u.setDisabled(!加载);
        });
        dialog.show();
    }


}
