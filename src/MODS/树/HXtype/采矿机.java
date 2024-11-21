package MODS.树.HXtype;

import arc.math.Mathf;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.production.BeamDrill;

public class 采矿机 extends BeamDrill {
    public 采矿机(String name) {
        super(name);

    }





    public class 采矿机实体 extends BeamDrillBuild {
        @Override
        public void updateTile() {
            super.updateTile();
            if (this.lasers[0] == null) {
                this.updateLasers();
            }

            this.warmup = Mathf.approachDelta(this.warmup, (float)Mathf.num(this.efficiency > 0.0F), 0.016666668F);
            this.updateFacing();
            float multiplier = Mathf.lerp(1.0F, 采矿机.this.optionalBoostIntensity, this.optionalEfficiency);
            float drillTime = 采矿机.this.getDrillTime(this.lastItem);
            this.boostWarmup = Mathf.lerpDelta(this.boostWarmup, this.optionalEfficiency, 0.1F);
            this.lastDrillSpeed = (float)this.facingAmount * multiplier * this.timeScale / drillTime;
            this.time += this.edelta() * multiplier;
            if (this.time >= drillTime) {
                Tile[] var3 = this.facing;
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Tile tile = var3[var5];
                    Item drop = tile == null ? null : wallDrop(tile);
                    if (this.items.total() < 采矿机.this.itemCapacity && drop != null) {
                        this.items.add(drop, 1);
                        if (tile.build instanceof 有限矿.有限矿实体) {
                            tile.build.damage(1000);
                            //tile.build.maxHealth -= 1;
                        }

                        this.produced(drop);
                    }
                }

                this.time %= drillTime;
            }

            if (this.timer(采矿机.this.timerDump, 5.0F)) {
                this.dump();
            }

        }

        public Item wallDrop(Tile tile) {
            return tile.build.block.solid ? (tile.build.block.itemDrop != null ? tile.build.block.itemDrop : null) : null;
        }

    }
}
