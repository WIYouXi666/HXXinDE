package MODS.关于文件;

import arc.Core;

import static MODS.HX_Vars.数据库;
import static mindustry.Vars.ui;

public class 预加载 {
    public static void load() {
     if (Core.settings.getBool("蓝图预加载")) {
        ui.schematics.show();
        ui.schematics.hide();
    }
        if (Core.settings.getBool("数据库预加载")) {
            ui.database.show();
            数据库.hide();
        }






    }
}
