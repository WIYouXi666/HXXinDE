package MODS;

import MODS.core内容.ui.核心数据库;
import MODS.core内容.ui.科技树;
import MODS.关于文件.关于地图保存.地图保存;
import MODS.关于文件.翻译.翻译类;
import MODS.关于文件.配置文件.配置基本;
import MODS.关于输入.关于输入处理;
import arc.Core;
import arc.assets.Loadable;
import arc.files.Fi;
import arc.graphics.Color;
import arc.scene.ui.Button;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.Content;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.gen.Building;
import mindustry.gen.Tex;
import mindustry.world.Block;
import mindustry.world.Tile;

import java.util.Objects;

import static MODS.HuanXiangMOD.ModName;
import static arc.Core.scene;
import static mindustry.Vars.*;

public class HX_Vars implements Loadable {
    public static Tile 点击的Tile;
    public static 关于输入处理 关于输入;
    public static Seq<String> 标准 = Seq.with("en", "fr", "de", "it", "ja", "ko", "zh", "zh_CN", "zh_TW", "fr_FR", "de_DE", "it_IT", "ja_JP", "ko_KR", "en_GB", "en_US", "en_CA", "fr_CA");
    public static Seq<String> 保存键表 = Seq.with("基本", "单位", "限制", "标签");
    public static 地图保存 保存s;
    public static String S_a = "占位";
    public static String VN = ".TXT";
    public static 核心数据库 数据库;
    public static 科技树 c;
    public static Button.ButtonStyle infoStyle;
    public static final Color 普通 = new Color(255, 255, 255);
    public static final Color 稀有 = new Color(150, 0, 255);
    public static final Color 金光 = new Color(242, 192, 86);
    public static Fi 保存目录;
    public static Fi 配置文件;
    public static Fi 翻译目录;


    public static final Seq<Runnable> 异步更新 = new Seq<>();
    public static final Seq<翻译类> 翻译类表 = new Seq<>();
    public static Seq<配置基本> 配置表 = new Seq<>();

    public static ObjectMap<String, String> 全部配置信息 = new ObjectMap<>();
    public static ObjectMap<String, String> 当前地图全部信息 = new ObjectMap<>();


    public static final ObjectMap<Block, HX_外_Building> WAI_Building = new ObjectMap<>();

    public static final ObjectMap<String, Objectives.Objective> 科技树目标 = new ObjectMap<>();
    /**
     * 关于核心数据库
     */
    public static final ObjectMap<String, Seq<Content>> 数据库内容 = new ObjectMap<>();


    /**
     * 关于初始化
     */
    public static void init() {
        infoStyle = scene.getStyle(Button.ButtonStyle.class);
        保存目录 = dataDirectory.child("全部文件/保存目录/");
        配置文件 = dataDirectory.child("全部文件/配置文件/");
        翻译目录 = dataDirectory.child("全部文件/翻译目录/");
        关于输入 = new 关于输入处理();
        保存s = new 地图保存();
        保存s.init();

        for (配置基本 配置基本 : 配置表) {
            配置基本.加载();
        }
        数据库 = new 核心数据库();
        c = new 科技树();
        点击的Tile = new Tile(0, 0);
    }

    public static String getModName() {
        return ModName;
    }

    public static boolean unlocked(Block block) {
        return block.unlockedNow() && block.placeablePlayer && block.environmentBuildable() &&
                block.supportsEnv(state.rules.env);
    }
    public static boolean unlocked(UnlockableContent content) {
        return (!Vars.state.isCampaign() && !Vars.state.isMenu()) || content.unlocked();
    }

    public static void 数据库put(String A, Seq<Content> B) {
        Seq<Content> AAAA = new Seq<>();
        if (数据库内容.get(A) != null) {
            AAAA = 数据库内容.get(A);
        }
        for (Content content : B) {
            String aa = 已存在(content);
            if (!Objects.equals(aa, A)) {
                if (aa != null) {
                    rrrr(aa, content);
                    continue;
                }
            }
            AAAA.addUnique(content);
        }
        数据库内容.put(A, AAAA);

    }

    public static boolean get配置(Block block) {
        return WAI_Building.containsKey(block) && WAI_Building.get(block).配置;
    }

    public static void 数据库put(String A, Content... B) {
        Seq<Content> a = new Seq<>();
        for (Content content : B) {
            a.addUnique(content);
        }
        数据库put(A, a);

    }


    public static String 已存在(Content j) {
        for (ObjectMap.Entry<String, Seq<Content>> stringSeqEntry : 数据库内容) {
            Seq<Content> aa = stringSeqEntry.value;
            String bb = stringSeqEntry.key;
            if (aa.contains(j)) return bb;
        }
        return null;
    }

    public static void rrrr(String A, Content... B) {
        Seq<Content> AAAA = new Seq<>();
        if (数据库内容.get(A) != null) {
            AAAA = 数据库内容.get(A);
        }
        for (Content content : B) {
            AAAA.remove(content);
        }
        数据库内容.put(A, AAAA);
    }

    public static Double Double(String H) {
        return java.lang.Double.valueOf(H);
    }

    public static Long Long(String H) {
        return java.lang.Long.valueOf(H);
    }

    public static Boolean Boolean(String H) {
        return java.lang.Boolean.valueOf(H);
    }


    public static Seq<Tile> 遍历方块覆盖(Block block, Building bb, int 范围) {
        Seq<Tile> hh = new Seq<>();
        int size = block.size + 范围;
        int 偏移量x = -(size - 1 + 范围 * 2) / 2;
        int 偏移量y = -(size - 1 + 范围 * 2) / 2;
        for (int dx = 0; dx < size; dx++) {
            for (int dy = 0; dy < size; dy++) {
                int wx = dx + 偏移量x + bb.tile.x, wy = dy + 偏移量y + bb.tile.y;
                Tile check = world.tile(wx, wy);
                hh.add(check);
            }
        }
        return hh;
    }

    /**
     * 关于测试
     */

    public static void 测试信息(String txt) {
        if (Core.settings.getBool("CeShi")) {
            Log.info(txt);
        }
    }

    /**
     * 常规方法
     */
    public void 设置下划线标题(Table i, String ff) {
        //设置一个下划线标题
        i.table(Tex.underline, (t) -> {
            t.add(ff);
        }).colspan(3).height(40.0F).width(50 * 3.0F + 3.0F).padBottom(3.0F).color(new Color(255, 255, 255));

    }

    public static boolean 依赖项目(UnlockableContent content, String ff) {
        if (content.minfo.mod == null) return false;
        var mod = content.minfo.mod;
        if (mod.meta.dependencies.size != 0) {
            for (String dependency : mod.meta.dependencies) {
                if (Objects.equals(dependency, ff)) {
                    return true;
                }
            }
        }
        return Objects.equals(mod.name, ff);
    }

}
