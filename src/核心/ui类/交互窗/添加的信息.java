package 核心.ui类.交互窗;

import mindustry.Vars;
import mindustry.type.Item;

import static 实际内容.content.Stats.ItemStat;

public class 添加的信息 {
    public static void 执行(){
        Vars.content.each(uc -> {
            if(uc instanceof Item) {
                ItemStat((Item) uc);
            }
        });
    }
}
