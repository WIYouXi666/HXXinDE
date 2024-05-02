package HuanXiangDIY.内容.局内ui内容;

import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.ui.Styles;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

import static HuanXiangLIB.libxx.set粘贴板;

public class 统计信息 extends uilib {
    Seq<Stat> all = new Seq();
    Seq<StatCat> allb = new Seq();
    int i;

    public 统计信息() {
        super("统计信息");
        all = Stat.all;
        allb = StatCat.all;
        i = 0;
    }

    @Override
    public void 加载(Table table) {

        table.pane(a -> {
            a.pane(d -> {
                d.add("统计信息·点击复制");
            });
            a.row();
            a.pane(b -> {
                i = 0;
                for (Stat stat : all) {
                    i++;
                    b.button(stat.localized(), Styles.logict, () -> {
                        set粘贴板(stat.name);
                    }).size(100,45);
                    if (i % 10 == 0) b.row();
                }
            });
            a.row();
            a.pane(c -> {
                i = 0;
                for (StatCat statCat : allb) {
                    i++;
                    c.button(statCat.localized(), Styles.logict, () -> {
                        set粘贴板(statCat.name);
                    }).size(100,45);;
                    if (i % 10 == 0) c.row();
                }

            });


        });


    }
}
