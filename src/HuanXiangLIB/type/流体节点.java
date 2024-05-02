package HuanXiangLIB.type;

import mindustry.gen.Building;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

public class 流体节点 extends 物品节点 {
    public 流体节点(String name) {
        super(name);
        hasItems = false;
        hasLiquids = true;
        outputsLiquid = true;
        group = BlockGroup.liquids;
        envEnabled = Env.any;
    }
    public class jnfhgjfhgyj extends sdfsf {
        @Override
        public void updateTransport(Building other){
            if(warmup >= 0.25f){
                moved |= moveLiquid(other, liquids.current()) > 0.05f;
            }
        }

        @Override
        public void doDump(){
            dumpLiquid(liquids.current(), 1f);
        }

    }

}
