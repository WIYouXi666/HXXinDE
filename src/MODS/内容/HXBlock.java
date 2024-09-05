package MODS.内容;

import MODS.测试生成;
import arc.Core;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.world.Block;
import mindustry.world.Tile;

import static MODS.一些方法.方法.测试聊天;
import static MODS.内容.建造限制.*;
import static arc.Core.settings;

public class HXBlock extends Block {
    public float 限制数量 = -1;
    public boolean 隐藏 = false;

    public HXBlock 前置建筑;
    public String 建筑标记 = name;
    public boolean 标记限制 = false;

    public HXBlock(String name) {
        super(name);
        前置建筑 = 测试生成.HuanXiangMODair;
        destructible = true;
    }


    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {

        if (标记限制 && 限制数量 != -1) {
            return 主动构建(建筑标记字符串(team, 建筑标记), 限制数量);

        }

        if (限制数量 != -1){
            return 主动构建(建筑块字符串(this, team), 限制数量);
        }

        if (前置建筑 != 测试生成.HuanXiangMODair) {
            return 前置构建(建筑块字符串(前置建筑, team), 1);

        }
        return true;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {


        if (!settings.getBool("建筑物限制提示")) return;
        if (限制数量 != -1) {
            drawPlaceText(Core.bundle.format(this.getContentType() + ".限制", 限制数量), x, y, valid);
        }
        if (前置建筑 != 测试生成.HuanXiangMODair) {
            drawPlaceText(Core.bundle.format(this.getContentType() + ".前置", 前置建筑.localizedName), x, y + 1, valid);
        }


        super.drawPlace(x, y, rotation, valid);
    }

    @Override
    public boolean isHidden() {
        return 隐藏;
    }

    public class HXBuilding extends Building {
        protected boolean f = true;

        @Override
        public void updateTile() {
            super.updateTile();

        }


        @Override
        public void add() {
            if (!this.added) {
                this.index__all = Groups.all.addIndex(this);
                this.index__build = Groups.build.addIndex(this);
                if (this.power != null) {
                    this.power.graph.checkAdd();
                }
                加建造数量(建筑实体字符串(this, team()));
                加建造数量(建筑标记字符串(team(), 建筑标记));
                测试聊天("add");
                this.added = true;
            }


        }

        @Override
        public void remove() {
            if (this.added) {
                Groups.all.removeIndex(this, this.index__all);
                this.index__all = -1;
                Groups.build.removeIndex(this, this.index__build);
                this.index__build = -1;
                if (this.sound != null) {
                    this.sound.stop();
                }
                减建造数量(建筑实体字符串(this, team()));
                减建造数量(建筑标记字符串(team(), 建筑标记));
                测试聊天("remove");
                this.added = false;
            }

        }
    }


}
