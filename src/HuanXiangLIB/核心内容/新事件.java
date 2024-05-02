package HuanXiangLIB.核心内容;

import HuanXiangDIY.事件.局内事件主类;

public class 新事件 {
    public enum 事件枚举{
        修正记录变更

    }
    //局内游戏事件
    public static class 功能事件 {
        public final 局内事件主类 ip;
        public 功能事件(局内事件主类 ip) {
            this.ip = ip;
        }
    }

}
