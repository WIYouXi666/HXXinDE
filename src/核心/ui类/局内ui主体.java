package 核心.ui类;

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
    public static Table table = new Table(), table1, table2;
    ;
    public static Table 左上角的菜单, 波, 状态表, 状态;
    public static WidgetGroup 波浪编辑器;


    public static ArrayList<ui内容主体> 交互集合 = new ArrayList<>();
    public static int 项目列数 = 10;
    public static float 长 = 65 * 5 + 4;
    public static int 高 = 45;
    /**可以下拉的最大尺寸*/
    public static float 最大尺寸 = 20 * 45;

    public static void 局内ui() {


        try {
            ui内容主体.all.each(SA ->
                    {
                        交互集合.add(SA);
                    }
            );
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
            波.row().add(table).left().growX().row();
            波.add(信息表).width(65f * 5f + 4f).left();

            Element 信息表2 = 左上角的菜单.find("mobile buttons");


        } catch (Exception e) {
            throw new ArcRuntimeException("无效的 UI 参数！检查游戏和模组的版本!");
        }
    }

    public static void coolapser(boolean aa,Table table, String text, Cons<Table> lambda) {
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
        table.background(Styles.black6);
        coolapser( false ,table, "菜单", OO -> {
            OO.table(Tex.buttonEdge4, t -> {
                for (ui内容主体 aa : 交互集合) {
                   coolapser(aa.设置禁用,t, aa.toString(), aa::加载);
                }
            });
        });
    }
}