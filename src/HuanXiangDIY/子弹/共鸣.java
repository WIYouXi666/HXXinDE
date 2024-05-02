package HuanXiangDIY.子弹;

import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;

import java.util.ArrayList;

import static mindustry.Vars.indexer;
import static mindustry.Vars.world;

public class 共鸣 extends BulletType {
    public ArrayList<Integer> 集合 = new ArrayList<>();
    public ArrayList<Integer> 集合2 = new ArrayList<>();
    public Team team;
    float
            X,
            Y,
            宽度 = 8,
            速度 = 10,
            时间 = 20,
            伤害 = 500;
    float 衰减 = 20;
    float 范围 = 0;


    public 共鸣(Building GG, Team eam) {
        super(0, 0);
        X = GG.x();
        Y = GG.y();
        team = eam;
        lifetime = 速度 * 时间;
    }

    public 共鸣(Unit GG, Team eam) {
        super(0, 0);
        X = GG.x();
        Y = GG.y();
        team = eam;
        lifetime = 速度 * 时间;
    }




    @Override
    public void update(Bullet b) {
        super.update(b);
        范围 = b.time;
        indexer.allBuildings(b.x, b.y, 范围, B -> {

            if (B.team == this.team) {
                return;
            }
            if (Math.sqrt(B.x()*B.x()+B.y()*B.y())<范围) {
                return;
            }
            if (集合.contains(B.pos())) {
                return;
            }
            if (集合2.contains(B.pos())) {
                return;
            }
            集合.add(B.pos());//添加
            //if (集合.contains(ss.pos())) return;
            //if (ss.team() == this.team) return;
        });

        // b.time


        if (范围 < 速度 * 时间) {
            for (Integer integer : 集合) {
                if (world.build(integer) == null ) {
                    集合.remove(integer);
                    break;
                }
                if (集合2.contains(integer)) {
                    集合.remove(integer);
                    break;
                }
                    Building HGDFG = world.build(integer);
                    HGDFG.damage(伤害/60);
                    集合2.add(integer);
                    //lifetime-=衰减;
                    b.time +=衰减;
                    共鸣 ik = new 共鸣(HGDFG, team){{
                        lifetime = 速度 * 时间/2;
                  }};
                    //ik.lifetime = 速度 * 时间/2;
                 Bullet gh = ik.create(HGDFG, team, HGDFG.x, HGDFG.y, 0);
                 //gh.time = b.time;
                b.remove();
            }
        }
    }

    @Override
    public void draw(Bullet b) {
        Drawf.circles(b.x, b.y, 范围);
        Drawf.circles(b.x, b.y, 范围 - 宽度 / 2);
    }

    


}
