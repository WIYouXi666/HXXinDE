package HuanXiangLIB.记录$功能;

import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.struct.StringMap;
import arc.util.Log;
import arc.util.serialization.Jval;
import mindustry.Vars;

public class 配置文件 {
    static final Fi 配置文件 = Vars.modDirectory.child("配置文件");
    public Fi getFi(){
        return 配置文件;
    }

    public Fi config;

    配置文件(String name){
        config = 配置文件.child(name + ".hjson");
        load();
    }
    public 配置文件 添加配置(String name){
        return new 配置文件(name);
    }
    ObjectMap<String, String> SettingsInfos = new ObjectMap<>();
    public void setInfo(String name, String info){
        SettingsInfos.put(name, info);
    }

    public Data settings = new Data();
    public void load() {
        settings.loadFi(config);
    }

    public String getPut(String key, String value){
        if(get(key) == null){
            settings.put(key, value);
        }

        return settings.get(key);
    }

    public boolean getPutBool(String key, String value){
        if(get(key) == null){
            settings.put(key, value);
        }

        return settings.getBool(key);
    }

    public String put(String key, String value){
        return settings.put(key, value);
    }

    public String get(String key){
        return settings.get(key);
    }

    public boolean getBool(String key){
        return settings.getBool(key);
    }

    public class Data extends StringMap {
        @Override
        public String put(String key, String value) {
            String old = super.put(key, value);
            config.writeString("" + this);
            return old;
        }

        public void loadFi(Fi fi) {
            if (!fi.exists()) return;
            try {
                for(var entry : Jval.read(fi.readString()).asObject()){
                    super.put(entry.key, "" + entry.value);
                }
            } catch (Exception e) {
                Log.err(e);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            each((k, v) -> {
                if(SettingsInfos.containsKey(k)){
                    builder.append("\n").append("//***//" + SettingsInfos.get(k) + "//***//").append("\n").append(k).append(": ").append(v).append("\n");
                }else {
                    builder.append("\n").append(k).append(": ").append(v).append("\n");
                }
            });

            return builder.toString();
        }
    }
}
