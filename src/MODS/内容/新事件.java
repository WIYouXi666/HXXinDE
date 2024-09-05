package MODS.内容;

import mindustry.gen.Building;

public class 新事件 {
    public  enum 枚举事件 {
 同步事件 ;
    }
    public static class 同步事件{
        public final Building a;
        public final Building b;

        public 同步事件(Building a, Building b){
            this.a = a;
            this.b = b;
        }
    }
    public static class 选择事件{
        public final Building a;
        public final int b;

        public 选择事件(Building a, int b){
            this.a = a;
            this.b = b;
        }
    }
}
