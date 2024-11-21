package MODS.core内容;

import arc.util.Time;

import static MODS.HX_Vars.数据库;
import static MODS.HX_Vars.c;
import static mindustry.Vars.ui;

public class 原版ui替换与添加 {





    public static void load() {
        //TODO 自定义规则 地图信息对话框  能改的都改  赞👍
//        ui.planet.shown(() -> {
//           // Time.runTask(1f, a.rebuildButtons(ui.planet));
//            d.show();
//            Time.runTask(1f, ui.planet::hide);
//
//        });
        //数据库
        ui.database.shown(() -> {
            数据库.show();
            Time.runTask(1f, ui.database::hide);
        });
        ui.research.shown(() -> {
            c.show();
            Time.runTask(1f, ui.research::hide);
        });





    }




}
