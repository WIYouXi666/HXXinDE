package 实际内容.主要;

import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;

import java.util.Locale;

public class 属性 implements Comparable<属性> {

    public static final Seq<属性> all = new Seq<>();
    public static ObjectMap<String, Float> alll = new ObjectMap<>();
    public static 属性
    空 = new 属性("空"),
    毁灭= new 属性("毁灭",4),
    致命= new 属性("致命",2),
    脆弱= new 属性("脆弱",  1.5f),
    一般= new 属性("一般",1),
    耐性= new 属性("耐性", 0.5f),
    抵抗= new 属性("抵抗", 0.25f),
    无效= new 属性("无效",0),
    吸收= new 属性("吸收",-1);







    public String name;
    public int id;
    public @Nullable String icon;
    public @Nullable float 数 = 0;
    public 属性(String name, String icon,float 数) {
        this.name = name;
        this.icon = icon;
        this.数 = 数;
        alll.put(name,数);
        id = all.size;
        all.add(this);
    }

    public 属性(String name) {
        this(name, null,0);
    }
    public 属性(String name,String icon) {
        this(name, icon,0);
    }
    public 属性(String name,float 数) {
        this(name, null,数);
    }
    public String localized() {
        return Core.bundle.get("属性." + name.toLowerCase(Locale.ROOT));
    }

    @Override
    public String toString() {
            return Core.bundle.get("属性." + this.name + ".name", this.name);
    }

    @Override
    public int compareTo(属性 o) {
        return id - o.id;
    }
}
