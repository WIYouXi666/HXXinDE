package MODS.关于设置;


import arc.files.Fi;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.serialization.Jval;
import mindustry.mod.Mods;

import static MODS.HuanXiangMOD.THIS;
import static MODS.HuanXiangMOD.getModName;
import static arc.Core.settings;
import static mindustry.Vars.mods;
import static mindustry.Vars.ui;

public class 设置实现 {


    private static float max = 500;


    public static Color 颜色1(String g) {
        return Color.valueOf("#" + g);
    }

    public static void load() {


        ui.settings.addCategory("名字", getModName() + "-设置iu", i -> {

            //设置
            //Core.bundle.get("1");
            //设置标题(i, "无需重启的设置", 颜色1("fff83e"));
            i.checkPref("建筑物限制提示", true);

            //i.checkPref("QBmodPZ", false);
            i.checkPref("CeShi", false);



            载入配置文件();
            if (settings.getBool("QBmodPZ")) {
                全部载入();
            } else {
                载入配置文件();
            }
        });
    }


    protected static void 载入配置文件() {
        Fi A = THIS().getConfig();
        Jval.JsonMap B = Jval.read(A.readString()).asObject();
        for (ObjectMap.Entry<String, Jval> C : B) {
            settings.defaults(new Object[]{C.key, C.value});
        }
    }

    protected static void 全部载入() {
        Seq<Mods.LoadedMod> o = mods.list();
        for (Mods.LoadedMod p : o) {
            Fi f = p.root.child("config.json");
            Jval.JsonMap zz = Jval.read(f.readString()).asObject();
            for (ObjectMap.Entry<String, Jval> cc : zz) {
                settings.defaults(new Object[]{cc.key, cc.value});
            }
        }
    }


}
