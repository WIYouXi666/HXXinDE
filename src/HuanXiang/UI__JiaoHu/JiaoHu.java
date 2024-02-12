package HuanXiang.UI__JiaoHu;

import arc.Core;
import arc.scene.Element;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;

public  class JiaoHu implements Comparable<JiaoHu>{
    public static final Seq<JiaoHu> all = new Seq<>();
    public Seq<Element> alll = new Seq<>();
    public int id;
    public String name;
    public boolean ShiFouJiaZai = true;
    public JiaoHu(String name) {
        this.name = Core.bundle.get("交互." + name + ".name");;
        id = all.size;
        all.add(this);
    }
    public void JiaZaiSheZhi(Table table) {
    }
    @Override
    public String toString() {
        return Core.bundle.get("交互." + name + ".name");
    }

    @Override
    public int compareTo(JiaoHu o) {
        return id - o.id;
    }


}
