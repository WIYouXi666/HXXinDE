package HuanXiangLIB.Unit$武器$能力;

import HuanXiangLIB.type.状态;
import arc.util.Time;
import arc.util.pooling.Pools;
import mindustry.content.Fx;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.UnitEntity;

public abstract class 单位实体 extends UnitEntity {
    public transient float 减伤 = 0F;
    public transient float 易伤 = 0F;
    public transient float 抹杀 = 0F;

    public transient float 护甲强度倍率 = 1F;
    public transient float gg = 0F;

    @Override
    public void rawDamage(float amount) {
        if (this.health() <= this.maxHealth() * 抹杀+1) {
            kill();
            if (this.shield != 0) {
                Fx.unitShieldBreak.at(this.x, this.y, 0.0F, this.team.color, this);
            }
        }
        amount += amount * 易伤;
        amount -= 减伤;
        super.rawDamage(amount);

    }

    @Override
    public void update() {
        super.update();
        this.applied.clear();
        this.减伤 = this.抹杀 = this.易伤 = 0;
        this.speedMultiplier = this.damageMultiplier = this.healthMultiplier = this.reloadMultiplier = this.buildSpeedMultiplier = this.dragMultiplier = 1.0F;
        this.disarmed = false;
        int index;
        if (!this.statuses.isEmpty()) {
            index = 0;

            label318:
            while (true) {
                while (true) {
                    if (index >= this.statuses.size) {
                        break label318;
                    }

                    StatusEntry entry = (StatusEntry) this.statuses.get(index++);
                    entry.time = Math.max(entry.time - Time.delta, 0.0F);
                    if (entry.effect != null && (!(entry.time <= 0.0F) || entry.effect.permanent)) {
                        this.applied.set(entry.effect.id);
                        减伤 += entry.effect instanceof 状态 ? ((状态) entry.effect).减伤 : 0;
                        易伤 += entry.effect instanceof 状态 ? ((状态) entry.effect).易伤 : 0;
                        抹杀 += entry.effect instanceof 状态 ? ((状态) entry.effect).抹杀 : 0;
                        抹杀 = 抹杀 > 1 ? 1 : 抹杀;
                        this.speedMultiplier *= entry.effect.speedMultiplier;
                        this.healthMultiplier *= entry.effect.healthMultiplier;
                        this.damageMultiplier *= entry.effect.damageMultiplier;
                        this.reloadMultiplier *= entry.effect.reloadMultiplier;
                        this.buildSpeedMultiplier *= entry.effect.buildSpeedMultiplier;
                        this.dragMultiplier *= entry.effect.dragMultiplier;
                        this.disarmed |= entry.effect.disarm;
                        entry.effect.update(this, entry.time);
                    } else {
                        Pools.free(entry);
                        --index;
                        this.statuses.remove(index);
                    }
                }
            }
        }


    }


}
