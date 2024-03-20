package 实际内容.主要;

import arc.files.Fi;
import arc.struct.Seq;
import arc.struct.StringMap;
import arc.util.Log;
import arc.util.serialization.Jval;

import static 核心.HuanXiangMOD.数据目录;

public class 配置 {
    public static final Seq<配置> all = new Seq<>();
    public static final StringMap BSS = new StringMap();
    public int id;
    public Fi 配置;
    public 配置 配置s(){
        return this;
    }
    public 配置(String 配置名称){
        配置=配置名称(配置名称);
        设置.loadFi(配置);
        id = all.size;
        all.add(this);
    }
    public Data 设置 = new Data();
    public static Fi 配置名称(String 配置名称){
       return 数据目录.child(配置名称 + ".hjson");
    }
    public  boolean 布尔值(String key,String value){
        if(获取(key) == null){
            设置(key, value);
        }
        return   BSS.getBool(key);
    }
    public  int Int(String key,String value){
        if(获取(key) == null){
            设置(key, value);
        }
        return BSS.getInt(key);
    }
    public  float Float(String key,String value){
        if(获取(key) == null){
            设置(key, value);
        }
        return BSS.getFloat(key);
    }
    public String String(String key,String value){
        if(获取(key) == null){
            设置(key, value);
        }
        return BSS.get(key);
    }
    public void 设置(String key, String value){
        设置.put(key, value);
    }

    public String 获取(String key){
        return 设置.get(key);
    }


    public class Data extends StringMap {
        @Override
        public String put(String key, String value) {
            String old = super.put(key, value);
            配置.writeString(String.valueOf(this));
            return old;
        }

        public void loadFi(Fi fi) {
            if (!fi.exists()) return;
            try {
                for(Entry<String, Jval> aa : Jval.read(fi.readString()).asObject()){
                    super.put(aa.key, String.valueOf(aa.value));
                    BSS.put(aa.key,String.valueOf(aa.value));
                }
            } catch (Exception e) {
                Log.err(e);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            each((k, v) -> {
                    builder.append("\n").append(k).append(": ").append(v).append("\n");
            });

            return builder.toString();
        }
    }
}