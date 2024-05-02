package HuanXiangLIB.type;

//
//public class 单位组装记录块 extends Block {
//    public 单位组装记录块() {
//        super("单位组装记录块");
//
//        health = Integer.MAX_VALUE;
//        targetable = false;
//        forceDark = true;
//        solid = true;
//        update = true;
//        category = Category.distribution;
//        buildVisibility = BuildVisibility.shown;
//    }
////    public static void 添加单位映射(int u, String... b) {
////      (  (单位组装记录块Build) 单位映射).put(u, b);
////    }
//
//    public class 单位组装记录块Build extends Building {
//
//         ObjectMap<Integer, String[]> 单位映射 = new ObjectMap<>();
//         Seq<Integer> 标记 = new Seq<>();
//        //         String[] 能力z = new String[0];
////         String[] 挂载z = new String[0];
//        Ability[] 能力 = new Ability[0];
//        武器挂载[] 挂载 = new 武器挂载[0];
//
//
//        int 单位id = -1;
//        public boolean 开始 = false;
//        @Nullable
//        Unit unit;
//        public  void 添加单位映射(int u, String... b) {
//       单位映射.put(u, b);
//    }
//
//
//        @Override
//        public boolean collide(Bullet other) {
//            return false;
//        }
//
//        @Override
//        public void damage(float damage) {
//            super.damage(0f);
//
//        }
//
//        @Override
//        public void read(Reads read, byte revision) {
//            读取映射(read);
////            开始 = true;
//        }
//
//        @Override
//        public void write(Writes write) {
//            写入映射(write, 单位映射);
//        }
//
//        @Override
//        public void update() {
//
//
//                if (单位映射.size == 0) return;
//                重置();
//                for (ObjectMap.Entry<Integer, String[]> a : 单位映射) {
//                    单位id = a.key;
//                    unit = Groups.unit.getByID(单位id);
//                    if (unit == null) {
//                        标记.remove(单位id,false);
//                        单位映射.remove(单位id);
//                        continue;
//                    }
//                    if (a.value.length == 0) {
//                        continue;
//                    }
//                    if(标记.contains(单位id)) continue;
//                    能力 = 能力转换(a.value);
//                    挂载 = 挂载转换(a.value);
//                    unit.abilities = new Ability[能力.length];
//                    for (int i = 0; i < 能力.length; ++i) {
//                        unit.abilities[i] = 能力[i].copy();
//                    }
//                    unit.mounts = new 武器挂载[挂载.length];
//                    for (int i = 0; i < 挂载.length; ++i) {
//                        unit.mounts[i] = 挂载[i];
//                    }
//                    标记.add(单位id);
//                    重置();
//                }
//                重置();
//                开始 = false;
//
//
//        }
//
//        void 写入映射(Writes write, ObjectMap<Integer, String[]> 映射) {
//            write.i(映射.size);
//            for (ObjectMap.Entry<Integer, String[]> 映射z : 映射) {
//                write.i(映射z.key);
//                写入String(write, 映射z.value);
//            }
//        }
//
//        void 读取映射(Reads read) {
//            int u = read.i();
//            for (int i = 0; i<u;i++){
//                int 映射zkey = read.i();
//                String[] a = 读取String(read);
//                单位映射.put(映射zkey, a);
//            }
//
//        }
//
//        void 写入String(Writes write, String[] abilities) {
//            write.b(abilities.length);
//            int a = abilities.length;
//            for (int i = 0; i < a; ++i) {
//                write.str(abilities[i]);
//            }
//
//        }
//
//        String[] 读取String(Reads read) {
//            byte len = read.b();
//            String[] a = new String[len];
//            for (int i = 0; i < len; ++i) {
//                a[i] = read.str();
//            }
//            return a;
//        }
//
//        Ability[] 能力转换(String... a) {
//            ObjectMap<String, Ability> 注册v = 能力注册;
//            int b = 0;
//            for (int i = 0; i < a.length; i++) {
//                if (注册v.containsKey(a[i])) {
//                    b++;
//                }
//            }
//            Ability[] c = new Ability[b];
//            b=0;
//            for (int i = 0; i < a.length; i++) {
//                if (注册v.containsKey(a[i])) {
//                    c[b] = 注册v.get(a[i]);
//                    b++;
//                }
//            }
//            return c;
//        }
//
//        武器挂载[] 挂载转换(String... a) {
//
//            ObjectMap<String, 武器挂载> 注册v = 挂载注册;
//            int b = 0;
//            for (int i = 0; i < a.length; i++) {
//                if (注册v.containsKey(a[i])) {
//                    b++;
//                }
//            }
//            武器挂载[] c = new 武器挂载[b];
//            b=0;
//            for (int i = 0; i < a.length; i++) {
//                if (注册v.containsKey(a[i])) {
//                    c[b] = 注册v.get(a[i]);
//                    b++;
//                }
//            }
//            return c;
//
//        }
//
//
//        void 重置() {
////            能力z = new String[0];
////            挂载z = new String[0];
//            能力 = new Ability[0];
//            挂载 = new 武器挂载[0];
//            单位id = -1;
//            unit = null;
//        }
//
//
//    }
//
//
//}
