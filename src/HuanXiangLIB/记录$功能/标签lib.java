package HuanXiangLIB.记录$功能;

import arc.struct.ObjectMap;
import arc.util.Nullable;
import mindustry.ctype.UnlockableContent;
import mindustry.type.Item;

import java.util.ArrayList;

import static HuanXiangLIB.工具方法.集合A;
import static HuanXiangLIB.工具方法.集合去重;

public class 标签lib {


    private static final ObjectMap<String, 标签> 核心标签映射 = ObjectMap.of(
            "空", new 标签(0, "空")
    );

    /**
     * 一个可解锁内容包含的标签
     */
    private static final ObjectMap<UnlockableContent, ArrayList<标签>> 内容标签 = ObjectMap.of();
    /**
     * 同属一个标签的可解锁内容
     */
    private static final ObjectMap<标签, ArrayList<UnlockableContent>> 标签内容 = ObjectMap.of();

    public static class 标签 {


        public String name;
        Item m;
        public int id;

        public static 标签[] all = {};


        public static 标签 核心标签内容(String st) {
            return 核心标签映射.get(st) == null ? 核心标签映射.get("空") : 核心标签映射.get(st);
        }

        标签(int id, String name) {
            this.id = id;
            this.name = name;
            /**
             * 感觉无意义
             * */
//            m = new Item(name + id) {
//                @Override
//                public void loadIcon() {
//                    super.loadIcon();
//                    if (this.frames > 0) {
//                        TextureRegion[] regions = new TextureRegion[this.frames * (this.transitionFrames + 1)];
//                        int i;
//                        if (this.transitionFrames <= 0) {
//                            for(i = 1; i <= this.frames; ++i) {
//                                regions[i - 1] = Core.atlas.find(this.name + i);
//                            }
//                        } else {
//                            for(i = 0; i < this.frames; ++i) {
//                                regions[i * (this.transitionFrames + 1)] = Core.atlas.find(this.name + (i + 1));
//
//                                for(int j = 1; j <= this.transitionFrames; ++j) {
//                                    int index = i * (this.transitionFrames + 1) + j;
//                                    regions[index] = Core.atlas.find(this.name + "-t" + index);
//                                }
//                            }
//                        }
//
//                        this.fullIcon = new TextureRegion(this.fullIcon);
//                        this.uiIcon = new TextureRegion(this.uiIcon);
//                        Events.run(EventType.Trigger.update, () -> {
//                            int frame = (int)(Time.globalTime / this.frameTime) % regions.length;
//                            this.fullIcon.set(regions[frame]);
//                            this.uiIcon.set(regions[frame]);
//                        });
//                    }
//
//                }
//                @Override
//                public boolean isHidden() {
//                    return true;
//                }
//
//                @Override
//                public void unlock() {
//                }
//                @Override
//                public boolean unlocked() {
//                    return false;
//                }
//
//                @Override
//                public void clearUnlock() {
//                }
//
//                @Override
//                public boolean unlockedNow() {
//                    return false;
//                }
//
//                @Override
//                public boolean locked() {
//                    return true;
//                }
//
//            @Override
//            public void quietUnlock () {
//            }
//            {
//                frames = 标签内容.get(获取标签(this.name)).size();
//
//                unlocked = false;
//                generateIcons = false;
//                alwaysUnlocked = false;
//                buildable = false;
//                hidden = true;
//            }
//        }
//
//        ;
    }


    public static 标签 get(String name) {

        if (!存在(name)) {
            return 核心标签映射.get("空");
        } else {
            return 核心标签映射.get(name);
        }

    }


    public static boolean 存在(String name) {
        return 核心标签映射.containsKey(name);
    }

    public static 标签 add(String name) {
        标签 a = new 标签(all.length, name);
        标签[] prev = all;
        all = new 标签[all.length + 1];
        System.arraycopy(prev, 0, all, 0, a.id);
        all[a.id] = a;
        核心标签映射.put(name, a);
        return a;
    }

    @Override
    public String toString() {
        return name;
    }

}

public static void 创建标签(String A) {
    标签.add(A);
}

public static 标签 获取标签(String A) {
    return 标签.get(A);
}

public static void 添加标签(UnlockableContent 内容, 标签 AAA) {
    添加标签(内容, 集合A(AAA));
}

public static void 添加标签(UnlockableContent 内容, ArrayList<标签> AAA) {
    if (内容标签.containsKey(内容)) {
        ArrayList<标签> A = 内容标签.get(内容);
        A.addAll(AAA);
        A = 集合去重(A);
        内容标签.remove(内容);
        内容标签.put(内容, A);
    } else {
        内容标签.put(内容, AAA);
    }
    for (标签 ss : AAA) {
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

public static void 删除标签(UnlockableContent 内容, @Nullable ArrayList<标签> AAA) {
    if (内容标签.containsKey(内容)) {
        if (AAA == null) {
            AAA = 内容标签.get(内容);
            内容标签.remove(内容);
        } else {
            内容标签.get(内容).removeAll(AAA);
        }
        for (标签 ss : AAA) {
            标签内容.get(ss).remove(内容);
        }
    }
}

public static void 清空标签(UnlockableContent 内容) {
    if (内容标签.containsKey(内容)) {
        for (标签 ss : 内容标签.get(内容)) {
            标签内容.get(ss).remove(内容);
        }
        内容标签.get(内容).clear();
    }
}

public static 标签[] 检测标签(UnlockableContent... 内容) {
    ArrayList<标签> kk = new ArrayList<>();
    for (UnlockableContent i : 内容) {
        try {
            kk.addAll(内容标签.get(i));
        } catch (Exception e) {
            throw new RuntimeException("无效内容" + e);
        }
    }
    标签[] AA = new 标签[kk.size()];
    return 集合去重(kk).toArray(AA);
}


public static UnlockableContent[] 检测内容(标签... 标签) {
    ArrayList<UnlockableContent> kk = new ArrayList<>();
    for (标签 i : 标签) {
        try {
            kk.addAll(标签内容.get(i));
        } catch (Exception e) {
            throw new RuntimeException("无效标签" + e);
        }
    }
    UnlockableContent[] AA = new UnlockableContent[kk.size()];
    return 集合去重(kk).toArray(AA);
}

public static 标签[] 筛选组(标签 A, 标签... a) {
    int aa = 0;
    标签[] Tas = new 标签[a.length];
    for (标签 t : a) {
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
