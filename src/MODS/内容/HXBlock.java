package MODS.内容;

import arc.Core;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;

import java.util.ArrayList;

import static MODS.内容.建造限制.*;
import static arc.Core.settings;
import static mindustry.gen.Call.sendChatMessage;

public class HXBlock extends Block {
    public float 限制数量 = -1;


    public ArrayList<HXBlock> 前置建筑;
    public String 建筑标记;

    public HXBlock(String name) {
        super(name);
        前置建筑 = new ArrayList<>();
        建筑标记 = name;
    }


    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if (限制数量 != -1) {
            return 主动构建(建筑块字符串(this, team), 限制数量);
        }
        if (!前置建筑.isEmpty()) {
            for (Block s : 前置建筑) {
                return 前置构建(建筑块字符串(s, team), 1);
            }
        }
        return false;
        // return 新放置规则(this , 前置建筑, 限制数量, tile, team, rotation);
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        if (settings.getBool("建筑物限制提示")) {
            int B = 0;
            if (限制数量 != -1) {
                if (!主动构建(建筑块字符串(this, this.newBuilding().team()), 限制数量)) {
                    String Disabled = "bcvbv"; //Core.bundle.format(this.getContentType() + "." + this.name + ".限制", String.valueOf(限制数量));
                    this.drawPlaceText(Disabled, x, y + B, valid);
                    B += 1;
                }
            }
            if (!前置建筑.isEmpty()) {
                if (!主动构建(建筑块字符串(this, this.newBuilding().team()), 限制数量)) {
                    String Disabled = Core.bundle.format(this.getContentType() + "." + this.name + ".前置", String.valueOf(限制数量));
                    this.drawPlaceText(Disabled, x, y + B, valid);
                    B += 1;
                }
            }
        }
    }


    public class HXBuilding extends Building {


        @Override
        public void updateTile() {
            super.updateTile();
        }

        @Override
        public void add() {

            加建造数量(建筑实体字符串(this, team()));
            加建造数量(建筑标记字符串(this, team(), 建筑标记));
            sendChatMessage(获取(建筑实体字符串(this, team())));
            super.add();
        }

        @Override
        public void remove() {

            减建造数量(建筑实体字符串(this, team()));
            减建造数量(建筑标记字符串(this, team(), 建筑标记));
            sendChatMessage(获取(建筑实体字符串(this, team())));
            super.remove();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
        }
    }


}
