package 核心.ui类;

import arc.scene.Element;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;

public  class ui内容主体 implements Comparable<ui内容主体>{
    public static final Seq<ui内容主体> all = new Seq<>();
    public Seq<Element> alll = new Seq<>();
    public int id;
    public String name;
    public boolean 设置禁用 = false;
    public ui内容主体(String name) {
        id = all.size;
        all.add(this);
    }
    public void 加载(Table table) {
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(ui内容主体 o) {
        return id - o.id;
    }


}
