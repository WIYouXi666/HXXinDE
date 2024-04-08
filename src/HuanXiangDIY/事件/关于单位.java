package HuanXiangDIY.事件;

import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.gen.Groups;

import java.util.Arrays;

import static mindustry.Vars.ui;

public class 关于单位 {

    public static void 执行() {
        final boolean[] gfvdsg = {true};
        Groups.unit.each(uc ->
                {

                    if (gfvdsg[0]) {
                        uc.abilities = new Ability[2];
                        uc.abilities[0] = (new UnitSpawnAbility(uc.type, 600,0,30)).copy();
                        uc.abilities[1] = (new ForceFieldAbility(1000, 1, 10000, 0)).copy();
                        gfvdsg[0] = false;

                    } else {
                        Ability[] hj = uc.abilities;
                        ui.chatfrag.addMessage(String.valueOf(hj.length));
                        ui.chatfrag.addMessage(Arrays.toString(hj));


//                        ArrayList<Ability> KL = 集合B(hj);
//                        KL.add(new HX能力());
//                        KL.add(new UnitSpawnAbility(uc.type, 600,0,0));

//                        uc.abilities = 关于单位能力(KL,hj.length+1);

                    }


                }


        );
    }

}
