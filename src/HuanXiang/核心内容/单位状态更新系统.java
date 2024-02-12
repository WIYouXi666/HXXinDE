package HuanXiang.核心内容;

import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

public class 单位状态更新系统 {
    public  void 主(){
        Events.run(EventType.Trigger.update, () -> {
            a();
        });
    }
    public void a(){
        if(!Vars.state.isPaused()) {
            Groups.unit.each(this::Update);
        }
    }
    public void Update(Unit unit){




    }

}
