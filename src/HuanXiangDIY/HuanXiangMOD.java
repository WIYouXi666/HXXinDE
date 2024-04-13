package HuanXiangDIY;

import HuanXiangLIB.*;
import HuanXiangLIB.ui类.对话框.核心数据库对话框;
import HuanXiangLIB.ui类.开局加载对话框;
import HuanXiangDIY.content.HXPlanets;
import HuanXiangDIY.content.自动生成;
import HuanXiangDIY.内容.局内ui内容.uilib;
import HuanXiangDIY.内容.标签内容;
import HuanXiangLIB.记录$功能.全部内容;
import arc.Events;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

import static HuanXiangLIB.ui类.开局加载对话框.异步;

public class HuanXiangMOD extends Mod {
    public 核心数据库对话框 database;
    public static uilib 主要交互;
    public static 全部内容 dsa ;
    public static Mods.LoadedMod 加载的模组;
    static boolean show = false;
    static boolean shdfsow = false;
    public static String ModName = "hxlid";
    private ClassLoader urlLoader;
    static {
        Time.run(1f, ()->{
            new 对话框().执行();
        });

    }
    public static String StringName(String A) {
      return  ModName +A;
    }
    public HuanXiangMOD() {
    }

    @Override
    public void loadContent() {
//        Package[] yy = Package.getPackages();
//        for (Package aPackage : yy) {
//            c
//        }
//        set文件("测试");
//        get配置("测试","kk");
        标签内容.创建();








        HXPlanets.load();
        //全部事件执行.load();
        自动生成.load();
        js.DawnMods();
        事件处理器.执行();
    }
    @Override
    public void init() {

         database = new 核心数据库对话框();
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
