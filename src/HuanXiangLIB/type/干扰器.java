package HuanXiangLIB.type;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.QuadTree;
import arc.math.geom.Vec2;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.Block;

import static mindustry.Vars.*;

public class 干扰器 extends Block {
    private QuadTree tree;
    public float 半径 = 200f;
    public int 边数 = 24;

    protected BaseShieldBuild paramBuild;
    protected final Cons<Player> bulletConsumer = bullet -> {


        if (bullet.team() != paramBuild.team && bullet.within(paramBuild, 半径)) {

            bullet.unit();
        }
    };


    public 干扰器(String name) {
        super(name);

        hasPower = true;
        update = solid = true;
    }

    @Override
    public void init() {
        super.init();

        updateClipRadius(半径);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, 半径, player.team().color);
    }


    public class BaseShieldBuild extends Building {
        public boolean broken = false; //TODO
        public float hit = 0f;
        public float smoothRadius;

        @Override
        public void updateTile() {

            if (this.power.status > 0) {
                Vec2 position = Core.camera.position;

                if(Mathf.dst2(position.getX(), position.getY(), x, y) < 半径*半径){
                    
                }


                paramBuild = this;
                Groups.player.intersect(x - 半径, y - 半径, 半径 * 2f, 半径 * 2f, bulletConsumer);
            }
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            Drawf.dashCircle(x, y, 半径, team.color);
        }


        //在配置时画
        @Override
        public void draw() {
            super.draw();

            Draw.z(Layer.shields);

            Draw.color(team.color, Color.white, Mathf.clamp(hit));

            if (renderer.animateShields) {
                Fill.poly(x, y, 边数, 半径);
            } else {
                Lines.stroke(1.5f);
                Draw.alpha(0.09f + Mathf.clamp(0.08f * hit));
                Fill.poly(x, y, 边数, 半径);
                Draw.alpha(1f);
                Lines.poly(x, y, 边数, 半径);
                Draw.reset();
            }

            Draw.reset();
        }

    }
}
