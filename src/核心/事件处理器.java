package 核心;

import arc.Events;
import mindustry.game.EventType;
import 实际内容.主要.配置;
import 核心.事件.关于单位;
import 核心.事件.自建事件;

public class 事件处理器 {
    public static void 执行() {

        //更新
        Events.run(EventType.Trigger.update, () -> {
            关于单位.执行();
        });
        //游戏内事件
        Events.on(自建事件.功能事件.class, e -> {
            e.ip.执行();
        });
        //配置文件
        Events.on(EventType.ClientLoadEvent.class, i -> {
//            for (配置 a : 配置.all) {
//                a.配置s();
//            }
        });
        //单位死亡事件
        Events.on(EventType.UnitDestroyEvent.class, e-> {});

    }
}
