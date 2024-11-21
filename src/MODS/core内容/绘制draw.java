package MODS.core内容;

import MODS.HX_外_Building;
import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
import arc.graphics.g2d.Lines;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Scl;
import arc.util.Align;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.game.EventType;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.input.InputHandler;
import mindustry.ui.Fonts;
import mindustry.world.Block;
import mindustry.world.Tile;

import static MODS.HX_Vars.WAI_Building;
import static MODS.HX_Vars.关于输入;
import static MODS.core内容.建造限制.*;
import static mindustry.Vars.*;

public class 绘制draw {
    static InputHandler input = control.input;
    public 绘制draw() {
        Events.run(EventType.Trigger.draw, () -> {
            Draw.draw(Layer.plans - 0.001f, this::UI);
        });
    }

    public void UI() {
        if (player.dead()) return;
        if (player.isBuilder()) {
            player.unit().drawBuildPlans();
        }
        if (Vars.ui == null) return;
        建筑物提示();
        建筑物hua();
    }


    public void 建筑物提示(){



        if (input.block != null) {
            boolean valid = input.validPlace(cursorX(), cursorY(), input.block, input.rotation);
            if (WAI_Building.containsKey(input.block)) {
                HX_外_Building j = WAI_Building.get(input.block);
                drawPlace(j, cursorX(), cursorY(), valid && 限制判断(j, input.block, Vars.player.team()));
                绘制文本(j.信息, cursorX()-10, cursorY()-5, true);
            }

        }
    }

    public void 建筑物hua(){
        if (关于输入.config.isShown()) {
            Block tile = 关于输入.config.getblock();
            if (WAI_Building.containsKey(tile)) {
                HX_外_Building j = WAI_Building.get(tile);
                j.绘制配置(关于输入.config.getSelected());
            }

        }
    }
    public static void drawPlace(HX_外_Building hhh, int x, int y, boolean valid) {
        if (world.tile(x, y) == null) return;
        HX_外_Building.HX_外_Building_限制 sp = hhh.限制;
        int B = 0;

//        if (sp.信息 != null) {
//            sp.block.drawPlaceText(sp.信息, x, y + B, valid);
//            B += 1;
//        }
        if (sp.限制数量 != -1) {
            hhh.block.drawPlaceText("限制数量: " + sp.限制数量, x, y + B, valid);
            B += 1;
        }
        if (hhh.标签限制) {
            hhh.block.drawPlaceText("主标签限制: " + sp.限制数量, x, y + B, valid);
            B += 1;
        }

        if (!sp.前置建筑.isEmpty()) {
            int jjj = 0;
            boolean ppo = true;
            for (Block block : sp.前置建筑) {
                boolean kkk = 前置构建(建筑块字符串(block, Vars.player.team()), sp.建筑低数);
                if (kkk) jjj++;
                ppo = ppo && kkk && jjj < sp.建筑类数;
            }
            if (!ppo) {
                for (Block block : sp.前置建筑) {
                    hhh.block.drawPlaceText("前置建筑: " + block.localizedName, x, y + B, valid);
                    B += 1;
                }
            }
        }
        if (!sp.前置标签.isEmpty()) {
            int jjj = 0;
            boolean ppo = true;
            for (String block : sp.前置标签) {
                boolean kkk = 前置构建(建筑标记字符串(Vars.player.team(), block), sp.标签低数);
                if (kkk) jjj++;
                ppo = ppo && kkk && jjj < sp.标签类数;

            }
            if (!ppo) {
                for (String block : sp.前置标签) {
                    hhh.block.drawPlaceText("前置标签: " + block, x, y + B, valid);
                    B += 1;
                }
            }
        }

    }


    public float 绘制文本(String text, int x, int y, boolean valid) {
        if (renderer.pixelator.enabled()) return 0;

        Color color = valid ? Pal.accent : Pal.remove;
        Font font = Fonts.outline;
        GlyphLayout layout = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
        boolean ints = font.usesIntegerPositions();
        font.setUseIntegerPositions(false);
        font.getData().setScale(1f / 4f / Scl.scl(1f));
        layout.setText(font, text);

        float width = layout.width;

        font.setColor(color);

        float dx = x * tilesize,
                dy = y * tilesize + 3;
        font.draw(text, dx, dy + layout.height + 1, Align.center);
        dy -= 1f;
        Lines.stroke(2f, Color.darkGray);
        Lines.line(dx - layout.width / 2f - 2f, dy, dx + layout.width / 2f + 1.5f, dy);
        Lines.stroke(1f, color);
        Lines.line(dx - layout.width / 2f - 2f, dy, dx + layout.width / 2f + 1.5f, dy);

        font.setUseIntegerPositions(ints);
        font.setColor(Color.white);
        font.getData().setScale(1f);
        Draw.reset();
        Pools.free(layout);

        return width;
    }




    public static Tile tileAt(float x, float y) {
        return world.tile(tileX(x), tileY(y));
    }

    public static Tile getCursorTile() {
        return world.tile(cursorX(), cursorY());
    }

    public static int cursorX() {
        return tileX(Core.input.mouseX());
    }

    public static int cursorY() {
        return tileY(Core.input.mouseY());
    }

    public static int tileX(float cursorX) {
        Vec2 vec = Core.input.mouseWorld(cursorX, 0);
        if (input.selectedBlock()) {
            vec.sub(input.block.offset, input.block.offset);
        }
        return World.toTile(vec.x);
    }

    public static int tileY(float cursorY) {
        Vec2 vec = Core.input.mouseWorld(0, cursorY);
        if (input.selectedBlock()) {
            vec.sub(input.block.offset, input.block.offset);
        }
        return World.toTile(vec.y);
    }



}
