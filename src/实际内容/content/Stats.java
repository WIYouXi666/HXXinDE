package 实际内容.content;

import mindustry.type.Item;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

public class Stats {










    public static final Stat 建造时间花费 = new Stat("建造时间花费", StatCat.general);
    public static final Stat 建筑血量系数 = new Stat("建筑血量系数", StatCat.general);
    public static final Stat 是否可建造 = new Stat("是否可建造", StatCat.general);
    public static final Stat 硬度 = new Stat("硬度", StatCat.general);
    public static final Stat 优先级 = new Stat("优先级", StatCat.general);
    public static void ItemStat(Item item){
        item.stats.add(建造时间花费, item.cost);
        item.stats.add(建筑血量系数, item.healthScaling);
        item.stats.add(是否可建造, item.buildable);
        item.stats.add(优先级, item.lowPriority);
        item.stats.add(硬度, item.hardness);
    }


}
