package HuanXiangLIB.单位实体改;

import HuanXiangLIB.接口.执行类;
import arc.struct.ObjectMap;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.UnitController;

public class 单位注册类 implements 执行类 {
    public static ObjectMap<String, Object> 记录 = new ObjectMap<>();


    @Override
    public void 执行() {

    }


    @Override
    public void 异步执行() {
        记录.put("注册能力",注册能力.注册);
        记录.put("注册挂载",注册挂载.注册);
        记录.put("注册操作",注册操作.注册);
    }


    public static class 注册能力 extends 单位注册类{
        public static ObjectMap<String, Ability> 注册 = new ObjectMap<>();
        public 注册能力(String name, Ability A) {
            注册.put(name,A);
        }

    }

    public static class 注册挂载 extends 单位注册类{
        public static ObjectMap<String, 武器挂载> 注册 = new ObjectMap<>();
        public 注册挂载(String name, 武器挂载 A) {
            注册.put(name,A);
        }


    }
    public static class 注册操作  extends 单位注册类{
        public static ObjectMap<String, UnitController> 注册 = new ObjectMap<>();
        public 注册操作(String name, UnitController A) {
            注册.put(name,A);
        }


    }


}
