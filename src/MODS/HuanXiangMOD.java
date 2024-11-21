package MODS;


import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.mod.Mod;

import static MODS.实现.基础加载;
import static arc.Core.assets;

public class HuanXiangMOD extends Mod {
    public static String ModName = "hx-hy";


    public HuanXiangMOD() {
        Log.info("fghfhffffffffffffffffffffffffffffff.");

    }

    @Override
    public void loadContent() {

        基础加载();
        Events.run(EventType.Trigger.update, 实现::更新事件);
        Events.run(EventType.ClientLoadEvent.class, 实现::客户端加载);
    }

    @Override
    public void init() {
        assets.load(new HX_Vars());
        HX_Vars.init();
    }

}
