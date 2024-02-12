package HuanXiang.content;


import HuanXiang.type.*;
import HuanXiang.主要.配方;
import HuanXiang.核心内容.事件.局内事件主类;
import HuanXiang.测试的文件.测试;
import HuanXiang.游戏内功能事件.召唤单位;
import HuanXiang.游戏内功能事件.掠夺;
import HuanXiang.游戏内功能事件.补给;
import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.type.StatusEffect;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Stat;

import static HuanXiang.content.Fxsos.DFSF;
import static HuanXiang.核心内容.核心标签.核心标签内容;
import static mindustry.content.StatusEffects.*;

public class HXPlanets {
    public static UnlockableContent dfgd,
            sdadasdas, dvf, sandCracker, test, sdadfa, dxfgh, 哈哈哈哈哈a哈哈, 哈哈哈哈哈哈哈, dtfrdgtdfgdfg, gtfdgddsfdsffgg, gtfdgdfgg, dfy, rftghf, tyfh, sdgfs, tyhj, FFFFF, ertgdfg, fsdgdfg, tasedfsyhj, tadfgsedfsyhj;
    public static 局内事件主类 dd,adsf,ddCSC,sdgdsfgs,asfdsaf;
    public static 能量
            空 = new 能量("空"),
            电力 = new 能量("电力"),
            热量= new 能量("热量"),
            以太 = new 能量("以太"),
            暗能 = new 能量("暗能"),
            幽能 = new 能量("幽能"),
            灵能 = new 能量("灵能");
    public static final Stat 抹杀 = new Stat("抹杀"),易伤 = new Stat("易伤");
    public static StatusEffect asdf = new 状态("状态"){{
        color = Color.valueOf("ffc455");
        damage = 0.167f;
        effect = Fx.burning;
        transitionDamage = 8f;

        init(() -> {
            opposite(wet, freezing);
            affinity(tarred, (unit, result, time) -> {
                unit.damagePierce(transitionDamage);
                Fx.burning.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                result.set(burning, Math.min(time + result.time, 300f));
            });
        });
    }},asdasd = new StatusEffect("状态2"){{
        color = Color.valueOf("ffc455");
        damage = 0.167f;
        effect = Fx.burning;
        transitionDamage = 8f;

        init(() -> {
            opposite(wet, freezing);
            affinity(tarred, (unit, result, time) -> {
                unit.damagePierce(transitionDamage);
                Fx.burning.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                result.set(burning, Math.min(time + result.time, 300f));
            });
        });
    }};


    public static void load() {
      new 配方(66,核心标签内容("熔炼")){{
          输入 = new 条目()
                  .物品( ItemStack.with(Items.copper, 100, Items.graphite, 80, Items.titanium, 50))
                  .流体(LiquidStack.with(Liquids.water,12,Liquids.oil,4532))
                  .添加能量(电力, 666)
                  .添加能量(以太,45);

          输出 = new 条目()
                  .物品( ItemStack.with(Items.copper, 100, Items.graphite, 80, Items.titanium, 50))
                  .流体(LiquidStack.with(Liquids.water,12,Liquids.oil,4532))
                  .添加能量(电力, 666);


      }};
//        new 配方(666,核心标签内容("熔炼")){{
//
//        }};
         dfgd=new gongCang("gjghjghjghjghjghjghjghjghjghjhgjghjghjggh"){{
             类型 = 核心标签内容("熔炼");
             category = Category.distribution;
             buildVisibility = BuildVisibility.shown;

         }};
        dd = new 召唤单位(){{
            all.put(UnitTypes.eclipse, 10f);
            all.put(UnitTypes.mono, 10f);
            team = Team.baseTeams[1];
           // 降临中心 = new 数组(128*8,128*8);
            //补给资源/盗取/交换
            //空袭/火力
            //召唤单位
            //任务
            //信号
            //建造建筑
            //倒计时
            //天气
            //护盾
            //修复
        }};
        adsf = new 掠夺(){{
            all.put(Items.lead, 100);
        }};
        ddCSC = new 补给(){{
            all.put(Items.sand, 100);
        }};
       // asfdsaf=new 力墙();



//        sdadasdas = new 有限矿("测试1") {
//            {
//                产出 = 1;
//size = 5;
//                category = Category.distribution;
//                buildVisibility = BuildVisibility.shown;
//            }
//        };
//        sdadasdas = new 采矿机("测试2") {
//            {
//
//                category = Category.distribution;
//                buildVisibility = BuildVisibility.shown;
//            }
//        };







        dvf = new HXblock("测试3") {
            {
                health = 10000;

                category = Category.distribution;
                buildVisibility = BuildVisibility.shown;
            }
        };
        sandCracker = new 多合成A("sand-cracker") {{
            size = 2;
            requirements(Category.crafting, ItemStack.with(Items.lead, 40, Items.copper, 60, Items.graphite, 45));
            health = 320;
            craftTime = 45f;
            itemCapacity = 20;
            hasPower = hasItems = true;
            //craftEffect = NHFx.hugeSmokeGray;
            updateEffect = DFSF;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawFrames(), new DrawArcSmelt(), new DrawDefault());
            consumePower(1.5f);
            设置配方(Items.lead, 1, 1, Items.titanium, 1, 2, Items.thorium, 1, 3, Items.scrap, 2, 5, Items.beryllium, 2, 3, Items.tungsten, 1, 2);
            设置输出(Items.sand);
        }};


//        fsdgdfg = new 新Block("11111111ceshi”") {{
//            插件 = new 插件().block(this).building((新Building)buildType).能力(new 更新图标(6,6,6));
//            category = Category.distribution;
//            buildVisibility = BuildVisibility.shown;
//        }};
//        dfy = new 资源分配("dhgfbfghh”") {{
//            category = Category.distribution;
//            buildVisibility = BuildVisibility.shown;
//            数量 = 10;
//        }};
//        rftghf = new 超级源("fgfghhfgfh") {{
//            category = Category.distribution;
//            buildVisibility = BuildVisibility.shown;
//        }};
        tyfh = new 测试("测试") {{
            category = Category.distribution;
            buildVisibility = BuildVisibility.shown;

        }};

//        tyhj = new 资源分配仓库T("ssdfdgdfdffvdf") {{
//            this.requirements(Category.effect, ItemStack.with(Items.titanium, 100));
//            this.size = 5;
//            this.itemCapacity = 300;
//            this.scaledHealth = 55.0F;
//        }};
//        tyhj = new 资源收集仓库T("ssdfddfgfdgfvdf") {{
//            this.requirements(Category.effect, ItemStack.with(Items.titanium, 100));
//            this.size = 5;
//            this.itemCapacity = 300;
//            this.scaledHealth = 55.0F;
//        }};

        //.反击闪电(0.5f,10,10).
//        tadfgsedfsyhj = new HXBlock("HXBlock2") {{
//            this.requirements(Category.effect, ItemStack.with(Items.titanium, 100));
//            this.size = 5;
//            this.itemCapacity = 300;
//            this.scaledHealth = 55.0F;
//            this.health = 1000;
//            this.Block功能.生命共享血量(1,1,1).反击闪电(0.5f,10,10).反射子弹(0.5f).不灭();
//        }};

    }

}