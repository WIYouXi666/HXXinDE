package HuanXiangLIB.关于修正;

import HuanXiangLIB.注册类.修正;
import arc.Events;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BuildVisibility;

import java.util.ArrayList;

import static HuanXiangLIB.核心内容.总集类.修正映射;
import static HuanXiangLIB.核心内容.新事件.事件枚举.修正记录变更;

public class 修正记录块 extends Block {

    public 修正记录块() {
        super("修正记录块");

        health = Integer.MAX_VALUE;
        targetable = false;
        forceDark = true;
        solid = true;
        update = true;
        category = Category.distribution;
        buildVisibility = BuildVisibility.shown;
    }
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return false;
    }

    public class 修正记录块实体 extends Building {
        public  ArrayList<修正> A = new ArrayList<>();


        public  void 加(修正 name) {
            if (!A.contains(name)) {
                A.add(name);
                Events.fire(修正记录变更);
            }
        }
        public  void 减(修正 name) {
            if (A.contains(name)) {
                A.remove(name);
                Events.fire(A);
            }
        }



        @Override
        public boolean collide(Bullet other) {
            return false;
        }

        @Override
        public void damage(float damage) {
            super.damage(0f);

        }

        @Override
        public void read(Reads read, byte revision) {
         float a = read.f();
            for (float i = 0;i<a;i++){
                A.add(修正映射.get(read.i()));
            }
        }

        @Override
        public void write(Writes write) {
            write.f(A.size());
            for (修正 a : A) {
                write.i(a.id());
            }

        }

        @Override
        public void update() {




        }




    }



}
