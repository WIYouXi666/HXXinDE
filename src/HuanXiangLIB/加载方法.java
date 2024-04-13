package HuanXiangLIB;

import HuanXiangDIY.内容.标签内容;
import HuanXiangLIB.ui类.局内ui主体;
import HuanXiangLIB.ui类.添加的信息;
import HuanXiangLIB.单位实体改.单位注册类;

public class 加载方法 {
    public static void 异步加载() {

        new 单位注册类().异步执行();
        局内ui主体.局内ui();


        标签内容.设置();

       // 全局标签数值映射.执行();

        添加的信息.执行();

        //替换原版ui.执行();



    }
}
