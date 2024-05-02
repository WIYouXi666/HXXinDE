package HuanXiangLIB;

import arc.Core;
import arc.Events;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.core.NetClient;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Player;
import mindustry.gen.SendChatMessageCallPacket;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;

import static mindustry.Vars.*;
import static mindustry.content.StatusEffects.none;
import static mindustry.gen.Call.sendChatMessage;


public class libxx {







    public static Vec2 get相机位置() {
        return Core.camera.position;
    }

    public static void gn控制相机位置(float x, float y, float 速度, float 缩放) {
        control.input.logicCutscene = true;
        control.input.logicCamPan.set(world.unconv(x), world.unconv(y));
        control.input.logicCamSpeed = 速度;
        control.input.logicCutsceneZoom = 缩放;
    }

    public static void gn关闭控制相机位置() {
        control.input.logicCutscene = false;
    }

    public static void add逻辑标签(String flag) {
        state.rules.objectiveFlags.add(flag);

    }

    public static void remove逻辑标签(String flag) {
        state.rules.objectiveFlags.remove(flag);
    }

    public static boolean get逻辑标签(String flag) {
        return state.rules.objectiveFlags.contains(flag);
    }

    public static float get世界宽度() {
        return world.width();
    }

    public static float get世界高度() {
        return world.height();
    }


    public static boolean get世界生成() {
        return world.isGenerating();
    }


    // public static ui ui;
//    public void sdfgdsg() {
//        new ContentParser(){{
//            //this.parse()
//                 //   this.markError();
//        }};
//
//    }

    public static String get粘贴板() {
        return Core.app.getClipboardText().replace("\r\n", "\n");
    }

    public static void set粘贴板(String A) {
        Core.app.setClipboardText(A);
    }

    /**
     * Runnable  == () -> {} 套
     */
    public static float get获得1秒() {
        return Time.delta;
    }

    public static float get计时器() {
        return Time.time;
    }


    public static void ui解锁通知(UnlockableContent content) {
        Vars.ui.hudfrag.showUnlock(content);
    }

    public static void ui详细信息(UnlockableContent content) {
        Vars.ui.content.show(content);
    }

    public static void ui显示警告(String text) {

        Vars.ui.showErrorMessage(gn翻译(text));
    }

    public static void ui创建加载(String text) {
        ui.loadfrag.show(text);//加载
    }

    public static void ui关闭加载() {
        ui.loadfrag.hide();//加载
    }

    /**
     * 在屏幕中间显示文本，然后淡出。
     */
    public static void ui宣布(String 内容, float 时间) {
        ui.announce(内容, 时间);
    }

    public static void ui显示确认(String 标题, String 内容, Runnable 执行) {
        ui.showConfirm(标题, 内容, 执行);
    }


    /**
     * ui.chatfrag.addMessage(text);
     */
    public static void gn添加聊天信息(String text) {
        Events.fire(new EventType.ClientChatEvent(text));
        sendChatMessage(text);

    }

    public static void gn添加聊天信息(Player 玩家, String text) {
        Events.fire(new EventType.ClientChatEvent(text));
        if (Vars.net.server() || !Vars.net.active()) {
            NetClient.sendChatMessage(玩家, text);
        }
        if (Vars.net.client()) {
            SendChatMessageCallPacket packet = new SendChatMessageCallPacket();
            packet.message = text;
            Vars.net.send(packet, true);
        }

    }

    /**
     * 其他
     * Core.bundle.format("", "数组");//"测试{0}测试测试测试{1}{2}"
     * Core.bundle.get("1");//未在bundle定义时，return ???内容???
     * Core.bundle.get("1", "2");//未在bundle定义时，return 2
     * Core.bundle.getOrNull("1"); //未在bundle定义时，return null
     * Core.bundle.getNotNull("1");//未在bundle定义时，抛出
     * <p>
     * <p>
     * 未在 bundle 定义时，return ???内容 ???
     *
     * @return
     */
    public static String gn翻译(String text) {
        return Core.bundle.get(text, text);
    }

    /**
     * "测试 {0} 测试测试测试 {1} {2} "
     *
     * @return
     */
    public static String gn翻译(String text, Object... args) {
        return Core.bundle.format(text, args);
    }

    public static void gn延时执行(float 时间, Runnable r) {
        Time.runTask(时间 * 60f, r);
    }


    public static void gn创建单位(UnitType unit, Team team, float x, float y, float 角度, StatusEffect 状态, float 时间) {
        if (Vars.state.isGame()) {
            Unit u = unit.create(team);
            u.set(x, y);
            u.rotation = 角度;
            Events.fire(new EventType.UnitCreateEvent(u, null));
            if (!Vars.net.client()) {
                u.add();
                u.apply(状态, 时间);

                u.apply(none);//添加
                u.unapply(none);//清除
                u.hasEffect(none);//有吗
                u.isImmune(none);//免疫
            }

        }


    }


}
