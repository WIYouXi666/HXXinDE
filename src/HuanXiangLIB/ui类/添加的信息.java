package HuanXiangLIB.ui类;

import arc.graphics.Color;
import mindustry.Vars;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.Block;

import java.util.Objects;

import static HuanXiangDIY.content.Stats.ItemStat;

public class 添加的信息 {
    public static void 执行() {
        Vars.content.each(uc -> {

            if (uc instanceof Item) {
                ItemStat((Item) uc);
            }
            if (uc instanceof Block) {
                if (((Block) uc).canOverdrive) {
                    ((Block) uc).addBar("asdfasdf", (q) -> {
                        Color h = Pal.health;
                        Objects.requireNonNull(q);
                        Bar H = new Bar("时间尺度: " + q.timeScale(), h, () -> 1f);
                        H.blink(Color.white);
                        return H;
                    });
                }
            }
        });
    }
}
