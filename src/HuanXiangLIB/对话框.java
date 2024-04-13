package HuanXiangLIB;

import arc.Core;
import arc.scene.style.Drawable;
import mindustry.gen.Icon;
import mindustry.ui.dialogs.BaseDialog;

public class 对话框 extends BaseDialog {

    private boolean 默认配置 = false;
    private boolean 不再显示 = false;
    public 对话框() {
        super("对话框");
        boolean b = Core.settings.get("对话框", false)==null?false:(boolean) Core.settings.get("对话框", false);
        if (b!=不再显示){
            hide();
        }
        this.buttons.button("@back", (Drawable) Icon.left, ()->{
            Core.settings.put("对话框", 不再显示);
            hide();

        }).size(210.0F, 64.0F);

        this.buttons.button(Core.bundle.get("package.start"), (Drawable)Icon.right, () -> {
//            if (!this.默认配置) {
//
//                hide();
//                return;
//            }
            //Vars.ui.showConfirm(Core.bundle.get("package.installConfirm"), this::releasePack);
        }).row();
        this.buttons.check(Core.bundle.get("package.useDefaultConfig"), this.默认配置, i -> this.默认配置 = i);
        this.buttons.check("不再显示", b, i -> this.不再显示 = i);

    }
    public void 执行() {
            buttons.add("dgfdgdf");



    }
}
