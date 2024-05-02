package HuanXiangLIB.ui类;

import arc.scene.Element;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.util.ArcRuntimeException;
import mindustry.Vars;

public class fff {
    public static Table table = new Table();
    public static Table 左上角的菜单, 波, 状态表, 状态;
    public static WidgetGroup 波浪编辑器;





    public  void 局内ui() {

        try {
            左上角的菜单 = Vars.ui.hudGroup.find("overlaymarker");//HudFragment
            波浪编辑器 = 左上角的菜单.find("waves/editor");
            波 = 波浪编辑器.find("waves");
            状态表 = 波.find("statustable");
            状态 = 状态表.find("status");
        } catch (ClassCastException e) {
            throw new ArcRuntimeException("无效的 UI 参数！检查游戏和模组的版本！");
        }

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




}
