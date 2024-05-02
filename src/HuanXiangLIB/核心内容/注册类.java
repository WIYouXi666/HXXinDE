package HuanXiangLIB.核心内容;

import HuanXiangDIY.type.能量;
import arc.struct.ObjectMap;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Effect;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.UnitController;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;

import java.util.ArrayList;

import static HuanXiangLIB.核心内容.总集类.*;

public class 注册类 {


    public static class 配方 {

        public 条目 输入;
        public 条目 输出;
        public float 工艺时间 = 0f;
        public Effect craftEffect = Fx.none;
        public boolean 额外输出类型 = true;
        public 条目 额外输出;
        public float 额外产出几率;
        public 条目 强化输入;
        public 条目 催化输入;




        static int id = 0;
        static String name;
        public 配方(String A) {

            name = A;
            配方组.add(this);
            id = nextId();
            配方映射.put(name, this);
        }

        int nextId() {
            return id++;
        }

        public static class 条目 {
            public  final ObjectMap<HuanXiangDIY.type.能量, Float> 能量 =  new ObjectMap<>();
            public 条目 添加能量(能量 st, float b) {
                float dd =0;
                dd = b<0? 0:b;
                能量.put(st,dd);
                return this;
            }
            public @Nullable ItemStack[] items;
            public @Nullable LiquidStack[] liquids;
            public 条目() {
            }
            public 条目 物品(ItemStack[] items) {
                this.items = items;
                return this;
            }
            public 条目 流体(LiquidStack[] liquids) {
                this.liquids = liquids;
                return this;
            }




        }
    }



















    public static class 单位技能 {
        Unit u;
        static int id = 0;
        static String name;
        Runnable 执行;

        public 单位技能(String A, Runnable b) {
            执行 = b;
            name = A;
            单位技能组.add(this);
            id = nextId();
            单位技能映射.put(name, this);
        }

        int nextId() {
            return id++;
        }


    }

    public static class 辞典 {

        static int id = 0;
        static String name;

        public 辞典(String A) {
            name = A;
            辞典组.add(this);
            id = nextId();
            辞典映射.put(id, this);
        }

        int nextId() {
            return id++;
        }

        public static void 注册辞典(UnlockableContent 内容, 注册类.辞典... B) {
            ArrayList<辞典> A = new ArrayList<>();
            ArrayList<UnlockableContent> J = new ArrayList<>();
            if (内容含有的辞典.containsKey(内容)) {
                A = 内容含有的辞典.get(内容);
            }
            for (注册类.辞典 C : B) {
                if (!A.contains(C)) {
                    A.add(C);
                }
            }
            内容含有的辞典.put(内容, A);
            for (注册类.辞典 ss : B) {
                if (辞典含有的内容.containsKey(ss)) {
                    J = 辞典含有的内容.get(ss);
                }
                J.add(内容);
                辞典含有的内容.put(ss, J);

            }


        }


    }



    public static void 注册能力(String name, Ability A) {
        能力注册.put(name, A);
    }
    public static void 注册挂载(String name, WeaponMount A) {
        挂载注册.put(name, A);
    }
    public static void 注册操作(String name, UnitController A) {
        操作注册.put(name, A);
    }



    public static class 单位组装 {

        public Ability[] 能力 = new Ability[0];
        public WeaponMount[] 挂载 = new WeaponMount[0];
        public UnitController[] 操作 = new UnitController[0];

        static int id = 0;
        static String name;

        public 单位组装(String A) {
            name = A;
            单位组装组.add(this);
            id = nextId();
            单位组装映射.put(id, this);
        }

        public void 能力(String... a) {
            String[] GG = new String[a.length];
            int b = 0;
            for (String s : a) {
                if (能力注册.containsKey(s)) {
                    GG[b] = s;
                    b++;
                }
            }

            能力 = new Ability[b];
            b = 0;
            for (String s : GG) {
                能力[b] = 能力注册.get(s);
                b++;
            }
        }

        public void 挂载(String... a) {
            String[] GG = new String[a.length];
            int b = 0;
            for (String s : a) {
                if (挂载注册.containsKey(s)) {
                    GG[b] = s;
                    b++;
                }
            }
            挂载 = new WeaponMount[b];
            b = 0;
            for (String s : GG) {
                挂载[b] = 挂载注册.get(s);
                b++;
            }

        }

        public void 操作(String... a) {
            String[] GG = new String[a.length];
            int b = 0;
            for (String s : a) {
                if (操作注册.containsKey(s)) {
                    GG[b] = s;
                    b++;
                }
            }
            操作 = new UnitController[b];
            b = 0;
            for (String s : GG) {
                操作[b] = 操作注册.get(s);
                b++;
            }

        }


        int nextId() {
            return id++;
        }

    }


}
