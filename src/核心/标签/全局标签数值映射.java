package 核心.标签;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;

import static mindustry.ctype.ContentType.*;

public class 全局标签数值映射<T extends UnlockableContent> {
    public static final ObjectMap<UnlockableContent, 标签数值存储类> 全局标签数值映射 =  new ObjectMap<>();

    // TODO 可能无效
    public static void 执行() {
        Seq<Item>  Item集合 = Vars.content.getBy(item);
        for (Item b : Item集合) {
            全局标签数值映射.put(b,new 标签数值存储类());
        }
        Seq<Block>  Block集合 = Vars.content.getBy(block);
        for (Block b : Block集合) {
            全局标签数值映射.put(b,new 标签数值存储类());
        }
        Seq<Liquid>  Liquid集合 = Vars.content.getBy(liquid);
        for (Liquid b : Liquid集合) {
            全局标签数值映射.put(b,new 标签数值存储类());
        }





    }

}
