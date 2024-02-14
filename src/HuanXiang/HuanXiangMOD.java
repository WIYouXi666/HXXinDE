package HuanXiang;

import HuanXiang.UI__JiaoHu.HXJiaoHu;
import HuanXiang.UI__JiaoHu.JiaoHu;
import HuanXiang.UI__JiaoHu.JiaoHuKaiPing;
import HuanXiang.content.HXPlanets;
import HuanXiang.content.全部翻译;
import HuanXiang.content.自动生成;
import HuanXiang.核心内容.全部事件执行;
import HuanXiang.核心内容.单位状态更新系统;
import HuanXiang.DawnModJS;
import arc.Events;
import arc.files.Fi;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

public class HuanXiangMOD extends Mod {
    public static JiaoHu 主要交互;
    public static final Fi 数据目录 = Vars.modDirectory.child("配置文件");
    public static Fi A = Vars.dataDirectory.child("翻译文件");
    public static Fi B = A.child("bundles");
    public static Fi 中文 = B.child("bundle_zh_CN.properties");
    public static Fi 主要 = A.child("mod.json");
    public static Mods.LoadedMod 加载的模组;
    static boolean show = false;
    public static String ModName = "hxlid";
    private ClassLoader urlLoader;

    public static String StringName(String A) {
      return  ModName +A;
    }
    public HuanXiangMOD() {
    }

    @Override
    public void loadContent() {









        Log.info("fggfhfhfgh");
        自动生成.load();

        全部翻译.load();
        HXPlanets.load();






        HXJiaoHu.load();

        JiaoHuKaiPing.load();


        全部事件执行.load();

DawnModJS.DawnMods();
    }
    @Override
    public void init() {

//        Class<ContentType>  fws = ContentType.class;
//        Constructor<?>[] gfds = fws.getDeclaredConstructors();
//        for (Constructor<?> gfd : gfds) {
//            try {
//                gfd.newInstance("分的");
//            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//                throw new RuntimeException(e);
//            }
//        }


        if(Vars.ui != null) {
            Events.run(EventType.ClientLoadEvent.class, JiaoHuKaiPing::show);
        }
       new 单位状态更新系统().主();

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
