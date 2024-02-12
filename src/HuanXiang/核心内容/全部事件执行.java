package HuanXiang.核心内容;

import HuanXiang.主要.配置;
import HuanXiang.核心内容.事件.自建事件;
import arc.Events;
import mindustry.game.EventType;

public class 全部事件执行 {
    public static void load() {
        //游戏内事件
        Events.on(自建事件.功能事件.class, e -> {
            e.ip.执行();
        });
        //配置文件
        Events.on(EventType.ClientLoadEvent.class, i -> {
            for (配置 a : 配置.all) {
                a.配置s();
            }
        });
        //单位死亡事件
        Events.on(EventType.UnitDestroyEvent.class, e-> {});

    }
}
