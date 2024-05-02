package HuanXiangLIB.核心内容;

import HuanXiangLIB.注册类.修正;
import arc.struct.ObjectMap;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.UnitController;
import mindustry.entities.units.WeaponMount;

import java.util.ArrayList;

public class 总集类 {
    public static final ArrayList<注册类.配方> 配方组 = new ArrayList<>();
    public static final ObjectMap<String, 注册类.配方> 配方映射 = new ObjectMap<>();

    public static final ArrayList<注册类.单位技能> 单位技能组 = new ArrayList<>();
    public static final ObjectMap<String, 注册类.单位技能> 单位技能映射 = new ObjectMap<>();

    public static final ArrayList<注册类.辞典> 辞典组 = new ArrayList<>();
    public static final ObjectMap<Integer, 注册类.辞典> 辞典映射 = new ObjectMap<>();
    public static final ObjectMap<UnlockableContent, ArrayList<注册类.辞典>> 内容含有的辞典 = new ObjectMap<>();
    public static final ObjectMap<注册类.辞典, ArrayList<UnlockableContent>> 辞典含有的内容 = new ObjectMap<>();

    public static final ArrayList<修正> 修正组 = new ArrayList<>();
    public static final ObjectMap<Integer, 修正> 修正映射 = new ObjectMap<>();



    public static final ArrayList<注册类.单位组装> 单位组装组 = new ArrayList<>();
    public static final ObjectMap<Integer, 注册类.单位组装> 单位组装映射 = new ObjectMap<>();


    public static final ObjectMap<String, Ability> 能力注册 = new ObjectMap<>();
    public static final ObjectMap<String, WeaponMount> 挂载注册 = new ObjectMap<>();
    public static  final ObjectMap<String, UnitController> 操作注册 = new ObjectMap<>();


}
