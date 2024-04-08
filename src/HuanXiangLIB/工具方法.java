package HuanXiangLIB;

import arc.Core;
import arc.Events;
import arc.audio.Music;
import arc.audio.Sound;
import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Floatf;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Button;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Align;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Blocks;
import mindustry.core.FileTree;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.MappableContent;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Lightning;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.mod.Mods;
import mindustry.type.*;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.consumers.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatUnit;
import HuanXiangLIB.GongJu.动态流体消耗;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

import static arc.Core.scene;
import static mindustry.Vars.*;
import static rhino.ScriptRuntime.isNaN;
import static rhino.ScriptRuntime.toNumber;

public class 工具方法 {
    public static final Button.ButtonStyle infoStyle = scene.getStyle(Button.ButtonStyle.class);
    public static boolean 规则1 = state.rules.editor && state.rules.infiniteResources;

    final Vec2 bound = new Vec2();

    public static boolean validPosInt(String text) {
        return text.matches("^\\d+(\\.\\d*)?([Ee]\\d+)?$");
    }

    /**
     * {@link UnlockableContent#loadIcon}
     */
    public static void 图标更新(UnlockableContent block, int 材质数量, int 过渡帧, float 动画间隔) {
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


    /**
     * {@link Building#updateTile }
     */
    public static void 生命共享(Building build, float 转换) {
        for (Building Z : build.proximity) {
            if (Z.block != build.block)
                continue;
            if (build.health > 1 && build.health > Z.health && Z.health < Z.maxHealth) {
                Z.health += 1 * 转换;
                build.health -= 1 * 转换;
            }
        }
    }

    public static Seq<ItemStack> 物品栈(Object... A) {
        return Seq.with(ItemStack.with(A));
    }

    public static Seq<LiquidStack> 流体栈(Object... A) {
        return Seq.with(LiquidStack.with(
                A
        ));
    }

    @SafeVarargs
    public static <T> ArrayList<T> 集合A(T... AAA) {
        ArrayList<T> A = new ArrayList<>();
        Collections.addAll(A, AAA);
        return 集合去重(A);
    }

    @SafeVarargs
    public static <T> ArrayList<T> 集合B(T... AAA) {
        ArrayList<T> A = new ArrayList<>();
        Collections.addAll(A, AAA);
        return A;
    }

//TODO 我当初在想什么SB事情
//    @SafeVarargs
//    public static <T> T[] 组A(T... a) {
//        return a;
//    }
    //添加
    public static  Ability[] 关于单位能力(ArrayList<Ability> aa, int a) {
        Ability[] S = new  Ability[a];
        int gg = 0;
        for (Ability t : aa) {
            S[gg] = t;
            gg++;
        }
        return S;
    }
    //TODO  改  关于单位能力
    public static  Ability[] 关于单位能力(Ability[] aa, Ability a) {
        Ability[] S = new  Ability[aa.length+1];
        System.arraycopy(aa, 0, S, 0, aa.length);
        S[aa.length+1]=a;
        return S;
    }
    //TODO  改 关于单位武器
    public static  WeaponMount[] 关于单位能力(WeaponMount[] aa, WeaponMount a) {
        WeaponMount[] S = new  WeaponMount[aa.length+1];
        System.arraycopy(aa, 0, S, 0, aa.length);
        S[aa.length+1]=a;
        return S;
    }


    public static <T> ArrayList<T> 集合去重(ArrayList<T> aa) {
        ArrayList<T> A = new ArrayList<>();
        for (T t : aa) {
            if (!A.contains(t)) {
                A.add(t);
            }
        }
        return A;
    }

    public static TextureRegionDrawable TRD(TextureRegion region) {
        return new TextureRegionDrawable(region);
    }

    public static int 矩形单位数量(float x, float y, float width, float height, Boolf<Unit> filter) {
        return Units.count(x, y, width, height, filter);
    }

    public static void 遍历矩形单位附近(@Nullable Team team, float x, float y, float width, float height, Cons<Unit> cons) {
        Units.nearby(team, x, y, width, height, cons);
    }

    public static void 遍历附近单位圆形(@Nullable Team team, float x, float y, float radius, Cons<Unit> cons) {
        Units.nearby(team, x, y, radius, cons);
    }

    public static void 遍历附近单位矩形(float x, float y, float width, float height, Cons<Unit> cons) {
        Groups.unit.intersect(x, y, width, height, cons);
    }

    public static void 遍历团队单位矩形(Team team, float x, float y, float width, float height, Cons<Unit> cons) {
        Units.nearbyEnemies(team, x, y, width, height, cons);
    }

    public static void 遍历团队单位圆形(Team team, float x, float y, float radius, Cons<Unit> cons) {
        Units.nearbyEnemies(team, x, y, radius, cons);
    }

    public static boolean 矩形中是否有敌人(Team team, float x, float y, float width, float height) {
        return Units.nearEnemy(team, x, y, width, height);
    }

    public static void 遍历附近建筑物圆形(Team team, float x, float y, float range, Cons<Building> cons) {
        indexer.allBuildings(x, y, range, cons);
    }

    public static void 遍历附近建筑物矩形(Team team, float x, float y, float width, float height, Cons<Building> cons) {
        indexer.eachBlock(team, Tmp.r1.setCentered(x, y, width * tilesize, height * tilesize), b -> true, cons);
    }

    //返回该团队在某个范围内最接近的盟友
    public static Unit 最近单位(Team team, float x, float y, float range, Boolf<Unit> predicate) {
        return Units.closest(team, x, y, range, predicate);
    }

    public static Building 最近建筑(Team team, float wx, float wy, float range, Boolf<Building> pred) {
        return Units.closestBuilding(team, wx, wy, range, pred);
    }

    /**********还有其他的呢？TODO*/
    public static <T extends Building> Consume 动态消耗流体(Func<T, LiquidStack[]> items) {
        return new 动态流体消耗(items);
    }

    public static <T extends Building> Consume 动态消耗物品(Func<T, ItemStack[]> items) {
        return new ConsumeItemDynamic(items);
    }

    public static <T extends Building> Consume 动态消耗载荷(Func<T, Seq<PayloadStack>> payloads) {
        return new ConsumePayloadDynamic(payloads);
    }

    public static Consume 动态消耗电力(Floatf<Building> usage) {
        return new ConsumePowerDynamic(usage);
    }

    public static Consume 条件消耗电力(float usage, Boolf<Building> consume) {
        return new ConsumePowerCondition(usage, consume);
    }

    public static Consume 消耗物品(ItemStack... items) {
        return new ConsumeItems(items);
    }

    public static Consume 消耗物品(Item item, int amount) {
        return new ConsumeItems(new ItemStack[]{new ItemStack(item, amount)});
    }

    public static Mods.LoadedMod 定位模组(String name) {
        return mods.locateMod(name);
    }

    public static boolean 有模组吗(String namesfad) {
        return mods.locateMod(namesfad) == null;
    }

    public static String FanYi(String a, Object... b) {
        return Core.bundle.format(a, b);
    }

    public static String FanYi(String a) {
        return Core.bundle.get(a);
    }

    public static TextureRegion 材质(String a) {
        return Core.atlas.find(a, Core.atlas.find("clear"));
    }

    //方块，单位，流体，物品，状态，天气，地图，星球
    public static MappableContent 映射内容(ContentType a, String b) {
        return content.getByName(a, b);

    }

    public static Content 映射内容(ContentType a, int ID) {
        return content.getByID(a, ID);

    }

    public static Stat 统计内容(String a) {
        return new Stat(a);
    }

    public static Stat 统计内容(String a, StatCat b) {
        return new Stat(a, b);

    }

    public static StatUnit 统计单位(String a) {
        return new StatUnit(a);

    }

    public static StatUnit 统计单位(String a, boolean b) {
        return new StatUnit(a, b);

    }

    public static StatUnit 统计单位(String a, String b) {
        return new StatUnit(a, b);

    }

    public static StatCat 统计类别(String a) {
        return new StatCat(a);

    }


//    public static Consume 消耗放电物品(float minCharge) {
//        return new ConsumeItemCharged(minCharge);
//    }

    public static Attribute 设置属性(String a) {
        return Attribute.add(a);

    }

    public static Attribute 读取属性(String a) {
        return Attribute.get(a);

    }

    public static float 属性数据(String a) {
        return Attribute.get(a).env();

    }

    public static Sound 声音(String a) {//??
        FileTree aa = new FileTree();
        return aa.loadSound(a);
    }

    public static Music 音乐(String a) {//??
        FileTree aa = new FileTree();
        return aa.loadMusic(a);
    }

    public static Team 团队(int a) {
        if (a < 0 || a > 256) return Team.derelict;
        if (a < 6) {
            return Team.baseTeams[a];
        } else {
            return Team.all[a];
        }
    }

    public static void 闪电(Team team, Color color, float damage, float x, float y, float 角度, int length) {
        Lightning.create(team, color, damage, x, y, 角度, length);
    }

    public static void 闪电(Bullet bullet, Color color, float damage, float x, float y, float 角度, int length) {
        Lightning.create(bullet, color, damage, x, y, 角度, length);
    }

    //一种 条目dsgdfgdfg
    public static void dfgfdgf(Table ggg, String number1, float sss, Color fdsg) {
        Table t = new Table();
        t.background(Tex.whiteui);
        t.setColor(Pal.darkestGray);
        Table time = new Table();
        final float[] duration = {0f};
        time.update(() -> {
            duration[0] += Time.delta;
            if (duration[0] > sss) {
                duration[0] = 0f;
                time.remove();
                t.remove();
            }
        });
        Cell<Bar> barCell = time.add(new Bar(() -> number1,
                        () -> fdsg,
                        () -> Interp.smooth.apply(duration[0] / sss)))
                .height(45f);
        if (mobile)
            barCell.width(220f);
        else
            barCell.width(250f);
        Cell<Table> timeCell = t.add(time).pad(12f);
        ggg.add(t).pad(10f).grow();
        ggg.row();
        ggg.defaults().grow();

    }

    public static void main(String base64String, String 名字) {
        try {
            // 将base64字符串解码为字节数组
            byte[] imageData = Base64.getDecoder().decode(base64String);

            // 创建一个空的输出流
            OutputStream outputStream = new FileOutputStream(名字 + ".jpg");

            // 将字节数组写入输出流
            outputStream.write(imageData);

            // 关闭输出流
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        int width = 820; // 图片宽
//        int height = 600;// 图片高
//        String titleStr = "公司测试点";
//        String zhangdanzhouqiStr = "2019年2月1日至2019年2月28日"; // 账单周期
//
//        // 得到图片缓冲区
//        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// INT精确度达到一定,RGB三原色，高度70,宽度150
//
//        // 得到它的绘制环境(这张图片的笔)
//        Graphics2D g2 = (Graphics2D) bi.getGraphics();
//        g2.setColor(java.awt.Color.WHITE); // 设置背景颜色
//        g2.fillRect(0, 0, width, height);// 填充整张图片(其实就是设置背景颜色)
//        g2.setColor(java.awt.Color.black);// 设置字体颜色
//        g2.setStroke(new BasicStroke(2.0f)); // 边框加粗
//        g2.drawRect(1, 1, width - 2, height - 2); // 画边框就是黑边框
//
//        g2.drawLine(0, 80, 820, 80); // 从上到下第二个横线(标题下面横线)
//        g2.setStroke(new BasicStroke(0.0f)); // 边框不需要加粗
//
//        // 设置标题的字体,字号,大小
//        Font titleFont = new Font("宋体", Font.BOLD, 30);
//        g2.setFont(titleFont);
//        String markNameStr = titleStr;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 抗锯齿
//        // 计算文字长度,计算居中的X点坐标
//        FontMetrics fm = g2.getFontMetrics(titleFont);
//        int titleWidth = fm.stringWidth(markNameStr);
//        int titleWidthX = (width - titleWidth) / 2 - 35;// 感觉不居中,向左移动35个单位
//        g2.drawString(markNameStr + "节能账单", titleWidthX, 45);
//
//        // 账单周期
//        g2.setFont(new Font("宋体", Font.BOLD, 20));
//        g2.drawString("账单周期", 33, 125);
//        // 账单周期的值
//        g2.drawString(zhangdanzhouqiStr, 230, 125);
//        g2.dispose(); // 释放对象
//        ImageIO.write(bi, "JPEG", new FileOutputStream("D:/a.jpg"));// 保存图片 JPEG表示保存格式
//    }

    public boolean validNumber(String str) {
        try {
            double d = toNumber(str);
            return Math.abs(d) < 1E6 && !isNaN(d);
        } catch (Exception ignored) {
        }
        return false;
    }

    public boolean 可以创建单位(Team team, UnitType type) {
        return Units.canCreate(team, type);
    }

    public int 单位上限(Team team) {
        return Units.getCap(team);
    }

    public boolean 验证目标(Posc 目标, Team team, float x, float y, float range) {
        return Units.invalidateTarget(目标, team, x, y, range);

    }

    public boolean 地板实体(Tile tile) {
        return Units.anyEntities(tile);
    }

    public Building 查找损坏(Team team, float x, float y) {
        return Units.findDamagedTile(team, x, y);
    }

    public Building 查找盟友(Team team, float x, float y, float range, Boolf<Building> pred) {
        return Units.findAllyTile(team, x, y, range, pred);
    }

    public Building 查找敌人(Team team, float x, float y, float range, Boolf<Building> pred) {
        return Units.findEnemyTile(team, x, y, range, pred);
    }

    public void 附近建筑(float x, float y, float range, Cons<Building> cons) {
        Units.nearbyBuildings(x, y, range, cons);
    }

    public Teamc 最近的目标(Team team, float x, float y, float range) {
        return Units.closestTarget(team, x, y, range);
    }

    public Consume 消耗流体(Liquid liquid, float amount) {
        return new ConsumeLiquid(liquid, amount);
    }

    public Consume 消耗流体(LiquidStack... stacks) {
        return new ConsumeLiquids(stacks);
    }

    /**********还有其他的呢？TODO
     * @return*/


//
//    public void 选择sgfdgfd建造(Table ue){
//     ue.pane(选择建造(Blocks.air));
//
//    };
    public ImageButton 选择建造(Block block) {
        if (state.isGame()) {
            ImageButton button = new ImageButton(new TextureRegionDrawable(block.uiIcon), Styles.clearNonei);
            button.setDisabled(() -> !block.unlockedNow() || !block.placeablePlayer || !block.environmentBuildable() || !block.supportsEnv(state.rules.env));
            button.clicked(() -> {
                while (scene.hasDialog()) {
                    scene.getDialog().hide();
                }
                control.input.block = control.input.block == block ? null : block;
            });
            button.margin(5);
            button.setSize(40);
            button.setPosition(bound.x, 0, Align.topRight);
            button.name = "block-" + block.name;
            return button;
        }
        return new ImageButton(new TextureRegionDrawable(Blocks.air.uiIcon));

    }


    //TODO 大师傅赶得上

    public void 闪电(Tile tile, Team team, float x, float y, Sound 闪电声音, Color 闪电颜色, float 闪电伤害, float 角度, int 闪电长度) {
        Lightning.create(team, 闪电颜色, 闪电伤害, x, y, 角度, 闪电长度);
        闪电声音.at(tile, Mathf.random(0.9f, 1.1f));
    }

    public void 闪电(Building buid, Sound 闪电声音, Color 闪电颜色, float 闪电伤害, float 角度, int 闪电长度) {
        闪电(buid.tile, buid.team, buid.x, buid.y, 闪电声音, 闪电颜色, 闪电伤害, 角度, 闪电长度);
    }

    public void 闪电(Building buid, float 闪电伤害, float 角度, int 闪电长度) {
        闪电(buid.tile, buid.team, buid.x, buid.y, Sounds.spark, Pal.surge, 闪电伤害, 角度, 闪电长度);
    }

    public void 创建单位(UnitType 单位, Team 阵营, float x, float y, float 角度) {
        Unit u = 单位.create(阵营);
        u.set(x + Mathf.range(2), y + Mathf.range(2));
        u.rotation = 角度;
        u.add();
//        u.kill();
//        u.remove();
//        u.dead = true;
//        u.health = 0;
    }

    public static class 数组 {
        public float x = 0, y = 0;

        public 数组(float x, float y) {

            this.x = x;
            this.y = y;
        }
    }
}
