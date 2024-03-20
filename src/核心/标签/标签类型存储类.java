package 核心.标签;

import arc.struct.ObjectMap;
import arc.util.Nullable;
import mindustry.ctype.UnlockableContent;

import java.util.ArrayList;

import static 核心.工具方法.集合A;
import static 核心.工具方法.集合去重;

public class 标签类型存储类 {

    /**
     * 一个可解锁内容包含的标签
     */
    private static final ObjectMap<UnlockableContent, ArrayList<核心标签>> 内容标签 = ObjectMap.of();
    /**
     * 同属一个标签的可解锁内容
     */
    private static final ObjectMap<核心标签, ArrayList<UnlockableContent>> 标签内容 = ObjectMap.of();

    public static void 添加标签(UnlockableContent 内容, 核心标签 AAA) {
        添加标签(内容,集合A(AAA));
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
    public static void 清空() {
        内容标签.clear();
        标签内容.clear();
    }

}
