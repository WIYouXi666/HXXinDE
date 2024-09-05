package MODS.一些方法;

import arc.Core;
import arc.scene.ui.Button;

import static arc.Core.scene;
import static mindustry.gen.Call.sendChatMessage;

public class 方法 {
    public static final Button.ButtonStyle infoStyle = scene.getStyle(Button.ButtonStyle.class);

    public static void 测试聊天(String txt) {
        if (Core.settings.getBool("CeShi")) {
            sendChatMessage(txt);
        }
    }
}
