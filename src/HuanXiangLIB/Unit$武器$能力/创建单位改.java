package HuanXiangLIB.Unit$武器$能力;

import arc.Events;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class 创建单位改 {
    static Unit u;

    public static Unit 创建单位( float x, float y,UnitType a, Team b, Ability[] c,WeaponMount[] d) {
        u = a.create(b);
        u.set(x,y);
        Events.fire(new EventType.UnitCreateEvent(u, null));
        u.add();
        sdfsdf v = new sdfsdf();
        v.unit = u;
        v.加能力(c);
        v.加武器(d);
        return u;
    }
    public static Unit 创建单位(float x, float y, UnitType a, Team b, Ability... c) {
       return 创建单位(x,y,a,b,c,null);
    }
    public Unit 创建单位(float x, float y,UnitType a, Team b, WeaponMount... c) {
        return 创建单位(x,y,a,b,null,c);
    }
}
