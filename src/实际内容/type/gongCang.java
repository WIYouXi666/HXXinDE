package 实际内容.type;

import 实际内容.主要.配方;
import 实际内容.核心内容.UI;
import 核心.标签.核心标签;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.gen.Tex;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;

import static 核心.标签.核心标签.核心标签内容;
import static mindustry.Vars.tilesize;
import static mindustry.ui.Styles.flatOver;

public class gongCang extends Block {
    public static ImageButton.ImageButtonStyle clearToggleTransi = new ImageButton.ImageButtonStyle(){{
        down = Styles.flatDown;
        checked = Styles.flatDown;
        up = Styles.black6;
        over = flatOver;
    }};
    public static final Seq<配方> 配方s = new Seq<>();
    public 核心标签 类型 = 核心标签内容("空");
    public int RecipeShowIS = 0;
    /**
     * Written to outputItems as a single-element array if outputItems is null.
     */
    public @Nullable ItemStack outputItem;
    /**
     * Overwrites outputItem if not null.
     */
    public @Nullable ItemStack[] outputItems;

    /**
     * Written to outputLiquids as a single-element array if outputLiquids is null.
     */
    public @Nullable LiquidStack outputLiquid;
    /**
     * Overwrites outputLiquid if not null.
     */
    public @Nullable LiquidStack[] outputLiquids;
    /**
     * Liquid output directions, specified in the same order as outputLiquids. Use -1 to dump in every direction. Rotations are relative to block.
     */
    public int[] liquidOutputDirections = {-1};

    /**
     * if true, crafters with multiple liquid outputs will dump excess when there's still space for at least one liquid type
     */
    public boolean dumpExtraLiquid = true;
    public boolean ignoreLiquidFullness = false;

    public float craftTime = 80;
    public Effect craftEffect = Fx.none;
    public Effect updateEffect = Fx.none;
    public float updateEffectChance = 0.04f;
    public float warmupSpeed = 0.019f;
    /**
     * Only used for legacy cultivator blocks.
     */
    public boolean legacyReadWarmup = false;

    public DrawBlock drawer = new DrawDefault();


    public gongCang(String name) {
        super(name);
        configurable = true;
        update = true;
        solid = true;
        hasItems = true;
        ambientSound = Sounds.machine;
        sync = true;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.factory);
        drawArrow = false;
        saveConfig = true;

        config(Integer.class, (GenericCrafterBuild b, Integer i) ->{
            b.IDD = i;
        });

