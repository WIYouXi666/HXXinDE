package MODS.sgfg;

import MODS.HX_外_Building;
import mindustry.gen.Building;
import mindustry.world.Block;

public class 新事件 {
    public enum 枚举事件 {
        实体替换事件;
    }

    public static class 同步事件 {
        public final Building a;
        public final Building b;

        public 同步事件(Building a, Building b) {
            this.a = a;
            this.b = b;
        }
    }

    public static class 转换建筑 {
        public final Block a;
        public final HX_外_Building b;
        public 转换建筑(Block a, HX_外_Building b) {
            this.a = a;
            this.b = b;
        }
    }
}
