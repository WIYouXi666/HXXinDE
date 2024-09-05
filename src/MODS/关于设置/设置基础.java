package MODS.关于设置;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.ui.CheckBox;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.ui.dialogs.SettingsMenuDialog;

import static arc.Core.settings;

public class 设置基础 {
  //     i.checkPref("改变输入方式", false, val -> {
//                if (val) {
//                    if (mindustry.Vars.mobile) {
//                        control.setInput(new MobileInput());
//                    } else {
//                        control.setInput(new DesktopInput());
//                    }
//                } else {
//                    control.setInput(zzz);
//                }
//            });
    //i.textPref("dsfs", "dfgfd");
    //i.sliderPref("mnbm", 100, 0, 100, 1, o -> o + "klj");
    //设置标题(i, "以下为mod配置文件 在mod里面\"config.json\"\n 此处仅为展示配置状态", 颜色1("ff1679"), 颜色1("#78FFFD"));
    protected static void 设置标题(SettingsMenuDialog.SettingsTable table, String A) {
        设置标题(table, A, Color.valueOf("#ff0000"));
    }

    protected static void 设置标题(SettingsMenuDialog.SettingsTable table, String A, Color K) {
        设置标题(table, A, K, K);
    }

    protected static void 设置标题(SettingsMenuDialog.SettingsTable table, String A, Color K, Color L) {
        table.pref(new 设置基础.标题(A, K, L));
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
                if (A != B) i.setColor(Tmp.c1.set(A).lerp(B, Mathf.sin((float) (Time.time * 0.05))));
            }).center().padTop(3.0F).get());
            table.row();
        }
    }


    public static class 不变的检查设置 extends SettingsMenuDialog.SettingsTable.Setting {
        public 不变的检查设置(String name) {
            super(name);
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table) {

            CheckBox box = new CheckBox(this.title);
            box.update(() -> {
                box.setChecked(settings.getBool(this.name));
            });
            box.left();
            this.addDesc(table.add(box).left().padTop(3.0F).get());
            table.row();
        }
    }

//    public static class 不变的滑块设置 extends SettingsMenuDialog.SettingsTable.Setting {
//        SettingsMenuDialog.StringProcessor sp;
//
//        public 不变的滑块设置(String name, SettingsMenuDialog.StringProcessor s) {
//            super(name);
//            this.sp = s;
//        }
//
//        public void add(SettingsMenuDialog.SettingsTable table) {
//            Slider slider = new Slider(0, max, 0, false);
//
//            Label value = new Label("", Styles.outlineLabel);
//            Table content = new Table();
//            content.add(this.title, Styles.outlineLabel).left().growX().wrap();
//            content.add(value).padLeft(10.0F).right();
//            content.margin(3.0F, 33.0F, 3.0F, 33.0F);
//            content.touchable = Touchable.disabled;
//            slider.update(() -> {
//                if (settings.getInt(this.name) > max) {
//                    slider.setValue(max);
//                    Core.settings.put(this.name, max);
//                    value.setText(this.sp.get((int) slider.getValue()) + "[red]配置超上限已重置[]");
//                }
//                if (settings.getInt(this.name) < 0) {
//                    slider.setValue(0);
//                    Core.settings.put(this.name, 0);
//                    value.setText(this.sp.get((int) slider.getValue()) + "[red]配置超下限已重置[]");
//                }
//                slider.setValue(settings.getFloat(this.name));
//                value.setText(this.sp.get((int) slider.getValue()));
//
//            });
//            slider.change();
//            this.addDesc(table.stack(new Element[]{slider, content}).width(Math.min((float) Core.graphics.getWidth() / 1.2F, 460.0F)).left().padTop(4.0F).get());
//            table.row();
//        }
//    }
    //    public  Fi getConfig(String S) {
//        Mods.LoadedMod load = Vars.mods.locateMod(S);
//        if (load == null) {
//            throw new IllegalArgumentException("Mod尚未加载(或丢失)!");
//        } else {
//            return
//        }
//    }
    //i.collapser(k->{ }, true, () -> settings.getBool("显示其他mod设置"));
}
