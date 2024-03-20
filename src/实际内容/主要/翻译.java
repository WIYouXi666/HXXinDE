package 实际内容.主要;

import arc.struct.Seq;

public class 翻译 {
    public Seq<String> 集合 = new Seq<>();
    public static final Seq<翻译> all = new Seq<>();
    public String 作者 = "";
    public String mod = "";
    public int id;
    public 翻译(String A,String mod) {
       this.作者 = A;
       this.mod = mod;
       id = all.size;
       all.add(this);
    }
    public 翻译(String A) {
        this(A,"");

    }
    public 翻译 添加(String A) {
            集合.add(A);
        return this;
    }

}
