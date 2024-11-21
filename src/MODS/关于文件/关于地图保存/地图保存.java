package MODS.关于文件.关于地图保存;

import MODS.HX_Vars;
import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.util.serialization.Jval;


import static MODS.HX_Vars.保存目录;
import static mindustry.Vars.control;

public class 地图保存 {

    public 保存的信息 保存的信息;
    public Fi 地图匹配文件;
    public ObjectMap<String, Fi> 全部文件 = new ObjectMap<>();

    public 地图保存() {
        保存的信息 = new 保存的信息();
    }


    public void clear() {
        保存的信息.clear();
        地图匹配文件 = null;
    }

    public void init() {
        for (Fi file : 保存目录.list()) {
            全部文件.put(file.nameWithoutExtension(), file);
        }
    }

    public void delete() {
        for (Fi file : 保存目录.list()) {
            file.delete();
        }
    }

    public Runnable 加载() {


        return () -> {
            if (control.saves.getCurrent() != null) {
                ObjectMap<String, String> 全部信息 = new ObjectMap<>();
                Fi 当前地图 = control.saves.getCurrent().file;
                String name = 当前地图.nameWithoutExtension();
                if (全部文件.containsKey(name)) {
                    地图匹配文件 = 全部文件.get(name);

                    if (地图匹配文件.exists()) {
                        Jval.JsonMap zz = Jval.read(地图匹配文件.readString()).asObject();
                        for (ObjectMap.Entry<String, Jval> cc : zz) {
                            全部信息.put(cc.key, cc.value.asString());
                        }
                        保存的信息.clear();
                        保存的信息.set全部信息(全部信息);
                        保存的信息.init();
                    }
                }
            }
        };


    }

    public void 保存() {
        if (control.saves.getCurrent() != null) {
            Fi 当前地图 = control.saves.getCurrent().file;
            String name = 当前地图.nameWithoutExtension();
            保存目录.child(name + HX_Vars.VN).writeString(保存的信息.toString());
        }
    }


}
