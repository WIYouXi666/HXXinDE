package HuanXiangLIB.核心内容;

import HuanXiangLIB.注册类.修正;
import arc.Events;
import arc.scene.ui.layout.Table;
import arc.util.serialization.Json;
import arc.util.serialization.JsonValue;

import java.util.ArrayList;

import static HuanXiangLIB.核心内容.总集类.修正组;
import static HuanXiangLIB.核心内容.新事件.事件枚举.修正记录变更;

public class 修正类记录 implements Json.JsonSerializable{
    public static void 修正加(修正 name) {
        修正记录.add(name);
        Events.fire(修正记录变更);

    }
    public static Table A;
    public static void 修正减(修正 name) {
        if (修正记录.contains(name)) {
            修正记录.remove(name);
            Events.fire(修正记录变更);
        }
    }

    public static ArrayList<修正> 修正记录 = new ArrayList<>();

    public static void 加载() {
        for (修正 ignored : 修正记录) {
            ignored.ffffff(A);
        }

    }

    @Override
    public void write(Json json){
        for (修正 修正 : 修正组) {
           if (修正记录.contains(修正)) {
               json.writeValue(修正.id() +"", 修正, 修正.class);
           }
        }

    }

    @Override
    public void read(Json json, JsonValue jsonData){
        for(JsonValue value : jsonData){
            修正 f = json.readValue(修正.class, value);
            修正记录.add(f);
        }
    }
}
