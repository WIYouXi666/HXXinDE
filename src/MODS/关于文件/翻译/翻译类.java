package MODS.关于文件.翻译;

import arc.struct.ObjectMap;

import static MODS.HX_Vars.翻译类表;

public class 翻译类 {
    public String 语言;
    String 作者;
    String 介绍;
    ObjectMap<String, String> 属性 = new ObjectMap<>();


    public 翻译类(String 语言, String 作者, String 介绍) {
        this.语言 = 语言;
        this.作者 = 作者;
        this.介绍 = 介绍;
        翻译类表.add(this);
    }


    public void set数据(String... values) {
        for (int i = 0; i < values.length / 2; ++i) {
            属性.put(values[i * 2], values[i * 2 + 1]);
        }
    }
}
