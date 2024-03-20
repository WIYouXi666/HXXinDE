package 核心;

import 核心.ui类.交互窗.添加的信息;
import 核心.ui类.局内ui主体;
import 核心.ui类.替换原版ui;
import 核心.内容.标签内容;
import 核心.标签.全局标签数值映射;

public class 加载方法 {
    public static void 异步加载() {


        局内ui主体.局内ui();


        标签内容.设置();

        全局标签数值映射.执行();

        添加的信息.执行();

        替换原版ui.执行();



    }
}
