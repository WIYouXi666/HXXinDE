package 实际内容.主要;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;

public class 主要 {

    public static final Seq<主要> all = new Seq<>();
    public static ObjectMap<String, Float> alll = new ObjectMap<>();






    public String name;
    public int id;
    public @Nullable String icon;
    public @Nullable float 数 = 0;
    public 主要(String name, String icon,float 数) {
        this.name = name;
        this.icon = icon;
        this.数 = 数;
        alll.put(name,数);
        id = all.size;
        all.add(this);
    }



}
