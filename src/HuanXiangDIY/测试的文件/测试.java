package HuanXiangDIY.测试的文件;

import HuanXiangLIB.记录$功能.标签lib;
import HuanXiangDIY.子弹.共鸣;
import arc.Core;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.entities.TargetPriority;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Icon;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.consumers.Consume;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.BlockStatus;

import java.util.ArrayList;

import static mindustry.Vars.*;

public class 测试 extends Block {





    static 标签lib.标签 a;
    public ArrayList<Integer> 集合 =
            new ArrayList<>();
    public float captureInvicibility = 60f * 15f;
    public float 限制数量 = 8;
    public 测试(String name) {
        super(name);
        final Seq<Consume> consumeBuilder1 = consumeBuilder;
        //consumeBuilder1.add(ItemStack.with(Items.copper, 0,Items.coal,1,Items.lead,1))
        configurable = true;
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        canOverdrive = false;
        drawDisabled = false;
        update = true;
        priority = TargetPriority.wall;
    }

    @Override
    public void init() {
        super.init();
    }
    @Override
    public void setStats() {
        super.setStats();
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("分配时间", (测试T e) -> new Bar("分配时间", Pal.lightOrange, () -> e.gg));
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
//    @Override
//    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
//        return 限制建造.相同建筑(tile.build,限制数量);
//    }
//    @Annotations.无用("可以打破")
//    public boolean canBreak(Tile tile, boolean b) {
//        return true;
//    }
//
//    @Annotations.无用("可以替代")
//    public boolean canReplace(Block other, boolean b) {
//        return false;
//    }
//
//    @Annotations.无用("可以建造")
//    public boolean canPlaceOn(Tile tile, Team team, int rotation, boolean b) {
//        return true;
//    }
//
//    @Annotations.无用("放置时绘制")
//    public void drawPlace(int x, int y, int rotation, boolean valid) {
//    }
//    @Annotations.无用("放置和悬停时绘制")
//    public void drawOverlay(float x, float y, int rotation) {
//
//    }
    // distributor.consumes.add(new DynamicConsumePower(b => b.mpower()));
    public class 测试T extends Building {
        public Team lastDamage = Team.derelict;
        Seq<Consume> sdf = consumeBuilder;
        float gg;
        float powerProduction = 11111110f;

        @Override
        public void afterDestroyed(){
            if(state.rules.coreCapture){
                if(!net.client()){
                    tile.setBlock(block, lastDamage);
                }

                Core.app.post(() -> tile.setNet(block, lastDamage, 0));


            }
        }
        public void 关于ai() {

            //在此地图上的任何地方是否有   Blocks
            indexer.isBlockPresent(Blocks.tankAssembler);
            // 此地图上是否有  Items
            indexer.hasOre(Items.coal);
            //检测范围内所有建筑物
            float 范围 = 0;
            indexer.allBuildings(this.x, this.y, 范围, Building -> {
            });
            //按团队返回所有损坏的块
            indexer.getDamaged(this.team);
            //查找相对于位置最近的矿石块
            indexer.findClosestOre(this.x, this.y, Items.coal);
        }


        //配置
        @Override
        public void buildConfiguration(Table table) {
            共鸣 sdfds = new  共鸣(this, team);
            table.button(Icon.infoCircle,()->{
                sdfds.create( this, team, this.x, this.y, 0);
            });

            // table.slider(5, 20, 1, 0, f -> delay = f * 60f).growX().row();//滑块

            //ItemSelection.buildTable(table, content.units().select(b -> !b.isHidden()), this::type, this::configure);//物品

        }
//画
//        @Override
//        public void draw() {
//            super.draw();
//            Tmp.v1.trns(60, tilesize * size * 1.5f);
//            Drawf.arrow(x, y, x + Tmp.v1.x, y + Tmp.v1.y, size * tilesize, tilesize / 2f);
//            //if (toSpawnType == null) return;
////            Vars.ui.showLabel(String.valueOf(储标签().toString()),1,x,y+16);
////            Vars.ui.showLabel(String.valueOf(储内容().toString()),1,x,y+8);
////            Vars.ui.showLabel(String.valueOf(使用().toString()),1,x,y-16);
////            Vars.ui.showLabel(String.valueOf(被使用().toString()),1,x,y-8);
//
//            Vars.ui.showLabel(String.valueOf(JiaoHuJuNei.交互集合.size()),1,x,y);
//            Vars.ui.showLabel(String.valueOf(JiaoHuJuNei.交互集合.toString()),1,x,y+8);
//      //Vars.ui.showLabel(String.valueOf(标签方法.检测标签(Items.sand).length),1,x,y-16);
//        //    Vars.ui.showLabel(String.valueOf(标签数量(this.team, 标签.ceshi)),1,x,y-24);
//
////            Vars.ui.showLabel(String.valueOf(限制建造.相同建筑(newBuilding(      ),限制数量)),1,x+16,y+8);
////            Log.info("JK");
////            Log.info(配置u.获取设置Float("豆腐干大概", "666"));
//
//
//            Drawf.light(x, y, tilesize * size * 3f, team.color, 0.8f);
//            Draw.z(Layer.overlayUI);
//            //Draw.rect(toSpawnType.fullIcon, x, y, size * tilesize, size * tilesize);
//            String dddd = String.valueOf(600 - Time.time);
//        }

        //在配置构建上点击
        @Override
        public boolean onConfigureBuildTapped(Building other) {
            super.onConfigureBuildTapped(other);
            return this == other;
        }

        //在配置时画
        public void drawConfigure() {
            super.drawConfigure();
        }

        //损坏时画
        @Override
        public void drawCracks() {
            super.drawCracks();
        }

        //画出发光？
        @Override
        public void drawLight() {
            super.drawLight();
        }

        //液体光
        @Override
        public void drawLiquidLight(Liquid liquid, float amount) {
            super.drawLiquidLight(liquid, amount);
        }

        //显示状态状态
        public BlockStatus status() {
            super.status();
            return null;
        }

        //状态画
        @Override
        public void drawStatus() {
            super.drawStatus();
        }

        //发电
        @Override
        public float getPowerProduction() {
            return 600000f / 60f;
        }

        //关于碰撞？
        @Override
        public boolean collision(Bullet bullet) {//子弹
            return super.collision(bullet);
        }

        //处理伤害
        @Override
        public float handleDamage(final float n) {
            return n;
        }

        //伤害
        @Override
        public void damage(float damage) {
            super.damage(damage);
        }

        //可以控制选择
        @Override
        public boolean canControlSelect(Unit player) {
            return super.canControlSelect(player);
        }



        //更新
        @Override
        public void updateTile() {
            // kill();
          ///  Vars.ui.showLabel(String.valueOf(集合.size()), 1, x, y);
        }

        @Override
        //捡起
        public void pickedUp() {
            super.pickedUp();
        }

        //删除时？
//        @Override
//        public void onRemoved() {
//            限制建造.删除(this);
//            super.onRemoved();
//        }
//
//
//
//        //放置
//        @Override
//        public void placed() {
//            super.placed();
//            if(限制数量>=0&&限制建造.相同建筑(this,限制数量)) {
//                限制建造.添加(this);
//            }
//        }
        //覆盖时？
        @Override
        public void overwrote(Seq<Building> previous) {
            super.overwrote(previous);
        }

        //销毁后
//        @Override
//        public void afterDestroyed() {
//            限制建造.删除(this);
//            super.afterDestroyed();
//        }
        //在 已销毁
//        @Override
//        public void onDestroyed(){
//            限制建造.删除(this);
//            super.onDestroyed();
//        }
        //捡起后
        @Override
        public void afterPickedUp() {
            super.afterPickedUp();

        }

        //可以捡起
        @Override
        public boolean canPickup() {
            return false;
        }

        //阅读后
        @Override
        public void afterRead() {
            super.afterRead();
        }

        //应用加速
        @Override
        public void applyBoost(float intensity, float duration) {
            super.applyBoost(intensity, duration);
        }

        //应用愈合抑制
        @Override
        public void applyHealSuppression(float amount) {
            super.applyHealSuppression(amount);
        }

        //应用减速
        @Override
        public void applySlowdown(float intensity, float duration) {
            super.applySlowdown(intensity, duration);
        }


        //显示
        @Override
        public void display(Table table) {
            super.display(table);
            table.row();
            table.table(null, t -> {
                t.image(Icon.power).size(8, 8);
                t.add("").update(l -> {
                    //l.setText((this.power.status == 1 ? "[]" : "[#ff4444]") + " " + (Math.round(gg * 10) / 10));
                }).wrap().padLeft(10);
            }).left().padLeft(10).padTop(10).padBottom(10);
        }

        //Cell
        //保存
        @Override
        public void write(Writes write) {
        }

        //读取
        @Override
        public void read(Reads read, byte revision) {
        }

        //可交互
        @Override
        public boolean interactable(Team team) {
            return super.interactable(team);
        }
    }
}
