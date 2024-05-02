package HuanXiangDIY.主要;

import arc.graphics.Color;
import arc.util.Time;
import arc.util.Tmp;

public class 颜色 extends Color{

    public static 颜色
    热色 = new 颜色(1f, 0.22f, 0.22f, 0.8f),
    白色 = new 颜色("FFFFFF"),
    黑色 = new 颜色("000000"),
    黑白色 = new 颜色(白色, 黑色, (float) Math.cos(Time.time * 0.01f)),
    金色传说 = new 颜色("FFEA00");
    public 颜色(float r, float g, float b, float a) {
        super(r,g,b,a);
    }
    public 颜色(String a) {
        Color.valueOf("#" + a);
    }

    public 颜色(String 默认, String 目标, float 差值) {
        Tmp.c1.set(new 颜色(默认)).lerp(new 颜色(目标), 差值);
    }

    public 颜色(颜色 默认, 颜色 目标, final float 差值) {
        Tmp.c1.set(默认).lerp(目标, 差值);
    }

    public 颜色(String 默认, String 目标, final float r, final float g, final float b, final float a, final float 差值) {
        Tmp.c1.set(new 颜色(默认)).lerp(r, g, b, a, 差值);
    }
    public 颜色(String 默认, String 目标, String 目标2, final float 差值, final float 差值2) {
        Tmp.c1.set(new 颜色(默认)).lerp(new 颜色(目标), 差值).lerp(new 颜色(目标2), 差值2);
    }


}

