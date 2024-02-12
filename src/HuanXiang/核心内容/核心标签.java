package HuanXiang.核心内容;

import arc.Core;
import arc.struct.ObjectMap;
import arc.util.Nullable;
import mindustry.ctype.UnlockableContent;

import java.util.ArrayList;
import java.util.Locale;

import static HuanXiang.KeNengYongDao.集合去重;

public class 核心标签 implements Comparable<核心标签> {

    public static ObjectMap<UnlockableContent, ArrayList<核心标签>> 内容标签 = ObjectMap.of();
    public static ObjectMap<核心标签, ArrayList<UnlockableContent>> 标签内容 = ObjectMap.of();
    public String name;
    public int id;
    public @Nullable String icon;
    private static final ObjectMap<String, 核心标签> 核心标签all =  new ObjectMap<>();
    public static 核心标签 核心标签内容(String st) {
        return 核心标签all.get(st) == null? 核心标签all.get("空"):核心标签all.get(st);
    }
    public 核心标签(String name, String icon) {
        this.name = name;
        this.icon = icon;
        核心标签all.put(name, this);
    }

    public 核心标签() {
    }

    public 核心标签(String name) {
        this(name, null);
    }

    public void load() {
        new 核心标签("空");

        new 核心标签("铜");
        new 核心标签("铅");
        new 核心标签("煤炭");
        new 核心标签("石墨");
        new 核心标签("钛");
        new 核心标签("钍");
        new 核心标签("硅");
        new 核心标签("塑钢");
        new 核心标签("相织布");
        new 核心标签("巨浪合金");
        new 核心标签("孢子荚");
        new 核心标签("沙");
        new 核心标签("爆炸混合物");
        new 核心标签("硫化物");
        new 核心标签("钢化玻璃");
        new 核心标签("废料");
        new 核心标签("裂变产物");
        new 核心标签("铍");
        new 核心标签("钨");
        new 核心标签("氧化物");
        new 核心标签("碳化物");
        new 核心标签("休眠囊肿");
        new 核心标签("水");
        new 核心标签("矿渣");
        new 核心标签("石油");
        new 核心标签("冷冻液");
        new 核心标签("瘤液");
        new 核心标签("芳油");
        new 核心标签("镓");
        new 核心标签("臭氧");
        new 核心标签("氢气");
        new 核心标签("氮气");
        new 核心标签("氰气");

        new 核心标签("暗物质");
        new 核心标签("二氧化碳");
        new 核心标签("二氧化硫");
        new 核心标签("高能粒子");
        new 核心标签("活性粒子");
        new 核心标签("暗能量");


        new 核心标签("熔炼");
        new 核心标签("sadad");


    }

    public static void 添加标签(UnlockableContent 内容, ArrayList<核心标签> AAA) {
        if (内容标签.containsKey(内容)) {
            ArrayList<核心标签> A = 内容标签.get(内容);
            A.addAll(AAA);
            A = 集合去重(A);
            内容标签.remove(内容);
            内容标签.put(内容, A);
        } else {
            内容标签.put(内容, AAA);
        }
        for (核心标签 ss : AAA) {
            if (标签内容.containsKey(ss)) {
                ArrayList<UnlockableContent> A = 标签内容.get(ss);
                A.add(内容);
                A = 集合去重(A);
                标签内容.remove(ss);
                标签内容.put(ss, A);
            } else {
                ArrayList<UnlockableContent> N = new ArrayList<>();
                N.add(内容);
                标签内容.put(ss, N);
            }
        }
    }

    public static void 删除标签(UnlockableContent 内容, @Nullable ArrayList<核心标签> AAA) {
        if (内容标签.containsKey(内容)) {
            if (AAA == null) {
                AAA = 内容标签.get(内容);
                内容标签.remove(内容);
            } else {
                内容标签.get(内容).removeAll(AAA);
            }
            for (核心标签 ss : AAA) {
                标签内容.get(ss).remove(内容);
            }
        }
    }

    public static void 清空标签(UnlockableContent 内容) {
        if (内容标签.containsKey(内容)) {
            for (核心标签 ss : 内容标签.get(内容)) {
                标签内容.get(ss).remove(内容);
            }
            内容标签.get(内容).clear();
        }
    }

    public static 核心标签[] 检测标签(UnlockableContent... 内容) {
        ArrayList<核心标签> kk = new ArrayList<>();
        for (UnlockableContent i : 内容) {
            try {
                kk.addAll(内容标签.get(i));
            } catch (Exception e) {
                throw new RuntimeException("无效内容" + e);
            }
        }
        核心标签[] AA = new 核心标签[kk.size()];
        return 集合去重(kk).toArray(AA);
    }

    public static UnlockableContent[] 检测内容(核心标签... 核心标签) {
        ArrayList<UnlockableContent> kk = new ArrayList<>();
        for (核心标签 i : 核心标签) {
            try {
                kk.addAll(标签内容.get(i));
            } catch (Exception e) {
                throw new RuntimeException("无效标签" + e);
            }
        }
        UnlockableContent[] AA = new UnlockableContent[kk.size()];
        return 集合去重(kk).toArray(AA);
    }

    public static 核心标签[] 筛选组(核心标签 A, 核心标签... a) {
        int aa = 0;
        核心标签[] Tas = new 核心标签[a.length];
        for (核心标签 t : a) {
            if (t == A) break;
            Tas[aa] = t;
            aa++;
        }
        return Tas;
    }

    public String localized() {
        return Core.bundle.get("标签." + name.toLowerCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        if (this.icon == null) {
            return Core.bundle.get("标签." + this.name + ".name", this.name);
        }
        return this.icon;
    }

    @Override
    public int compareTo(核心标签 o) {
        return id - o.id;
    }
}
