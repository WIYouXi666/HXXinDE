package MODS.关于文件.关于地图保存;

import arc.struct.ObjectMap;
import cn.hutool.core.map.multi.RowKeyTable;

import static MODS.HX_Vars.保存键表;
import static MODS.HX_Vars.当前地图全部信息;


public class 保存的信息 {


    static String 未知 = "未知";

    static RowKeyTable<String, String, String> dafsdf = new RowKeyTable<>();
    static ObjectMap<String, ObjectMap<String, String>> 分类的全部信息 = new ObjectMap<>();

    public void set全部信息(ObjectMap<String, String> a) {
        当前地图全部信息 = a;
    }


    public void init() {
        for (String 类型 : 保存键表) {
            信息分类(类型);
        }
    }


    public String get(String 分类, String 键) {
        return 分类的全部信息.containsKey(分类) ? 分类的全部信息.get(分类).containsKey(键) ? 分类的全部信息.get(分类).get(键) : null : null;
    }

    public String get(String 键) {
        return 当前地图全部信息.containsKey(键) ? 当前地图全部信息.get(键) : null;
    }

    public void add(String 键, String 值) {
        当前地图全部信息.put(键, 值);
    }


    public void clear() {
        当前地图全部信息.clear();
        分类的全部信息.clear();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 保存键表.size; i++) {
            if (分类的全部信息.containsKey(保存键表.get(i))) {
                分类的全部信息.get(保存键表.get(i)).each((k, v) -> builder.append("\n").append(k).append(" : ").append(v).append("\n"));

            }
        }
        if (分类的全部信息.containsKey(未知)) {
            分类的全部信息.get(未知).each((k, v) -> builder.append("\n").append(k).append(" : ").append(v).append("\n"));
        }

        return builder.toString();
    }

    public void 信息分类(String 标签) {
        ObjectMap<String, String> 集合 = new ObjectMap<>();
        for (ObjectMap.Entry<String, String> stringStringEntry : 当前地图全部信息) {
            String l = stringStringEntry.key;
            String v = stringStringEntry.value;
            if (l.contains(标签)) {
                int 标签长度 = 标签.length();
                String name = l.substring(0, 标签长度 - 1);
                集合.put(name, v);
                分类的全部信息.put(标签, 集合);
            } else {
                集合.put(l, v);
                ObjectMap<String, String> pp = 分类的全部信息.containsKey(未知) ? 分类的全部信息.get(未知) : new ObjectMap<>();
                pp.putAll(集合);
                分类的全部信息.put(未知, pp);
            }
        }

    }


}
