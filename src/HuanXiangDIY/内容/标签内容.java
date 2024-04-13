package HuanXiangDIY.内容;

import HuanXiangLIB.libxx;
import mindustry.content.Items;

import static HuanXiangLIB.记录$功能.标签lib.标签.add;
import static HuanXiangLIB.记录$功能.标签lib.标签.get;
import static HuanXiangLIB.记录$功能.标签lib.添加标签;
import static HuanXiangLIB.记录$功能.标签lib.清空;

public class 标签内容 {
    {
     libxx.加载YY.add标签创建(()->{
         add("空");
         add("jk");
     });
    }
    //TODO 创建//设置  分开？
    public static void 创建(){
        add("空");
        add("jk");




















    }
    public static void 设置(){
        清空();
        添加标签(Items.copper, get("空"));
















    }

}
