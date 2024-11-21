package MODS.树.HXtype;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import mindustry.content.Items;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.world.Block;

public class 有限矿 extends Block{
    public 有限矿(String name) {
        super(name);
        update  = true;
        //destructible = false;
        solid  = true;
        itemDrop = Items.copper;
    }
//    @Override
//    public void loadIcon() {
//        super.loadIcon();
//        TextureAtlas.AtlasRegion laser = Core.atlas.find("item-"+itemDrop.name);
//        fullIcon = new TextureRegion(laser);
//        uiIcon = new TextureRegion(laser);
//    }


    public class 有限矿实体 extends Building {
        float maxHealths = maxHealth;
        @Override
        public void draw() {
            super.draw();
            var realWidth = size * (health /maxHealths);
            Draw.color(Pal.health);
            Fill.rect(x, y, realWidth, 4);
            Draw.reset();
            Draw.color();

        }
    }





}
