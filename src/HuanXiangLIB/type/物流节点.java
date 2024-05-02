package HuanXiangLIB.type;

import mindustry.gen.Building;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

public class 物流节点 extends 物品节点{
    public 物流节点(String name) {
        super(name);

        hasLiquids = true;
        outputsLiquid = true;
        group = BlockGroup.liquids;
        envEnabled = Env.any;
    }
    public class dsfgdfg extends sdfsf {
        @Override
        public void updateTransport(Building other){
            super.updateTransport(other);
            if(warmup >= 0.25f){
                moved |= moveLiquid(other, liquids.current()) > 0.05f;
            }
        }

        @Override
        public void doDump(){
            super.doDump();
            dumpLiquid(liquids.current(), 1f);
        }

    }
}
