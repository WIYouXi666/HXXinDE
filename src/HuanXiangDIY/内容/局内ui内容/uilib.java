package HuanXiangDIY.内容.局内ui内容;

import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;

public abstract class uilib {
    public static final ObjectMap<String, uilib> ui映射 = new ObjectMap<>();

    public static String name;
    public static boolean 设置禁用 = false;

    public uilib(String name) {
        this.name = name;
        ui映射.put(this.name, this);
    }

    public abstract  void 加载(Table table);


}
