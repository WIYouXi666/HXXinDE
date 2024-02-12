package HuanXiang.能力;

import HuanXiang.KeNengYongDao.数组;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.graphics.Layer;

import static mindustry.Vars.renderer;
import static mindustry.Vars.state;

public class 力墙 {
    public static 数组 起点 = new 数组(100 * 8, 100 * 8);
    protected static Effect paramEffect;
    public Effect absorbEffect = Fx.absorb;
    public Effect shieldBreakEffect = Fx.shieldBreak;
    public Team team = Team.baseTeams[2];
    boolean ff = false;
    float buildupbuildup = 100000;
    int 边数 = 6;
    float 半径 = 200, 旋转 = 0;

    public 力墙() {

    }


    ///创建一个力墙子弹


    public void 执行() {

        final float[] 半径人 = {半径};
        ff = true;
        final float[] buildup = {0.1f};
            if (Vars.state.isGame()) {
                paramEffect = absorbEffect;

                if (ff) {
                    Groups.bullet.intersect(起点.x - 半径人[0], 起点.y - 半径人[0], 半径人[0] * 2f, 半径人[0] * 2f, bullet -> {
                        if (bullet.team != team && bullet.type.absorbable &&
                                Intersector.isInRegularPolygon(边数, 起点.x, 起点.y, 半径人[0], 旋转, bullet.x, bullet.y)) {
                            bullet.absorb();
                            paramEffect.at(bullet);
                            buildup[0] += bullet.damage;
                        }
                    });
                }
                // 半径人[0] = 半径*Math.max(Math.min(1-buildup[0]/buildupbuildup,1),0.5f);//动态变化
                if (buildup[0] >= buildupbuildup) {
                    shieldBreakEffect.at(起点.x, 起点.y, 半径人[0], team.color);
                    //buildup[0] = 0;
                    ff = false;
                    if (team != state.rules.defaultTeam) {
                        Events.fire(EventType.Trigger.forceProjectorBreak);
                    }
                }
            }
            Events.run(EventType.Trigger.draw, () -> {
                if (ff) {
                    Draw.color(team.color, Color.white, Mathf.clamp(1));

                    if (renderer.animateShields) {
                        Draw.z(Layer.shields + 0.001f * 1);
                        Fill.poly(起点.x, 起点.y, 边数, 半径人[0], 旋转);
                    } else {
                        Draw.z(Layer.shields);
                        Lines.stroke(1.5f);
                        Draw.alpha(0.09f + Mathf.clamp(0.08f * 1));
                        Fill.poly(起点.x, 起点.y, 边数, 半径人[0], 旋转);
                        Draw.alpha(1f);
                        Lines.poly(起点.x, 起点.y, 边数, 半径人[0], 旋转);
                        Draw.reset();
                    }
                }
            });
    }


}
