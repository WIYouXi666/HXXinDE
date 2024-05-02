package HuanXiangLIB.注册类;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.gen.Tex;

import static HuanXiangLIB.核心内容.总集类.修正映射;
import static HuanXiangLIB.核心内容.总集类.修正组;

public class 修正 {


    public boolean 无限火力 = false;

    public boolean 构建不消耗资源 = false;
    public boolean 无限弹药 = true;

    public float 单位生产速度 = 0f;
    public float 单位伤害 = 0f;

    public float 单位死亡伤害 = 0f;

    public float 单位生产花费 = 0f;

    public float 单位生命值 = 0f;

    public float 块生命值 = 0f;

    public float 块伤害 = 0f;
    public float 建造速度 = 0f;
    static boolean z = false;
    static int id = 0;
    static String name;

    public 修正(String A) {
        name = A;
        修正组.add(this);
        id = nextId();
        修正映射.put(id, this);
    }

    public int id() {
        return id;
    }

    public int nextId() {
        return id++;
    }

    public Table ffffff(Table A) {
        Table g = new Table();
        g.table(Tex.button, t -> {
            t.margin(0).defaults().pad(3).padLeft(5f).growX().left();
            t.button(b -> {
                b.left();
//                b.image(group.type.uiIcon).size(32f).padRight(3).scaling(Scaling.fit);
//                b.add(group.type.localizedName).color(Pal.accent);

                b.add().growX();
//
//                b.label(() -> (group.begin + 1) + "").color(Color.lightGray).minWidth(45f).labelAlign(Align.left).left();
//
//                b.button(Icon.copySmall, Styles.emptyi, () -> {
//                    groups.insert(groups.indexOf(group) + 1, expandedGroup = group.copy());
//                    buildGroups();
//                }).pad(-6).size(46f).tooltip("@editor.copy");
//
//                b.button(group.effect != null ?
//                                new TextureRegionDrawable(group.effect.uiIcon) :
//                                Icon.logicSmall,
//                        Styles.emptyi, () -> showEffects(group)).pad(-6).size(46f).scaling(Scaling.fit).tooltip(group.effect != null ? group.effect.localizedName : "@none");
//
//                b.button(Icon.unitsSmall, Styles.emptyi, () -> showUnits(type -> group.type = type, false)).pad(-6).size(46f).tooltip("@stat.unittype");
//                b.button(Icon.cancel, Styles.emptyi, () -> {
//                    groups.remove(group);
//                    if(expandedGroup == group) expandedGroup = null;
//                    table.getCell(t).pad(0f);
//                    t.remove();
//                    buildGroups();
//                }).pad(-6).size(46f).padRight(-12f).tooltip("@waves.remove");
//                b.clicked(KeyCode.mouseMiddle, () -> {
//                    groups.insert(groups.indexOf(group) + 1, expandedGroup = group.copy());
//                    buildGroups();
//                });
            }, () -> {
                z = !z;
                //   t.remove();

                // gengxin
            }).height(46f).pad(-6f).padBottom(0f).row();

            if (z) {
                if (无限火力) t.add(Core.bundle.get("修正.无限火力") + ": [00FF00FF]ture[]").row();
                if (构建不消耗资源) t.add(Core.bundle.get("修正.构建不消耗资源") + ": [00FF00FF]ture[]").row();
                if (无限弹药) t.add(Core.bundle.get("修正.无限弹药") + ": [FF0000FF]false[]").row();
                if (单位生产速度 != 0) {
                    if (单位生产速度 > 0) {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [00FF00FF]+" + 单位生产速度 * 100 + "%[]").row();
                    } else {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [FF0000FF]-" + 单位生产速度 * 100 + "%[]").row();
                    }
                }
                if (单位伤害 != 0) {
                    if (单位伤害 > 0) {
                        t.add(Core.bundle.get("修正.单位伤害") + ": [00FF00FF]+" + 单位伤害 * 100 + "%[]").row();
                    } else {
                        t.add(Core.bundle.get("修正.单位伤害") + ": [FF0000FF]-" + 单位伤害 * 100 + "%[]").row();
                    }
                }
                if (单位死亡伤害 != 0) {
                    if (单位死亡伤害 > 0) {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [00FF00FF]+" + 单位死亡伤害 * 100 + "%[]").row();
                    } else {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [FF0000FF]-" + 单位死亡伤害 * 100 + "%[]").row();
                    }
                }
                if (单位生产花费 != 0) {
                    if (单位生产花费 > 0) {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [00FF00FF]+" + 单位生产花费 * 100 + "%[]").row();
                    } else {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [FF0000FF]-" + 单位生产花费 * 100 + "%[]").row();
                    }
                }
                if (单位生命值 != 0) {
                    if (单位生命值 > 0) {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [00FF00FF]+" + 单位生命值 * 100 + "%[]").row();
                    } else {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [FF0000FF]-" + 单位生命值 * 100 + "%[]").row();
                    }
                }
                if (块生命值 != 0) {
                    if (块生命值 > 0) {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [00FF00FF]+" + 块生命值 * 100 + "%[]").row();
                    } else {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [FF0000FF]-" + 块生命值 * 100 + "%[]").row();
                    }
                }
                if (块伤害 != 0) {
                    if (块伤害 > 0) {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [00FF00FF]+" + 块伤害 * 100 + "%[]").row();
                    } else {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [FF0000FF]-" + 块伤害 * 100 + "%[]").row();
                    }
                }
                if (建造速度 != 0) {
                    if (建造速度 > 0) {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [00FF00FF]+" + 建造速度 * 100 + "%[]").row();
                    } else {
                        t.add(Core.bundle.get("修正.单位生产速度") + ": [FF0000FF]-" + 建造速度 * 100 + "%[]").row();
                    }
                }
            }
        });

        return g;
    }

}