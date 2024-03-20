package 实际内容.content;

import arc.util.Log;
import 实际内容.主要.配置;

//TODO
public class 全部配置 {
    public static void load() {
        new 配置("配置测试"
        ){{
            Log.info(""+布尔值("sfddsfs","true"));
        }};
        new 配置("配置测试"
        ){{
            布尔值("fgdgfdfgdgsads","false");
        }};
        new 配置("配置测试"
        ){{
            布尔值("fgdgfdffghfhfghggfgggdgsads","false");
        }};
    }
}
