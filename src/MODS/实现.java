package MODS;

import MODS.core内容.ui.开局加载对话框;
import MODS.core内容.ui.设置实现;
import MODS.core内容.事件逻辑;
import MODS.core内容.原版ui替换与添加;
import MODS.core内容.绘制draw;
import MODS.树.树根;
import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import mindustry.ai.types.BuilderAI;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.gen.EntityMapping;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.type.unit.ErekirUnitType;
import mindustry.type.weapons.RepairBeamWeapon;

import static mindustry.Vars.tilesize;


public class 实现 {
    public static UnitType mega;
    public static Item 转换材料;
    public static void 基础加载() {
         转换材料 = new Item("微型模块", Color.valueOf("c1c2c3")) {{
            this.buildable = false;
        }};
        ;
        unitload();
        //统一事件.load();
        事件逻辑.load();

        new 树根("树根") {{
            生长速度 = 120;
            主枝干数 = 666;
            主干 = Blocks.microProcessor;
            枝干 = Blocks.copperWall;
            附属物 = Blocks.microProcessor;

            this.requirements(Category.defense, ItemStack.with(new Object[]{Items.copper, 6}));
            this.health = 80;
            this.researchCostMultiplier = 0.1F;
        }};
    }

    public static void 客户端加载() {


        设置实现.load();
        开局加载对话框.show();
        原版ui替换与添加.load();
        测试.rebuild();


        new HX_外_Building(Blocks.copperWall) {

            @Override
            public void 构建配置(Building build, Table table) {
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });


            }

            {
                配置 = true;
                限制.限制数量 = 10;

            }
        };
        new HX_外_Building(Blocks.microProcessor) {

            @Override
            public void 构建配置(Building build, Table table) {
                table.button("fbgcvbcvb", () -> {
                });
                table.button("fbgcvbcvb", () -> {
                });


            }

            {
                限制.限制数量 = 10;

            }
        };
        new HX_外_Building(Blocks.titaniumWall) {{
            限制.限制数量 = 10;



        }};
        new HX_外_Building(Blocks.copperWallLarge) {{
            限制.限制数量 = 10;



        }};
        new 绘制draw();

    }

    public static void 更新事件() {


    }

    public static Runnable 异步() {
        return () -> {
            //  反射输入.sgsdgfg();
            //  输入加载.load();

//            输入处理 = new 输入处理器();
//            输入处理.drawUI();

        };
    }

    public static void unitload() {
        //EntityMapping.register(THIS()+"-emanate",)
        float coreFleeRange = 500f;
        mega = new ErekirUnitType("emanate") {{
            constructor = EntityMapping.map(5);
            coreUnitDock = true;
            controller = u -> new BuilderAI(true, coreFleeRange);
            isEnemy = false;
            envDisabled = 0;

            targetPriority = -2;
            lowAltitude = false;
            mineWalls = true;
            mineFloor = false;
            mineHardnessScaling = false;
            flying = true;
            mineSpeed = 9f;
            mineTier = 3;
            buildSpeed = 1.5f;
            drag = 0.08f;
            speed = 7.5f;
            rotateSpeed = 8f;
            accel = 0.08f;
            itemCapacity = 110;
            health = 700f;
            armor = 3f;
            hitSize = 12f;
            buildBeamOffset = 8f;
            payloadCapacity = 2f * 2f * tilesize * tilesize;
            pickupUnits = false;
            vulnerableWithPayloads = true;

            fogRadius = 0f;
            targetable = false;
            hittable = false;

            engineOffset = 7.5f;
            engineSize = 3.4f;

            setEnginesMirror(
                    new UnitEngine(35 / 4f, -13 / 4f, 2.7f, 315f),
                    new UnitEngine(28 / 4f, -35 / 4f, 2.7f, 315f)
            );

            weapons.add(new RepairBeamWeapon() {{
                widthSinMag = 0.11f;
                reload = 20f;
                x = 19f / 4f;
                y = 19f / 4f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.7f;
                aimDst = 0f;
                shootCone = 40f;
                mirror = true;

                repairSpeed = 3.6f / 2f;
                fractionRepairSpeed = 0.03f;

                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = Pal.accent;
                healColor = Pal.accent;

                bullet = new BulletType() {{
                    maxRange = 65f;
                }};
            }});
        }};
    }
}
