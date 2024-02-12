package HuanXiang.核心内容.事件;

import arc.Core;
import arc.struct.Seq;
import arc.util.Nullable;

public abstract class 局内事件主类 implements Comparable<局内事件主类>{
    public static final Seq<局内事件主类> all = new Seq<>();
    public String name;
    public int id;
    public @Nullable float 数 = 0;
    public 局内事件主类(String name) {
        this.name = Core.bundle.get("局内事件." + name + ".name");
        id = all.size;
        all.add(this);
    }
    public void 执行() {
    }
    @Override
    public String toString() {
        return Core.bundle.get("局内事件." + name + ".name");
    }

    @Override
    public int compareTo(局内事件主类 o) {
        return id - o.id;
    }

}
