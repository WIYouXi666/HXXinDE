package HuanXiangDIY.事件;

public class 自建事件 {
    public enum 事件{
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
