package MODS.core内容;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import mindustry.entities.Effect;

public class FX {

    public static Effect 烟气效果(Color a, Color b, float 烟气范围) {
        return new Effect(20, e -> {
            Draw.color(a, b, e.fin());
            Fill.circle(e.x, e.y, 烟气范围 * e.fout());
        });
    }

    public static Effect 烟气效果(Color a, Color b, float 火花范围, float 火花尺寸) {
        return new Effect(18, e -> {
            Draw.color(a, b, e.fin());
            Fill.circle(e.x + Mathf.randomSeedRange(e.id, 火花范围) * e.fin(), e.y + Mathf.randomSeedRange(e.id + 3, 火花范围) * e.fin() - e.rotation * e.fin(), 火花尺寸 * e.fout());
        });
    }
}
