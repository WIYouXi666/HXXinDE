package MODS.内容;


import arc.Core;
import arc.files.Fi;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.ui.CheckBox;
import arc.struct.ObjectMap;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.serialization.Json;
import arc.util.serialization.Jval;
import mindustry.ui.dialogs.SettingsMenuDialog;

import static MODS.HuanXiangMOD.THIS;
import static MODS.HuanXiangMOD.getModName;
import static mindustry.Vars.ui;

public class 生成设置实现 {
    private final Json parser = new Json(){};
    public static void load() {


        ui.settings.addCategory("名字", getModName() + "-设置iu", i -> {

            //设置
            i.checkPref("savecreate", true);
            i.checkPref("建筑物限制提示", true);
            i.textPref("dsfs", "dfgfd");
            i.sliderPref("mnbm", 100, 0, 100, 1, o -> o + "klj");
            Core.settings.getBool("gbcb");
            设置标题(i, "以下为mod配置文件 在mod里面\"config.json\"",Color.valueOf("#ff1679"),Color.valueOf("#78FFFD"));
            设置标题(i, "此处仅为展示配置状态",Color.valueOf("#ff1679"),Color.valueOf("#78FFFD"));

            fnhhfgh(i);


        });
    }
    private static void 设置标题(SettingsMenuDialog.SettingsTable table, String A) {
        设置标题(table,A,Color.valueOf("#ff0000"));
    }
    private static void 设置标题(SettingsMenuDialog.SettingsTable table, String A,Color K) {
        设置标题(table,A,K,K);
    }
    private static void 设置标题(SettingsMenuDialog.SettingsTable table, String A,Color K,Color L) {
        table.pref(new 标题(A,K,L));
    }

    private static void fnhhfgh(SettingsMenuDialog.SettingsTable table) {
        Fi A = THIS().getConfig();
        Jval.JsonMap B = Jval.read(A.readString()).asObject();
        for (ObjectMap.Entry<String, Jval> C : B) {

           // table.pref(new t666666(C.key, "" + C.value));










            Core.settings.defaults(new Object[]{C.key, C.value});

        }
    }

    public static class 标题 extends SettingsMenuDialog.SettingsTable.Setting {
        public 标题(String name, Color a, Color b) {
            super(name);
            A = a;
            B = b;
        }

        Color A;
        Color B;

        @Override
        public void add(SettingsMenuDialog.SettingsTable table) {
            this.addDesc(table.add("[red]" + this.title + "[]").update(i -> {
                if(A!=B) i.setColor(Tmp.c1.set(A).lerp(B, Mathf.sin((float) (Time.time*0.05))));
            }).center().padTop(3.0F).get());
            table.row();
        }
    }



    public static class t666666 extends SettingsMenuDialog.SettingsTable.Setting {
        boolean def;

        public t666666(String name, String def) {
            super(name);
            this.def = def.equals("true");
        }

        public void add(SettingsMenuDialog.SettingsTable table) {

            CheckBox box = new CheckBox(this.title);
            box.update(() -> {
                box.setChecked(getBool(this.name));
            });
            box.left();
            this.addDesc(table.add(box).left().padTop(3.0F).get());
            table.row();
        }
    }


    public static Jval.JsonArray getArray(String name) {
        return  getArray(Core.settings.get(name,new Jval.JsonArray()));
    }

    public static boolean getBool(String name) {
        return  getBool(Core.settings.getString(name), true);
    }

    public static int getInt(String name) {
        return getInt(Core.settings.getString(name), 0);
    }

    public  float getFloat(String name) {
        return getFloat(Core.settings.getString(name), 0.0F);
    }

    public static long getLong(String name) {
        return getLong(Core.settings.getString(name), 0L);
    }
    public static double getDouble(String name) {
        return getDouble(Core.settings.getString(name), 0d);
    }

    public static int getInt(String name, int def) {
        return Jval.newArray().getInt(name, def);
    }

    public float getFloat(String name, float def) {
        return Jval.newArray().getFloat(name, def);
    }

    public static long getLong(String name, long def) {
        return Jval.newArray().getLong(name, def) ;
    }
    public static double getDouble(String name, double def) {
        return Jval.newArray().getDouble(name, def) ;
    }
    public static boolean getBool(String name, boolean def) {
        return Jval.newArray().getBool(name, def) ;
    }
    public static Jval.JsonArray getArray(Object name) {
        return  ((Jval)name).asArray();
    }



}
