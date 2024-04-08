package HuanXiangLIB;

import HuanXiangLIB.Unit$武器$能力.adcf;
import arc.struct.ObjectMap;
import mindustry.game.Rules;
import mindustry.gen.Unit;

public class 规则Rules extends Rules {
    public ObjectMap<Unit, adcf> 武器能力记录;


    public 规则Rules() {
     super();
        武器能力记录 = new ObjectMap<>();
    }
}
