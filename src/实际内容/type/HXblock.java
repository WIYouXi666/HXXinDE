package 实际内容.type;

import arc.Core;
import arc.Events;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Lightning;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.consumers.Consume;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import java.util.ArrayList;



public class HXblock extends Block {
    public static ObjectMap<String, Integer> 限制 = new ObjectMap<>();
    public boolean 图标更新 = false;
    public int 材质数量 = 0;
    public int 过渡帧 = 0;
    public float 动画间隔 = 5f;



    public float 闪电几率 = -1f;
    public float 闪电伤害 = 20f;
    public int 闪电长度 = 17;
    public Color 闪电颜色 = Pal.surge;
    public Sound 闪电声音 = Sounds.spark;


    public float 反射概率 = -1f;
    public Color 反击颜色 = Color.white;
    public Sound 反击声音 = Sounds.none;



    public boolean 生命共享 = false;
    public float 转换 = 1f;
    public String 防御效果 = "一般";
    public int 限制数量 = 5;
    boolean a=true,aa=true,aaa=true,aaaa=true;
    public ArrayList<Consume> 消费 =
            new ArrayList<>();




    public HXblock(String name) {
        super(name);
        configurable = true;
        solid = true;
        destructible = true;
        canOverdrive = false;
        drawDisabled = false;
        update = true;
        for (Consume A : 消费) {
            consume(A);
        }
    }



    @Override
    public void init() {
        super.init();
    }
    //可以打破
    @Override
    public boolean canBreak(Tile tile) {
        return super.canBreak(tile);
    }

    //可以替代
    @Override
    public boolean canReplace(Block other) {
        return super.canReplace(other);
    }

