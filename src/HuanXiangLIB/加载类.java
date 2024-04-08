package HuanXiangLIB;

import arc.struct.Seq;
import mindustry.Vars;

public class 加载类 {
    public static Seq<Runnable> 标签创建 = new Seq<>();
    public static Seq<Runnable> 标签设置 = new Seq<>();

        public  void run标签创建() {

            for (Runnable a : 标签创建) {
                try {
                    a.run();
                } catch (Throwable e) {
                    Vars.ui.showException(e);
                }

            }


        }
        public  void add标签创建(Runnable a) {
           标签创建.add(a);
        }
        public  void run标签设置() {
            for (Runnable a : 标签设置) {
                try {
                    a.run();
                } catch (Throwable e) {
                    Vars.ui.showException(e);
                }
            }
        }
        public  void add标签设置(Runnable a) {
            标签设置.add(a);
        }



}
