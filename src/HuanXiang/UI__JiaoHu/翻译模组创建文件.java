package HuanXiang.UI__JiaoHu;

import HuanXiang.主要.翻译;

import static HuanXiang.HuanXiangMOD.中文;
import static HuanXiang.HuanXiangMOD.主要;

public class 翻译模组创建文件 {
    public static void load() {
        主要.writeString("{\n");
        主要.writeString("\"name\":\"hx-fy\",\n", true);
        主要.writeString("\"displayName\":\"幻想-翻译\",\n", true);
        主要.writeString("\"format\":\"自由\",\n", true);
        主要.writeString("\"description\":\"这是测试><\",\n", true);
        主要.writeString("\"minGameVersion\":\"145\"\n", true);
        主要.writeString("}", true);
        中文.writeString("\n\n\n#mod作者：自由#\n\n\n");
        中文.writeString("#[#\n", true);
        翻译.all.each(翻译 -> {
            中文.writeString("\n#翻译作者：" + 翻译.作者 + " [][][][][] ", true);
            中文.writeString("翻译的mod：" + 翻译.mod, true);
        });
        中文.writeString("\n\n#]#\n", true);
        翻译.all.each(翻译 -> {
            中文.writeString("\n#翻译作者：" + 翻译.作者 + "#", true);
            中文.writeString("\n#翻译的mod：" + 翻译.mod + "#\n", true);
            翻译.集合.each(TT -> {
                中文.writeString("\n" + TT + "\n", true);
            });
        });

    }
}
