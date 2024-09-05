package MODS;

import MODS.关于设置.设置实现;
import MODS.内容.ui.开局加载对话框;
import MODS.内容.统计信息;
import MODS.内容.适配类.替换实体;

public class 实现 {
    public static void 基础加载() {
        统计信息.load();
        测试生成.load();

    }
    public static void 客户端加载() {
        设置实现.load();
        开局加载对话框.show();
        替换实体.load();
    }
    public static void B加载() {

    }
    public static void C加载() {

    }
    public static void D加载() {

    }
    public static void 更新() {

    }

    public static class 异步实现 {
        public static void 异步() {

//            输入处理 = new 输入处理器();
//            输入处理.drawUI();








        }
    }


}
