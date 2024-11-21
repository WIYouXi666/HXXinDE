package MODS.ui;

public class 方块切换方法 {





//
//
//
//    public static void GN(Building b, Table table) {
//        table.row();
//        AtomicInteger i = new AtomicInteger(b.rotation());
//        table.table(o -> {
//            o.button("?", () -> {
//                ui.content.show(b.block());
//            }).size(64);
//            o.button("O", () -> {
//                i.getAndIncrement();
//                b.rotation(i.get() % 4);
//            }).size(64);
//        });
//    }
//
//    public static void buildConfiguration(Building b, Table table) {
//        AtomicInteger i = new AtomicInteger();
//        GN(b, table);
//        table.row();
//        table.table(o -> {
//            for (Block z : 建筑表) {
//                Seq<String> OO = get标记映射(z);
//                String h = null;
//                for (String s : OO) {
//                    if (get标记映射(b.block()).contains(s)) {
//                        h = s;
//                        break;
//                    }
//                }
//                if (h == null) continue;
//                //if (hxBlock == this.block()) continue;
//                if (z.unlockedNow()) {
//
//                    if (z.size != b.block().size) continue;
//                    i.getAndIncrement();
//                    o.button(new TextureRegionDrawable(z.uiIcon), () -> {
//                        if (z != b.block()) {
//                            Events.fire(new 新事件.选择事件(b, 建筑表.lastIndexOf(z)));
//                            put转换材料数量(获取转换消耗(z));
//                        }
//                    }).update(f -> {
//                        f.setDisabled(!消耗成立(z));
//                    }).size(64);
//                }
//                if (i.get() % 转换长度 == 0) o.row();
//            }
//
//        });
//    }

}
