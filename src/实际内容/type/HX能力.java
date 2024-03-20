package 实际内容.type;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

import static mindustry.content.StatusEffects.none;

public class HX能力 extends Ability {

    public boolean 可见吗 = true;
    //同步的变量
    //public float data;
    public HX能力() {
        this.display = 可见吗;

    }

    public void update(Unit unit){





        unit.apply(none);//添加
        unit.unapply(none);//清除
        unit.hasEffect(none);//有吗
        unit.isImmune(none);//免疫
    }
    public void draw(Unit unit){


    }
    public void death(Unit unit){}
    public void init(UnitType type){}

    public void displayBars(Unit unit, Table bars){}

    public void addStats(Table t){}


    /** @return localized ability name; mods should override this. */
    public String localized(){
        var type = getClass();
        return Core.bundle.get("ability." + (type.isAnonymousClass() ? type.getSuperclass() : type).getSimpleName().replace("Ability", "").toLowerCase());
    }
}
