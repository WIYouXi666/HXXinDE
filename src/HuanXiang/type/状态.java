package HuanXiang.type;

import HuanXiang.content.HXPlanets;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class 状态 extends StatusEffect {

    public float 抹杀 = 0f;
    public float 易伤 = 0f;

    public 状态(String name) {
        super(name);
    }
    @Override
    public void setStats(){
        super.setStats();
        stats.addPercent(HXPlanets.抹杀, 抹杀);
        stats.addPercent(HXPlanets.易伤, 易伤);
    }
    @Override
    public boolean showUnlock(){
        return true;
    }
    @Override
    public boolean isHidden(){
        return false;
    }
    @Override
    public void update(Unit unit, float time){
       super.update(unit,time);
        if (unit.health<unit.maxHealth*抹杀)unit.damage(unit.maxHealth+1);
       // unit.dockedType.update();
    }
}