        this.buildType = GenericCrafterBuild::new;
    }

    @Override
    public void setStats() {
        super.setStats();
        //stats.timePeriod = craftTime;
        stats.remove(Stat.powerUse);
        stats.remove(Stat.productionTime);


//        if ((hasItems && itemCapacity > 0) || outputItems != null) {
//            stats.add(Stat.productionTime, craftTime / 60f, StatUnit.seconds);
//        }
//
//        if (outputItems != null) {
//            stats.add(Stat.output, StatValues.items(craftTime, outputItems));
//        }
//
//        if (outputLiquids != null) {
//            stats.add(Stat.output, StatValues.liquids(1f, outputLiquids));
//        }


        stats.add(Stat.output, table -> {
            UI.多合成统计显示(table,this, 配方s);
        });
    }

    @Override
    public void setBars() {
        super.setBars();

        //set up liquid bars for liquid outputs
        if (outputLiquids != null && outputLiquids.length > 0) {
            //no need for dynamic liquid bar
            removeBar("liquid");

            //then display output buffer
            for (LiquidStack stack : outputLiquids) {
                addLiquidBar(stack.liquid);
            }
        }
    }

    @Override
    public boolean rotatedOutput(int x, int y) {
        return false;
    }

    @Override
    public void load() {
        super.load();

        drawer.load(this);
    }

    @Override
    public void init() {


        for (配方 aa : 配方.all) {
            if (aa.类型 == this.类型) {
                配方s.add(aa);
            }
        }

        if (outputItems == null && outputItem != null) {
            outputItems = new ItemStack[]{outputItem};
        }
        if (outputLiquids == null && outputLiquid != null) {
            outputLiquids = new LiquidStack[]{outputLiquid};
        }
        //write back to outputLiquid, as it helps with sensing
        if (outputLiquid == null && outputLiquids != null && outputLiquids.length > 0) {
            outputLiquid = outputLiquids[0];
        }
        outputsLiquid = outputLiquids != null;

        if (outputItems != null) hasItems = true;
        if (outputLiquids != null) hasLiquids = true;


        super.init();
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

//    @Override
//    public TextureRegion[] i) {
//        return drawer.finalIthis);
//    }

    @Override
    public boolean outputsItems() {
        return outputItems != null;
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out) {
        drawer.getRegionsToOutline(this, out);
    }

    @Override
    public void drawOverlay(float x, float y, int rotation) {
        if (outputLiquids != null) {
            for (int i = 0; i < outputLiquids.length; i++) {
                int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

                if (dir != -1) {
                    Draw.rect(
                            outputLiquids[i].liquid.fullIcon,
                            x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
                            y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
                            8f, 8f
                    );
                }
            }
        }
    }

    public class GenericCrafterBuild extends Building {
        public int IDD = 0;
        public float progress;
        public float totalProgress;
        public float warmup;
        @Override
        public Integer config(){
            return IDD;
        }
        @Override
        public void buildConfiguration(Table table) {
            ButtonGroup<ImageButton> group = new ButtonGroup<>();
            group.setMinCheckCount(0);
            group.setMaxCheckCount(1);

            int A = 0;
            for (配方 配方 : 配方s) {



            int finalI = 1;

            ImageButton button = table.button(Tex.whiteui, clearToggleTransi, 40, () -> {
            }).group(group).get();
            button.clicked(() -> configure(button.isChecked() ? finalI : -1));

            //               TextureRegion icon;
//                if (ItemLiquid == null) {
//                    icon = recs[i].output.items.length > 0 ?
//                            recs[i].output.items[0].item.uiIcon :
//                            recs[i].output.liquids.length > 0 ? recs[i].output.liquids[0].liquid.uiIcon : region;
//                } else {
//                    icon = ItemLiquid[finalI] ?
//                            recs[i].output.items[0].item.uiIcon : recs[i].output.liquids[0].liquid.uiIcon;
//                }
//
//                if(icon == Core.atlas.find("error")){
//                    for(var a : recs[i].output.DawnPower){
//                        icon = (TextureRegion) Icon.power.tint(a.key.color);
//                    }
//                }

            // button.getStyle().imageUp = new TextureRegionDrawable(icon);
            button.update(() -> button.setChecked(IDD == finalI));

            A += 1;
            if (A == (RecipeShowIS != 0 ? RecipeShowIS : size + 1)) {
                A = 0;
                table.row();
            }
        }
        }

        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public boolean shouldConsume() {
            if (outputItems != null) {
                for (ItemStack output : outputItems) {
                    if (items.get(output.item) + output.amount > itemCapacity) {
                        return false;
                    }
                }
            }
            if (outputLiquids != null && !ignoreLiquidFullness) {
                boolean allFull = true;
                for (LiquidStack output : outputLiquids) {
                    if (liquids.get(output.liquid) >= liquidCapacity - 0.001f) {
                        if (!dumpExtraLiquid) {
                            return false;
                        }
                    } else {
                        //if there's still space left, it's not full for all liquids
                        allFull = false;
                    }
                }

                //if there is no space left for any liquid, it can't reproduce
                if (allFull) {
                    return false;
                }
            }

            return enabled;
        }

        @Override
        public void updateTile() {
            if (efficiency > 0) {

                progress += getProgressIncrease(craftTime);
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);

                //continuously output based on efficiency
                if (outputLiquids != null) {
                    float inc = getProgressIncrease(1f);
                    for (LiquidStack output : outputLiquids) {
                        handleLiquid(this, output.liquid, Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
                    }
                }

                if (wasVisible && Mathf.chanceDelta(updateEffectChance)) {
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            } else {
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            //TODO may look bad, revert to edelta() if so
            totalProgress += warmup * Time.delta;

            if (progress >= 1f) {
                craft();
            }

            dumpOutputs();
        }

        @Override
        public float getProgressIncrease(float baseTime) {
            if (ignoreLiquidFullness) {
                return super.getProgressIncrease(baseTime);
            }

            //limit progress increase by maximum amount of liquid it can produce
            float scaling = 1f, max = 1f;
            if (outputLiquids != null) {
                max = 0f;
                for (LiquidStack s : outputLiquids) {
                    float value = (liquidCapacity - liquids.get(s.liquid)) / (s.amount * edelta());
                    scaling = Math.min(scaling, value);
                    max = Math.max(max, value);
                }
            }

            //when dumping excess take the maximum value instead of the minimum.
            return super.getProgressIncrease(baseTime) * (dumpExtraLiquid ? Math.min(max, 1f) : scaling);
        }

        public float warmupTarget() {
            return 1f;
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        public void craft() {
            consume();

            if (outputItems != null) {
                for (ItemStack output : outputItems) {
                    for (int i = 0; i < output.amount; i++) {
                        offload(output.item);
                    }
                }
            }

            if (wasVisible) {
                craftEffect.at(x, y);
            }
            progress %= 1f;
        }

        public void dumpOutputs() {
            if (outputItems != null && timer(timerDump, dumpTime / timeScale)) {
                for (ItemStack output : outputItems) {
                    dump(output.item);
                }
            }

            if (outputLiquids != null) {
                for (int i = 0; i < outputLiquids.length; i++) {
                    int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

                    dumpLiquid(outputLiquids[i].liquid, 2f, dir);
                }
            }
        }

        @Override
        public double sense(LAccess sensor) {
            if (sensor == LAccess.progress) return progress();
            //attempt to prevent wild total liquid fluctuation, at least for crafters
            if (sensor == LAccess.totalLiquids && outputLiquid != null) return liquids.get(outputLiquid.liquid);
            return super.sense(sensor);
        }

        @Override
        public float progress() {
            return Mathf.clamp(progress);
        }

        @Override
        public int getMaximumAccepted(Item item) {
            return itemCapacity;
        }

        @Override
        public boolean shouldAmbientSound() {
            return efficiency > 0;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(progress);
            write.f(warmup);
            if (legacyReadWarmup) write.f(0f);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            progress = read.f();
            warmup = read.f();
            if (legacyReadWarmup) read.f();
        }
    }
}
