package HuanXiangDIY;

import HuanXiangDIY.事件.自建事件;
import arc.Events;
import mindustry.game.EventType;

public class 事件处理器 {

    public static void 执行() {

        //ObjectMap<Integer, adcf> po = adcf.alll;
        //更新
//        Events.run(EventType.Trigger.update, () -> {
//
//            Groups.unit.each(u->{
//                if(po.containsKey(u.id())){
//                    po.get(u.id()).update();
//                }
//                gn添加聊天信息("" +u.id());
//                gn添加聊天信息("" +po.containsKey(u.id()));
//            });
//            gn添加聊天信息("" + po.size);
//            信息ui.更新();
//        });
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
        Events.on(EventType.UnitDestroyEvent.class, u-> {

        });

    }
}
