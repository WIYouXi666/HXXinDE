package HuanXiang.游戏内功能事件;

import HuanXiang.KeNengYongDao.数组;
import HuanXiang.核心内容.事件.局内事件主类;
import arc.Events;
import arc.struct.ObjectMap;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;

public class 召唤单位 extends 局内事件主类 {
    public  ObjectMap<UnitType, Float> all = new ObjectMap<>();
    public  Team team = Team.baseTeams[2];
    public 数组 降临中心 = new 数组(0,0);
    public 数组 起点 = new 数组(0,0);
    public StatusEffect SDAFA = StatusEffects.none;
    public  float fdsa = 0;
    public 召唤单位() {
        super("召唤单位");
    }
    @Override
    public void 执行() {
        if (Vars.state.isGame()){
            double 角度 = Math.toDegrees(Math.atan2(降临中心.x - 起点.x, 降临中心.y - 起点.y));
            for (ObjectMap.Entry<UnitType, Float> unit : all) {
                for (float i = 0; i < unit.value; i++) {
                    Unit u = unit.key.create(team);
                    u.set(降临中心.x, 降临中心.y);
                    u.rotation = (float) 角度;
                    Events.fire(new EventType.UnitCreateEvent(u, null));
                    if (!Vars.net.client()) {
                        u.add();
                        u.apply(SDAFA,fdsa);
                    }

                }

            }
        }


    }


}
