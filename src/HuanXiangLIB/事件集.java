package HuanXiangLIB;


import HuanXiangLIB.核心内容.修正类记录;
import HuanXiangLIB.注册类.修正;
import arc.ApplicationListener;
import arc.Events;
import mindustry.Vars;
import mindustry.game.Rules;

import static HuanXiangLIB.核心内容.修正类记录.修正记录;
import static HuanXiangLIB.核心内容.新事件.事件枚举.修正记录变更;

public class 事件集 implements ApplicationListener {
    public 事件集() {
        Events.run(修正记录变更, () -> {
            修正类记录.加载();



            Rules.TeamRule 团队 = Vars.state.rules.teams.get(Vars.state.rules.defaultTeam);

            for (修正 修正 : 修正记录) {
                if (修正.无限火力) 团队.cheat = true;
                if (修正.构建不消耗资源) 团队.infiniteResources = true;
                if (!修正.无限弹药) 团队.infiniteAmmo = false;
                团队.unitBuildSpeedMultiplier += 修正.单位生产速度;
                团队.unitDamageMultiplier += 修正.单位伤害;
                团队.unitCrashDamageMultiplier += 修正.单位死亡伤害;
                团队.unitCostMultiplier += 修正.单位生产花费;
                团队.unitHealthMultiplier += 修正.单位生命值;
                团队.blockHealthMultiplier += 修正.块生命值;
                团队.blockDamageMultiplier += 修正.块伤害;
                团队.buildSpeedMultiplier += 修正.建造速度;
            }


        });


    }
}
