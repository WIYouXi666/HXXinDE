package HuanXiangLIB.ui类;

import mindustry.Vars;
import mindustry.type.Item;

import static HuanXiangDIY.content.Stats.ItemStat;

public class 添加的信息 {
    public static void 执行(){
        Vars.content.each(uc -> {
            if(uc instanceof Item) {
                ItemStat((Item) uc);
            }
        });
    }
}
