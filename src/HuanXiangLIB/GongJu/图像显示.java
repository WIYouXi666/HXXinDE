package HuanXiangLIB.GongJu;

import arc.scene.ui.Image;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.ctype.UnlockableContent;
import mindustry.ui.Styles;

import static mindustry.Vars.iconMed;

public class 图像显示<T extends UnlockableContent> extends Table{
/**
 * UnlockableContent 内容显示下标
 * */
        public final T type;
        public final float amount;

        public 图像显示(T type, float amount){
            this.type = type;
            this.amount = amount;
            add(new Stack(){{
                add(new Image(type.uiIcon).setScaling(Scaling.fit));

                if(amount != 0){
                    Table t = new Table().left().bottom();
                    t.add(Strings.autoFixed(amount, 2)).style(Styles.outlineLabel);
                    add(t);
                }
            }}).size(iconMed).padRight(3  + (amount != 0 && Strings.autoFixed(amount, 2).length() > 2 ? 8 : 0));
            add(type.localizedName);
        }

}
