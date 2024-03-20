package 核心.标签;

import arc.struct.ObjectMap;

public class 核心标签 {


    public String name;
    public int id;

    public static 核心标签[] all = {};
    public static final ObjectMap<String, 核心标签> 核心标签映射 =  new ObjectMap<>();
    public static 核心标签 核心标签内容(String st) {
        return 核心标签映射.get(st) == null? 核心标签映射.get("空"): 核心标签映射.get(st);
    }
    public 核心标签(String name) {
        this.name = name;
        核心标签映射.put(name, this);
    }
    核心标签(int id, String name){
        this.id = id;
        this.name = name;
    }


    public static 核心标签 get(String name){

        if (!核心标签映射.containsKey(name)) {
            return 核心标签映射.getThrow("空", () -> new IllegalArgumentException("标签系统加载错误"));
        } else {
            return 核心标签映射.get(name);
        }

    }


    public static boolean exists(String name){
        return 核心标签映射.containsKey(name);
    }

    public static 核心标签 add(String name){
        核心标签 a = new 核心标签(all.length, name);
        核心标签[] prev = all;
        all = new 核心标签[all.length + 1];
        System.arraycopy(prev, 0, all, 0, a.id);
        all[a.id] = a;
        核心标签映射.put(name, a);
        return a;
    }
    @Override
    public String toString() {
       return name;
    }

}
