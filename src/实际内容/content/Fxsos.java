package 实际内容.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;


public class Fxsos {
    public static Effect DFSF = new Effect(80f, e -> {
        Fx.rand.setSeed(e.id);
        Draw.color(Color.lightGray, Color.gray, e.fin());
        Angles.randLenVectors(e.id, 4, 2.0F + 12.0F * e.fin(Interp.pow3Out), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * Fx.rand.random(1, 2.5f));
        });
    }).layer(Layer.blockOver + 1);
    public static Effect tFx = new Effect(40, e -> {
        Draw.color(Pal.lancerLaser);
        Lines.stroke(1 + e.fout() * 2);
        Lines.circle(e.x, e.y, e.fout() * 8);
    });

    public static Effect dFx = new Effect(80, e -> {
        Draw.color(Pal.lancerLaser);
        Lines.stroke(e.fout() * 3);
        Lines.circle(e.x, e.y, e.finpow() * 80);

        float f1 = e.finpow() * 80;
        float f2 = e.rotation + e.fin() * 450;
        float h = 20 + e.fin() * 20;
        float w = e.fout() * 20;

        for (int i = 0; i < 2; i++) {
            f2 = i == 0 ? f2 : f2 + 90;
            Drawf.tri(e.x + Angles.trnsx(f2, f1), e.y + Angles.trnsy(f2, f1), w, h, f2);
            Drawf.tri(e.x - Angles.trnsx(f2, f1), e.y - Angles.trnsy(f2, f1), w, -h, f2);
        }

        e.scaled(7, i -> {
            Lines.stroke(3 * i.fout());
            Lines.circle(e.x, e.y, 4 + i.fin() * 70);
        });
    });

    public static Effect hFx = new Effect(30, e -> {
        Draw.color(Pal.lancerLaser);
        e.scaled(25, i -> {
            Lines.stroke(3 * i.fout());
            Lines.circle(e.x, e.y, 4 + i.fin() * 60);
        });
    });

    public static Effect shootingFx = new Effect(30, e -> {
        Draw.color(Pal.lancerLaser);
        Lines.stroke(e.fslope() * 2.5f);
        Lines.circle(e.x, e.y, 5 + e.fout() * 70);
    });

    public static Effect sFx = new Effect(30, e -> {
        Draw.color(Pal.lancerLaser);
        Lines.stroke(e.fout() * 2.5f);
        Lines.circle(e.x, e.y, 5 + e.fin() * 70);
    });

    public static Effect spark = new Effect(20, e -> {
        Draw.color(Pal.bulletYellow);
        Angles.randLenVectors(e.id, 5, 1 + 20 * e.finpow(), e.rotation, 110, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 3 + 1);
        });
    });


    public static Effect zz = new Effect(40, e -> {
        Draw.color(Color.valueOf("6e7080"), Color.valueOf("f3f3f3"), Color.valueOf("ffffff"), e.fin());

        Draw.color(Color.valueOf("f3f3f3"));
        Angles.randLenVectors(e.id, 8, 2 + 30 * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2 + 0.5f);
        });
    });
    public static Effect craftFx = new Effect(38, e -> {
        for (int i = 0; i < 8; i++) {
            Draw.color(i > 4 ? Color.valueOf("f1f1f1") : Color.valueOf("ffffff"));
            Tmp.v1.trns(90 * i, 6.8f).add(e.x, e.y);

            float h = e.finpow() * 22;
            float w = e.fout() * 10;
            Drawf.tri(Tmp.v1.x, Tmp.v1.y, w, h, 90 * i);
            Drawf.tri(Tmp.v1.x, Tmp.v1.y, w - 2, h / 2, 90 * i);

            Draw.color(Pal.meltdownHit, Color.gray, e.fin());
            Angles.randLenVectors(e.id + (i * 10), 6, 5 + 15 * e.finpow(), i * 90, 30, (x, y) -> {
                Fill.circle(Tmp.v1.x + x, Tmp.v1.y + y, e.fout() * 1.8f);
            });
        }
        Draw.color(Color.valueOf("6e7080"), Color.valueOf("f3f3f3"), Color.valueOf("ffffff"), e.fin());
        e.scaled(7, i -> {
            Lines.stroke(3 * i.fout());
            Lines.circle(e.x, e.y, 4 + i.fin() * 30);
        });

        Draw.color(Color.valueOf("f3f3f3"));
        Angles.randLenVectors(e.id, 8, 2 + 30 * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2 + 0.5f);
        });
    });
    public static Effect t4 = new Effect(120, e -> {
        int rad = 15 * 6;
        Draw.z(Layer.bullet + 10);
        Lines.stroke(e.fout() * 4);
        Draw.color(Pal.lancerLaser, Color.purple, e.fin());
        Lines.circle(e.x, e.y, e.fin() * rad);
    });

    public static Effect chargeEffect(float range, Color colorFrom, Color colorTo) {
        return new Effect(range * 2, e -> {
            Draw.z(Layer.bullet + 10.001f);
            Lines.stroke(e.fout() * 4);
            Draw.color(colorFrom, colorTo, e.fin());
            Lines.circle(e.x, e.y, e.fin() * range);
        });
    }

}
