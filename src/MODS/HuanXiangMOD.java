package MODS;


import MODS.内容.HXBlock;
import MODS.内容.ui.开局加载对话框;
import arc.Core;
import arc.Events;
import arc.files.Fi;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

import static MODS.实现.基础加载;

public class HuanXiangMOD extends Mod {

    protected static String ModName = "hxlid";

    public HuanXiangMOD() {

    }


    @Override
    public void loadContent() {
        基础加载();
        Events.run(EventType.ClientLoadEvent.class, 实现::客户端加载);
        Events.run(EventType.Trigger.update, 实现::更新);
    }

    @Override
    public void init() {

        if (Vars.ui != null) {
            开局加载对话框.初始化();
        }
    }

    public static HuanXiangMOD THIS() {
        return new HuanXiangMOD();
    }

    public static String getModName() {
        return ModName;
    }


    @Override
    public Fi getConfig() {
        Mods.LoadedMod load = Vars.mods.locateMod(ModName);
        if (load == null) {
            throw new IllegalArgumentException("Mod尚未加载(或丢失)!");
        } else {
            return load.root.child("config.json");
        }
    }
}
