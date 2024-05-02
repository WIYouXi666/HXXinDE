package HuanXiangLIB.Unit$武器$能力;


import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class 创建单位改 {

    public static Unit 创建单位(float x, float y, UnitType a, Team b, String A) {
        Unit u = a.create(b);
        u.set(x, y);
        Events.fire(new EventType.UnitCreateEvent(u, null));
        u.add();
//        for (Building G : Groups.build) {
//            if(G instanceof 单位组装记录块.单位组装记录块Build h){
//              h.添加单位映射(u.id(), A);
//            }
//        }

        // 单位组装记录块.添加单位映射(u.id(), A);



        return u;
    }

}
