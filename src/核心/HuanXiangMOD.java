package 核心;

import arc.Events;
import arc.files.Fi;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.mod.Mods;
import 实际内容.content.HXPlanets;
import 实际内容.content.自动生成;
import 核心.ui类.ui内容主体;
import 核心.ui类.交互窗.核心数据库;
import 核心.ui类.开局加载对话框;
import 核心.内容.局内ui内容;
import 核心.内容.标签内容;
import 核心.标签.核心标签;

import static 核心.ui类.开局加载对话框.异步;

public class HuanXiangMOD extends Mod {
    public 核心数据库 database;
    public static ui内容主体 主要交互;
    public static final Fi 数据目录 = Vars.modDirectory.child("配置文件");
    public static Fi A = Vars.dataDirectory.child("翻译文件");
    public static Fi B = A.child("bundles");
    public static Fi 中文 = B.child("bundle_zh_CN.properties");
    public static Fi 主要 = A.child("mod.json");
    public static Mods.LoadedMod 加载的模组;
    static boolean show = false;
    static boolean shdfsow = false;
    public static String ModName = "hxlid";
    private ClassLoader urlLoader;

    public static String StringName(String A) {
      return  ModName +A;
    }
    public HuanXiangMOD() {
    }

    @Override
    public void loadContent() {
        标签内容.创建();
        Log.info(核心标签.get("jk"));

        局内ui内容.load();








        Log.info("fggfhfhfgh");
        HXPlanets.load();
        //全部事件执行.load();
        自动生成.load();
        js.DawnMods();
    }
    @Override
    public void init() {
        包导入.hh ();
         database = new 核心数据库();
//        Class<ContentType>  fws = ContentType.class;
//        Constructor<?>[] gfds = fws.getDeclaredConstructors();
//        for (Constructor<?> gfd : gfds) {
//            try {
//                gfd.newInstance("分的");
//            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//                throw new RuntimeException(e);
//            }
//        }
//new 单位状态更新系统().主();

        if(Vars.ui != null) {
            异步();
            /**关于异步加载*/
            Events.run(EventType.ClientLoadEvent.class, 开局加载对话框::show);
        }
    }

//    public class asfdass extends Content{
//        /**
//         * Returns the type name of this piece of content.
//         * This should return the same value for all instances of this content type.
//         */
//        @Override
//        public ContentType getContentType() {
//            return null;
//        }
//    }
}
