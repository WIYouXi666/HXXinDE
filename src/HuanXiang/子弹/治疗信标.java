package HuanXiang.子弹;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

public class 治疗信标 extends BasicBulletType {

    public TextureRegion laser;
    public TextureRegion laserEnd;
    public TextureRegion frontR;
    public TextureRegion backR;
    public float 治疗百分比 = 10;// = healAmount+;

    public 治疗信标() {
        super();
        width = 25;
        height = 25;
        speed = 0.5F;
        lifetime = 60 * 8;
        healAmount = 3.4F;
        drag = 1;
        sprite = "large-bomb";
        backColor = Pal.heal;
        weaveScale = 90;
        weaveMag = 10;
        shrinkY = 0;
        collidesAir = false;
        collidesGround = false;
        hittable = false;
        pierce = true;
        absorbable = false;
       despawnEffect = Fx.heal;

    }

    @Override
    public void load() {
        super.load();
        laser = Core.atlas.find("laser");
        laserEnd = Core.atlas.find("laser-end");
        frontR = Core.atlas.find("large-bomb");
        backR = Core.atlas.find("large-bomb-back");
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
        range = 90;
    }

    public void drawHeal(Bullet b, Unit unit, float mag, Color color) {
        float rad = 4.5f + Mathf.absin(Time.time, 12f, mag);
        float rand1 = Mathf.absin(Time.time + 48, 12F, 3.5F), x = unit.x + 2 - rand1;
        float rand2 = Mathf.absin(Time.time + 48, 12 + 2, 3.5f), y = unit.y + 2 - rand2;

        Draw.color(color);
        Draw.z(Layer.flyingUnit + 1);
        Draw.alpha(mag == 0 ? 0.7f : 0.6f + Mathf.absin(Time.time, 12, mag / 4));
        Drawf.laser(
                laser,
                laserEnd,
                b.x - 1 + rand1 / 2,
                b.y - 1 + rand2 / 2,
                x,
                y,
                0.35f + rad / 12f
        );
        Draw.alpha(mag == 0 ? 0.7f : 1 - Mathf.absin(Time.time, 12, mag / 4));
        Fill.circle(x, y, rad);
        Draw.z(Layer.flyingUnit + 1.1f);
        Draw.color(Color.white);
        Fill.circle(x, y, rad / 2);
    }

    @Override
    public void update(Bullet b) {
        float length = Mathf.random(30);
        float rotation = (float) ((b.fin() * b.type.lifetime + Time.time + Mathf.random(5)) * Math.PI);
        Unit target = Units.closest(b.team, b.x, b.y, range, u -> u.team == b.team);
        if (target != null) {
                float x = target.x + Angles.trnsx(rotation, 20 + length);
                float y = target.y + Angles.trnsy(rotation, 20 + length);
                //vertical and horizontal speed control based on target's position
                float dst = Mathf.dst(b.x, b.y, x, y) / 12;
                b.vel.set(dst, dst);
                b.rotation(b.angleTo(x, y));
        }
    }

    @Override
    public void draw(Bullet b) {

        float pulse = ((this.width + this.height) / 2) + Mathf.absin(Time.time, 8, 6);

        Draw.z((Layer.flyingUnit + 1.2f));
        Draw.color(this.backColor);
        Draw.rect(backR, b.x, b.y, pulse, pulse, b.rotation() + (Time.time * 2) + pulse / 2);
        Draw.z(Layer.flyingUnit + 1.4f);
        Draw.color(Pal.bulletYellow);
        Draw.rect(this.frontR, b.x, b.y, pulse + 0.5f, pulse + 0.5f, b.rotation() - (Time.time * 2));

        Groups.unit.each(e -> {
            if (e.within(b.x, b.y, range) && e.team == b.team) {
                drawHeal(b, e, e.damaged() ? 2 : 0, e.damaged() ? Color.valueOf("8fffa9") : Pal.plastanium);
                if (!Vars.state.isPaused()) if (e.maxHealth() * 治疗百分比<healAmount) {
                    e.heal(healAmount * Time.delta);
                } else {
                    e.heal(e.maxHealth() * 治疗百分比 * Time.delta);
                }
            }
            ;
        });
    }
}


