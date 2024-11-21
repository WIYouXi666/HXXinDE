package MODS.ui;

import MODS.HX_外_Building;
import arc.Core;
import arc.Events;
import arc.math.Interp;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.Group;
import arc.scene.actions.Actions;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import mindustry.content.Blocks;
import mindustry.game.EventType.ResetEvent;
import mindustry.gen.Building;
import mindustry.ui.fragments.BlockConfigFragment;
import mindustry.world.Block;

import static MODS.HX_Vars.get配置;
import static MODS.HX_Vars.WAI_Building;
import static mindustry.Vars.player;

public class 块配置片段  extends BlockConfigFragment {
    Table table = new Table();
    Building selected;
    Block block;
    HX_外_Building 超b;

    public void build(Group parent) {
        table.visible = false;
        table.name = "块配置";
        parent.addChild(table);

        Events.on(ResetEvent.class, e -> forceHide());
    }

    public void forceHide() {
        table.visible = false;
        selected = null;
        block = null;
        超b = null;
    }

    public boolean isShown() {
        return table.visible && selected != null;
    }

    public Building getSelected() {
        return selected;
    }

    public Block getblock() {
        return block;
    }

    public void showConfig(Building tile) {
        if (tile.block() != null) {
            block = block == null ? tile.block() : block;
        }
        if (get配置(block)) {
            if (selected != null) selected.onConfigureClosed();


            if (WAI_Building.containsKey(block)) {
                超b = WAI_Building.get(block);
            }
            selected = tile;

            table.visible = true;

            table.clear();
            if (超b != null) {
                table.table(i -> {
                    超b.构建配置(tile, i);
                });
            }

            table.pack();
            table.setTransform(true);
            table.actions(Actions.scaleTo(0f, 1f), Actions.visible(true),
                    Actions.scaleTo(1f, 1f, 0.07f, Interp.pow3Out));

            table.update(() -> {
                if (selected != null && selected.shouldHideConfigure(player)) {
                    hideConfig();
                    return;
                }

                table.setOrigin(Align.center);
                if (selected == null || selected.block == Blocks.air || !selected.isValid()) {
                    hideConfig();
                } else {
                    updateTableAlign(selected,table);
                }
            });

        }

    }

    public boolean hasConfigMouse() {
        Element e = Core.scene.hit(Core.input.mouseX(), Core.graphics.getHeight() - Core.input.mouseY(), true);
        return e != null && (e == table || e.isDescendantOf(table));
    }

    public void hideConfig() {
        if (selected != null) selected.onConfigureClosed();
        selected = null;
        block = null;
        超b = null;
        table.actions(Actions.scaleTo(0f, 1f, 0.06f, Interp.pow3Out), Actions.visible(false));
    }
    public void updateTableAlign(Building tile,Table table) {
        Vec2 pos = Core.input.mouseScreen(tile.x, tile.y);
        table.setPosition(pos.x, pos.y+((float)(this.block.size * 8) / 2.0F)*80, 2);
    }
}
