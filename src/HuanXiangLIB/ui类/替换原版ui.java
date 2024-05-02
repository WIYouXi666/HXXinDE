package HuanXiangLIB.ui类;

import HuanXiangLIB.ui类.对话框.MODS;
import HuanXiangLIB.ui类.对话框.内容详情对话框;
import HuanXiangLIB.ui类.对话框.地图编辑对话框;
import HuanXiangLIB.ui类.对话框.核心数据库对话框;
import arc.util.Time;
import mindustry.Vars;
import mindustry.editor.MapEditorDialog;
import mindustry.ui.dialogs.ContentInfoDialog;
import mindustry.ui.dialogs.DatabaseDialog;
import mindustry.ui.dialogs.ModsDialog;

public class 替换原版ui {


    public static void 核心数据库(boolean a) {
        //TODO 蛋疼 shi
        核心数据库对话框 数据库新 = new 核心数据库对话框();
        DatabaseDialog 数据库原 = Vars.ui.database;
        数据库原.shown(() -> {
                数据库新.show();
                Time.runTask(1f, 数据库原::hide);
        });

    }

    public static void MODS() {
        //TODO  替换MOD对话框
        MODS M = new MODS();
        ModsDialog dialog = Vars.ui.mods;
        dialog.shown(() -> {
            M.show();
            Time.runTask(1f, dialog::hide);
        });
    }

    public static void 地图编辑() {
        if (!Vars.headless) {
            Vars.ui.editor.remove();
            MapEditorDialog z = Vars.ui.editor;
            Vars.ui.editor = null;
            Vars.ui.editor = new 地图编辑对话框();
        }
    }

    public static void 内容详情() {
        if (!Vars.headless) {
            /* 内容详情 */
            Vars.ui.content.remove();
            ContentInfoDialog parent = Vars.ui.content;
            Vars.ui.content = null;
            Vars.ui.content = new 内容详情对话框(parent);
        }
    }
}
