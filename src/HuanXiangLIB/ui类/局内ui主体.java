package HuanXiangLIB.ui类;

import HuanXiangDIY.内容.局内ui内容.uilib;
import HuanXiangDIY.内容.局内ui内容.测试;
import HuanXiangLIB.核心内容.修正类记录;
import arc.func.Cons;
import arc.scene.Element;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.util.ArcRuntimeException;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

import java.util.ArrayList;


public class 局内ui主体 {
    public static Table table = new Table();
    public static Table 左上角的菜单, 波, 状态表, 状态;
    public static WidgetGroup 波浪编辑器;


    public static ArrayList<uilib> 交互集合 = new ArrayList<>();
    public static final int 项目列数 = 10;
    public static final float 长 = 65 * 5 + 4;
    public static final int 高 = 45;
    /**可以下拉的最大尺寸*/
    public static float 最大尺寸 = 20 * 45;
    /**
     *
     *
     * TODO 改**/
    public static void 局内ui() {


        try {
//            for (ObjectMap.Entry<String, uilib> stringuilibEntry : ui映射) {
//                交互集合.add(stringuilibEntry.value);
//            }




            左上角的菜单 = Vars.ui.hudGroup.find("overlaymarker");//HudFragment
            波浪编辑器 = 左上角的菜单.find("waves/editor");
            波 = 波浪编辑器.find("waves");
            状态表 = 波.find("statustable");
            状态 = 状态表.find("status");
        } catch (ClassCastException e) {
            throw new ArcRuntimeException("无效的 UI 参数！检查游戏和模组的版本！");
        }
        setup();
        try {
            Element 信息表 = 波.find("infotable");
            信息表.remove();
            波.row().add(table).left().margin(10f).growX().row();
            波.add(信息表).width(65f * 5f + 4f).left();

            Element 信息表2 = 左上角的菜单.find("mobile buttons");


        } catch (Exception e) {
            throw new ArcRuntimeException("无效的 UI 参数！检查游戏和模组的版本!");
        }
    }

    public static void coolapser(boolean aa, Table table, String text, Cons<Table> lambda) {
        final boolean[] 开启状态 = {false};
        table.button(Icon.downOpen, Styles.flati, () -> {
            开启状态[0] = !开启状态[0];
        }).size(长, 高).update(b -> {
            b.setDisabled(aa);
            b.getStyle().imageUp = 开启状态[0] ? Icon.upOpen : Icon.downOpen;
        }).get().add(text);
        table.row();
        table.collapser(lambda, true, () -> 开启状态[0]).maxSize(长, 最大尺寸).row();
    }

    public static void setup() {
        table.clear();
        table.visibility = () -> Vars.ui.hudfrag.shown && !Vars.ui.minimapfrag.shown();
        table.background(Tex.buttonTrans);
        coolapser( false ,table, "菜单", OO -> {
            uilib vbx = new 测试();
            OO.table(Tex.buttonEdge4, t -> {
//                coolapser(uilib.设置禁用,t, libxx.gn翻译(uilib.name), new 单位()::加载);
//                coolapser(uilib.设置禁用,t, libxx.gn翻译(uilib.name), new 物品()::加载);
                coolapser(vbx.设置禁用,t, vbx.name, vbx::加载);
//                coolapser(uilib.设置禁用,t, libxx.gn翻译(uilib.name), new 统计信息()::加载);
//                coolapser(uilib.设置禁用,t, libxx.gn翻译(uilib.name), new 信息ui()::加载);
               coolapser(false,t, "修正类记录", i->{
                   修正类记录.A = i;
                   修正类记录.加载();
               });



            });
        });
    }
}