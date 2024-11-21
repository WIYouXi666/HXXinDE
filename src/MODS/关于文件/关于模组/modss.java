package MODS.关于文件.关于模组;

import arc.files.Fi;
import arc.struct.ObjectMap;

import static mindustry.Vars.modDirectory;

public class modss {

    public ObjectMap<String, Fi> 全部文件 = new ObjectMap<>();
    public void init() {
        for (Fi file : modDirectory.list()) {
            全部文件.put(file.nameWithoutExtension(), file);
        }
    }
}
