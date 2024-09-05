package MODS.内容.切换方块类;

import MODS.内容.新事件;
import arc.Core;
import arc.Events;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import mindustry.gen.Building;
import mindustry.world.Block;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static mindustry.Vars.ui;
import static mindustry.Vars.world;

public class 切换方法 {


    public static void set长度(int 长度) {
        切换方法.长度 = 长度;
    }

    public static int 长度 = 7;
    public static final ArrayList<Block> 建筑表 = new ArrayList<>();
    public static final ObjectMap<Block, String> 建筑标记映射 = new ObjectMap<>();

    public static void 设置标记映射(Block st, String se) {
        建筑标记映射.put(st, se);
    }
    public static boolean 包含(Block st) {
        return 建筑标记映射.containsKey(st);
    }


    public static String 获取标记映射(Block st) {
        if (建筑标记映射.get(st) != null) {
            return 建筑标记映射.get(st);
        }
        return "KONG";
    }


    public static void 建筑表add(Block g) {
        if (!建筑表.contains(g)&&!g.isHidden()) {
            建筑表.add(g);
        }
    }

    static {
        Events.on(新事件.同步事件.class, e -> {
            float s = e.a.health() / e.a.maxHealth();
            e.b.health(e.b.maxHealth() * s);

            if(e.a.items != null&&e.b.items!=null){
                e.b.items = e.a.items;
            }
            if(e.a.liquids != null&&e.b.liquids!=null){
                e.b.liquids = e.a.liquids;
            }
            if(e.a.power != null&&e.b.power!=null){
                e.b.power = e.a.power;
            }

        });
        Events.on(新事件.选择事件.class, e -> {
            Building a = e.a;
            int i = e.b;
            Block kk = 建筑表.get(i);
            //测试聊天("" + i);
            if (kk == null) return;
            if (kk == a.block()) return;

            if (!a.block().unlockedNow()) {
                ui.announce(Core.bundle.format("hxblock.unlocked", a.block().localizedName), 3);
                return;
            }
            world.tile(a.pos()).setBlock(kk, a.team(), a.rotation());
            Events.fire(new 新事件.同步事件(a, kk.newBuilding()));
        });
    }


    public static void 功能(Building b, Table table) {
        table.row();
        AtomicInteger i = new AtomicInteger(b.rotation());
        table.table(o -> {
            o.button("?", () -> {
                ui.content.show(b.block());
            });
            o.button("O", () -> {
                i.getAndIncrement();
                b.rotation(i.get() % 4);
            });
        });
    }

    public static void 构建配置(Building b, Table table) {
        AtomicInteger i = new AtomicInteger();
        功能(b, table);
        table.row();
        table.table(o -> {

            for (Block z : 建筑表) {

                //if (hxBlock == this.block()) continue;
                if (!Objects.equals(获取标记映射(z), 获取标记映射(b.block()))) {
                    continue;
                }
                if (z.unlockedNow()) {

                    if (z.size != b.block().size) continue;
                    i.getAndIncrement();
                    o.button(new TextureRegionDrawable(z.uiIcon), () -> {
                        Events.fire(new 新事件.选择事件(b, 建筑表.lastIndexOf(z)));
                    });
                }
                if (i.get() % 长度 == 0) o.row();

            }

        });
    }

}
