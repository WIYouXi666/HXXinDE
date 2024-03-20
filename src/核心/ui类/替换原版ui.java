package 核心.ui类;

import arc.util.Time;
import mindustry.Vars;
import mindustry.ui.dialogs.ContentInfoDialog;
import mindustry.ui.dialogs.DatabaseDialog;
import 核心.ui类.交互窗.内容详情;
import 核心.ui类.交互窗.核心数据库;

public class 替换原版ui {
    public static void 执行() {
//TODO  替换MOD对话框
//        MODS M = new MODS();
//        ModsDialog dialog = Vars.ui.mods;
//        dialog.shown(() -> {
//            M.show();
//            Time.runTask(1f, dialog::hide);
//        });


        核心数据库 数据库新 = new 核心数据库();
        DatabaseDialog 数据库原 = Vars.ui.database;
        数据库原.shown(() -> {
            数据库新.show();
            Time.runTask(1f, 数据库原::hide);
        });
       // 分类栏 分类栏 = new 分类栏();


        if (!Vars.headless) {
            /* 内容详情 */
            Vars.ui.content.remove();
            ContentInfoDialog parent = Vars.ui.content;
            Vars.ui.content = null;
            Vars.ui.content = new 内容详情(parent);
        }
    }
}
