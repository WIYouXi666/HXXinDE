package HuanXiangDIY.单位;

import arc.struct.ObjectMap;
import mindustry.gen.Unit;

public class 能力组 {
    public Unit unit;

    public ObjectMap<String, 功能> 内容 = ObjectMap.of(
            "dsfgg", new 功能(unit) {
                @Override
                public void 实现() {


                }
            },
            "gjhj", new 功能(unit) {
                @Override
                public void 实现() {


                }
            }

    );

    public 能力组(Unit unit) {
        this.unit = unit;
//        for (功能 fff : 内容.values()) {
//            fff.实现();
//        }

    }

    public class 功能 {
        public Unit unit;

        public 功能(Unit unit) {
            this.unit = unit;
        }

        public void 实现() {


        }
    }


}
