package MODS.关于文件.配置文件;

import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.util.serialization.Jval;

import static MODS.HX_Vars.*;


public class 配置基本 {
    String name;
    public ObjectMap<String, String> 配置信息 = new ObjectMap<>();
    public Fi 配置;

    public 配置基本(String name) {
        this.name = name;
        配置表.add(this);
    }

    public void add配置(String J, Object k) {
        配置信息.put(J, String.valueOf(k));

    }

    public void remove(String J) {
        if (配置信息.containsKey(J)) {
            配置信息.remove(J);
        }
    }

    public void clear() {
        配置信息.clear();

    }




    public String get(String H) {
        if (配置信息.containsKey(H)) {
            Object HJ = 配置信息.get(H);
            return String.valueOf(HJ);
        }
        return "555555~~~~";
    }
    public void 重载() {
        Fi HH = 配置文件.child(name + VN);
        HH.writeString(this.toString());
    }
    public void 加载() {
        配置 = 配置文件.child(name + VN);
        if (配置.exists()) {
            clear();
            Jval.JsonMap zz = Jval.read(配置.readString()).asObject();
            for (ObjectMap.Entry<String, Jval> cc : zz) {
                全部配置信息.put(cc.key, cc.value.asString());
                配置信息.put(cc.key, cc.value.asString());
            }
        } else {
            重载();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        配置信息.each((k, v) -> {
            builder.append("\n").append(k).append(" : ").append(v).append("\n");
        });

        return builder.toString();
    }


}
