package MODS.关于输入;

import MODS.ui.块配置片段;
import arc.Core;
import arc.input.GestureDetector;
import arc.input.InputProcessor;
import arc.input.KeyCode;
import arc.math.geom.Vec2;
import arc.scene.event.Touchable;
import arc.scene.ui.layout.WidgetGroup;
import arc.util.Nullable;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.world.Tile;

import static MODS.HX_Vars.WAI_Building;
import static MODS.HX_Vars.点击的Tile;
import static MODS.core内容.绘制draw.tileAt;
import static mindustry.Vars.player;

public class 关于输入处理 implements InputProcessor, GestureDetector.GestureListener {
    public 块配置片段 config = new 块配置片段();
    private final WidgetGroup group = new WidgetGroup();

    public 关于输入处理() {

        group.touchable = Touchable.childrenOnly;
        Core.input.getInputProcessors().remove(i -> i instanceof 关于输入处理 || (i instanceof GestureDetector && ((GestureDetector) i).getListener() instanceof 关于输入处理));
        Core.input.addProcessor(new GestureDetector(20, 0.5f, 0.3f, 0.15f, this));
        Core.input.addProcessor(this);
        group.setFillParent(true);
        Vars.ui.hudGroup.addChildBefore(Core.scene.find("overlaymarker"), group);
        config.build(group);
    }


    /**
     * 拖拽
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * 划动
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }


    /**
     * 任何点击
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, KeyCode button) {

        return false;
    }

    /**
     * 松开
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, KeyCode button) {
//        Tile cursor = tileAt(screenX, screenY);
//        Tile linked = cursor.build == null ? cursor : cursor.build.tile;
//        Log.info(cursor.toString());
        //config.showConfig(linked.build);
        return false;
    }


    /**
     * 点击
     */
    @Override
    public boolean touchDown(float x, float y, int pointer, KeyCode button) {
        return false;
    }

    /**
     * 点击
     */
    @Override
    public boolean tap(float x, float y, int count, KeyCode button) {
        点击的Tile = tileAt(x, y);
        Tile i = 点击的Tile.build == null ? 点击的Tile : 点击的Tile.build.tile;
        float worldx = Core.input.mouseWorld(x, y).x, worldy = Core.input.mouseWorld(x, y).y;
        tileTapped(i.build);

        return false;
    }


    /**
     * 长按
     */
    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    /**
     * 当用户在屏幕上拖动手指并抬起手指时调用
     */
    @Override
    public boolean fling(float velocityX, float velocityY, KeyCode button) {
        return false;
    }

    /**
     * 拖拽
     */
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    /**
     * 不在拖拽
     */
    @Override
    public boolean panStop(float x, float y, int pointer, KeyCode button) {
        return false;
    }

    /**
     * 缩放
     */

    @Override
    public boolean pinch(Vec2 initialPointer1, Vec2 initialPointer2, Vec2 pointer1, Vec2 pointer2) {
        return false;
    }

    /**
     * 缩放
     */
    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    /**
     * 缩放结束
     */
    @Override
    public void pinchStop() {

    }



    public void tileTapped(@Nullable Building build) {
        if (build == null) {
            config.hideConfig();
            return;
        }
        if (!WAI_Building.containsKey(build.block())) {
            config.hideConfig();
            return;
        }
        if (WAI_Building.get(build.block).配置 && build.interactable(player.team())) {

            if ((!config.isShown() && build.shouldShowConfigure(player))
                    || (config.isShown())) {

                if (!build.block().configurable && WAI_Building.get(build.block).配置) {
                    Sounds.click.at(build);
                }
                config.showConfig(build);
            }

        }


    }


}
