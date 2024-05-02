package HuanXiangLIB;

import HuanXiangLIB.接口.执行加载异步兼容;
import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

import java.util.ArrayList;

public class 声音注册 extends Sound implements 执行加载异步兼容 {
    public ArrayList<声音注册> 声音表 = new ArrayList<>();
    public String name;
    public static 声音注册 ghjg = new 声音注册("fhghjng");
    public 声音注册(String name) {
        this.name = name;
        声音表.add(this);
    }


    public 声音注册 设置循环声音(Sound 循环声音) {
        this.循环声音 = 循环声音;
        return this;
    }

    public Sound 循环声音;



    private static Sound loadSound(String soundName) {
        if (!Vars.headless) {
            String name = "sounds/" + soundName;
            String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

            Sound sound = new Sound();

            AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
            desc.errored = Throwable::printStackTrace;
            return sound;
        } else return new Sound();
    }


    /**
     *
     */
    @Override
    public void 执行() {
        if (!Vars.headless) {
            Vars.control.sound.loop(this.循环声音, 2f);
        }
    }

    /**
     *
     */
    @Override
    public void 加载() {
        for (声音注册 声音注册 : 声音表) {
            声音表.set(声音表.indexOf(声音注册),(HuanXiangLIB.声音注册) loadSound(声音注册.name));
        }


    }

    /**
     *
     */
    @Override
    public void 异步() {

    }
}
