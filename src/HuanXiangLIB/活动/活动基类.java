package HuanXiangLIB.活动;

import arc.func.Prov;
import arc.graphics.g2d.TextureRegion;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;

public abstract class 活动基类 {
    public static final 活动基类 NULL = new 活动基类("null",null) {
        @Override
        void 执行() {
        }
    };
    public Prov<? extends 活动实体> eventProv = 活动实体::new;
   private final ObjectMap<String, 活动基类> 活动映射 = new ObjectMap<>();
   private final Seq<活动基类> 活动存储 = new Seq<>();

    public Seq<活动基类> 活动存储() {
        return 活动存储;
    }

    public ObjectMap<String, 活动基类> 活动映射() {
        return 活动映射;
    }


    public String name;
    public int id;
    public @Nullable TextureRegion icon;
    public int 强度 = 1;

    public 活动基类(String name, TextureRegion icon) {
        this.name = name;
        this.icon = icon;
        活动映射.put(name, this);
        id = 活动存储.size;
        活动存储.add(this);
    }
    abstract void 执行();

    public <T extends 活动实体> T create(){
        T event = (T)eventProv.get();
        event.type = this;
//
//        event.init();

//        if(initPos != -1 && hasCoord){
//            Tmp.p1.set(Point2.unpack(initPos));
//            event.set(Tmp.p1.x * tilesize, Tmp.p1.y * tilesize);
//        }

        event.add();

        return event;
    }

}
