package HuanXiang.测试的文件;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.TextureRegion;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.world.Block;

public class  FGHFG {
    //updateTile()
    public static void 生命共享(Building build,float 转换) {
        for (Building Z : build.proximity) {
            if (Z.block != build.block)
                continue;
            if (build.health > 1 && build.health > Z.health && Z.health < Z.maxHealth) {
                Z.health += 1 *  转换;
                build.health -= 1 *  转换;
            }
        }
    }
// loadIcon()
    public static void 图标更新(Block block, int 材质数量,int 过渡帧,float 动画间隔) {
        if (材质数量 > 0) {
            TextureRegion[] 材质组 = new TextureRegion[材质数量 * (过渡帧 + 1)];
            if (过渡帧 <= 0) {
                for (int i = 1; i <= 材质数量; i++) {
                    材质组[i - 1] = Core.atlas.find(block.name + i);
                }
            } else {
                for (int i = 0; i < 材质数量; i++) {
                    材质组[i * (过渡帧 + 1)] = Core.atlas.find(block.name + (i + 1));
                    for (int j = 1; j <= 过渡帧; j++) {
                        int 数 = i * (过渡帧 + 1) + j;
                        材质组[数] = Core.atlas.find(block.name + "-t" + 数);
                    }
                }
            }
            block.fullIcon = new TextureRegion(block.fullIcon);
            block.uiIcon = new TextureRegion(block.uiIcon);
            Events.run(EventType.Trigger.update, () -> {
                int frame = (int) (Time.globalTime / 动画间隔) % 材质组.length;

                block.fullIcon.set(材质组[frame]);
                block.uiIcon.set(材质组[frame]);
            });
        }

    }








//    public boolean 反击子弹(Building build,Bullet bullet) {
//        //slow bullets are not deflected
//        if (bullet.vel.len() <= 0.1f || !bullet.type.reflectable) return true;
//
//        //bullet reflection chance depends on bullet damage
//        if (!Mathf.chance(反射概率 / bullet.damage())) return true;
//
//        //make sound
//        反击声音.at(tile, Mathf.random(0.9f, 1.1f));
//
//        //translate bullet back to where it was upon collision
//        bullet.trns(-bullet.vel.x, -bullet.vel.y);
//
//        float penX = Math.abs(x - bullet.x), penY = Math.abs(y - bullet.y);
//
//        if (penX > penY) {
//            bullet.vel.x *= -1;
//        } else {
//            bullet.vel.y *= -1;
//        }
//
//        bullet.owner = build;
//        bullet.team = build.team;
//        bullet.time += 1f;
//
//        //disable bullet collision by returning false
//        return false;
//    }
//
//    public boolean 反击闪电(Building build,Bullet bullet) {
//        if (Mathf.chance(闪电几率)) {
//            Lightning.create(team, 闪电颜色, 闪电伤害, x, y, bullet.rotation() + 180f, 闪电长度);
//            闪电声音.at(tile, Mathf.random(0.9f, 1.1f));
//        }
//        return true;
//    }

}
