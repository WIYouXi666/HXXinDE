package HuanXiangDIY.主要;

import HuanXiangLIB.记录$功能.标签lib;
import HuanXiangDIY.type.能量;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
//TODO 重写
public class 配方 {
    public static final Seq<配方> all = new Seq<>();
    public int id;
    public 条目 输入;
    public 条目 输出;
    public float 工艺时间 = 0f;
    public 标签lib.标签 类型;
    public Effect craftEffect = Fx.none;
    /**
     * 额外输出类型
     * true 数量
     * false 几率
     * */
    public boolean 额外输出类型 = true;
    public 条目 额外输出;
    public float 额外产出几率;
    public 条目 强化输入;
    public 条目 催化输入;
    public 配方( float 工艺时间, 标签lib.标签 类型) {
        this.工艺时间 = 工艺时间;
        this.类型  = 类型;
        id = all.size;
        all.add(this);
    }
    public static class 条目 {
        public  final ObjectMap<能量, Float> 能量 =  new ObjectMap<>();
        public 条目 添加能量(能量 st, float b) {
             float dd =0;
              dd = b<0? 0:b;
             能量.put(st,dd);
             return this;
        }
        public @Nullable ItemStack[] items;
        public @Nullable LiquidStack[] liquids;
        public 条目() {
        }
        public 条目 物品(ItemStack[] items) {
            this.items = items;
            return this;
        }
        public 条目 流体(LiquidStack[] liquids) {
            this.liquids = liquids;
            return this;
        }




    }

}