    //可以建造
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return 数量限制(team, tile.block(), 限制数量);
    }
    @Override
    public void loadIcon() {
        super.loadIcon();
        if (材质数量 > 0 && 图标更新) {
            TextureRegion[] 材质组 = new TextureRegion[材质数量 * (过渡帧 + 1)];
            if (过渡帧 <= 0) {
                for (int i = 1; i <= 材质数量; i++) {
                    材质组[i - 1] = Core.atlas.find(this.name + i);
                }
            } else {
                for (int i = 0; i < 材质数量; i++) {
                    材质组[i * (过渡帧 + 1)] = Core.atlas.find(this.name + (i + 1));
                    for (int j = 1; j <= 过渡帧; j++) {
                        int 数 = i * (过渡帧 + 1) + j;
                        材质组[数] = Core.atlas.find(this.name + "-t" + 数);
                    }
                }
            }
            this.fullIcon = new TextureRegion(this.fullIcon);
            this.uiIcon = new TextureRegion(this.uiIcon);
            Events.run(EventType.Trigger.update, () -> {
                int frame = (int) (Time.globalTime / 动画间隔) % 材质组.length;

                this.fullIcon.set(材质组[frame]);
                this.uiIcon.set(材质组[frame]);
            });
        }
    }

    @Override
    public void setStats() {
        super.setStats();
        if (反射概率 > 0f)
            stats.add(Stat.baseDeflectChance, 反射概率, StatUnit.percent);
        if (闪电几率 > 0f) {
            stats.add(Stat.lightningChance, 闪电几率 * 100f, StatUnit.percent);
            stats.add(Stat.lightningDamage, 闪电伤害, StatUnit.none);
        }
        //stats.add(特殊.防御效果, 效果组[防御效果].toString(), StatUnit.none);
    }

    public void 载入限制(Team t, Block b) {
        if (限制.get(t.name + b.name) == null) {
            限制.put(t.name + b.name, 1);
        } else {
            限制.put(t.name + b.name, 限制.get(t.name + b.name) + 1);
        }
    }

    public void 卸载限制(Team t, Block b) {
        if (限制.get(t.name + b.name) != null) {
            限制.put(b.name + b.name, 限制.get(t.name + b.name) - 1);
        }
    }

    public boolean 数量限制(Team t, Block b, float s) {
        if (s==-1)return true;
        if (限制.get(t.name + b.name) == null) {
            限制.put(t.name + b.name, 1);
        }
        return 限制.get(t.name + b.name) < s;
    }

    public boolean 前置限制(Team t, Block b) {
        return 限制.get(t.name + b.name) > 0;
    }

    public boolean 前置限制(Team t, Block... b) {
        float aaa = 0;
        for (Block b1 : b) {
            if (限制.get(t.name + b1.name) > 0) {
                aaa += 1;
            }
        }

        return aaa == b.length;

    }

    public class 核心的建筑Build extends Building {


        @Override
        public void updateTile() {







            if (生命共享) {
                生命共享(转换);
            }
            if (a){
                if (限制数量 > 0) {
                    if (team != Team.derelict) {
                        载入限制(this.team, this.block);
a = !a;
                    }
                }
            }

        }

        @Override
        public float handleDamage(final float n) {
            return n;
        };

        @Override
        public boolean collision(Bullet bullet) {
            super.collision(bullet);
            //create lightning if necessary
            if (闪电几率 > 0f) {
                反击闪电(bullet);
            }

            //deflect bullets if necessary
            if (反射概率 > 0f) {
                反击子弹(bullet);
            }

            return true;
        }

        @Override
        public void remove() {
            if (this.added) {
                Groups.all.removeIndex(this, this.index__all);
                this.index__all = -1;
                Groups.build.removeIndex(this, this.index__build);
                this.index__build = -1;
                if (this.sound != null) {
                    this.sound.stop();
                }
                卸载限制(this.team, this.block);
                this.added = false;
            }
        }

        @Override
        //保存
        public void write(Writes write) {
            super.write(write);
        }

        @Override
        //读取
        public void read(Reads read, byte revision) {
            super.read(read, revision);
        }


        public void 生命共享(float 转换) {

            for (Building A : proximity) {
                if (A.block != block)
                    continue;
                if (health > 1 && health > A.health && A.health < A.maxHealth) {
                    A.health += 转换;
                    health -= 转换;
                }

            }
        }
        public void 物品提取() {
            for (Building A : proximity) {
                for (final Item item : Vars.content.items()) {
                    if (hasItems) {
                        break;
                    }
                    if (items == null) {
                        break;
                    }
                    if (acceptItem(this, item)) {
                        continue;
                    }
                    if (items.get(item) < itemCapacity) {
                        if (A.items.get(item) >0) {
                            A.items.remove(item, 1);
                            Fx.itemTransfer.at(this.x, this.y, 0, item.color, A);
                            items.add(item, 1);
                        }
                    }
                }
            }
        }


        public boolean 反击子弹(Bullet bullet) {
            //slow bullets are not deflected
            if (bullet.vel.len() <= 0.1f || !bullet.type.reflectable) return true;

            //bullet reflection chance depends on bullet damage
            if (!Mathf.chance(反射概率 / bullet.damage())) return true;

            //make sound
            反击声音.at(tile, Mathf.random(0.9f, 1.1f));

            //translate bullet back to where it was upon collision
            bullet.trns(-bullet.vel.x, -bullet.vel.y);

            float penX = Math.abs(x - bullet.x), penY = Math.abs(y - bullet.y);

            if (penX > penY) {
                bullet.vel.x *= -1;
            } else {
                bullet.vel.y *= -1;
            }

            bullet.owner = this;
            bullet.team = team;
            bullet.time += 1f;

            //disable bullet collision by returning false
            return false;
        }

        public boolean 反击闪电(Bullet bullet) {
            if (Mathf.chance(闪电几率)) {
                Lightning.create(team, 闪电颜色, 闪电伤害, x, y, bullet.rotation() + 180f, 闪电长度);
                闪电声音.at(tile, Mathf.random(0.9f, 1.1f));
            }
            return true;
        }

    }
}
