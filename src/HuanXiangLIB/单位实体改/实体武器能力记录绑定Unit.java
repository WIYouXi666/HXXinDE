package HuanXiangLIB.单位实体改;

import arc.struct.ObjectMap;
import arc.util.Nullable;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Building;
import mindustry.gen.Entityc;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.io.TypeIO;
import mindustry.type.Weapon;

import static HuanXiangLIB.libxx.gn添加聊天信息;

public class 实体武器能力记录绑定Unit extends 实体绑定Unit基类 implements Entityc {
    protected  boolean added;
    public  String[] 能力FFFF = new String[0];
    public  String[] 挂载FFFF = new String[0];
    public  Ability[] 能力 = new Ability[0];
    public  WeaponMount[] 武器挂载 = new 武器挂载[0];
    public  Weapon[] 武器 = new Weapon[0];
    public  boolean z = true;
    public  boolean m = true;
    protected transient int index__all = -1;
    protected transient int index__build = -1;
    public void set绑定unitid(int 绑定unitid) {
        this.绑定unitid = 绑定unitid;
    }

    public int 绑定unitid = -1;
    public int 绑定buildid = -1;
    public @Nullable Unit unit;
    public @Nullable Building build;

    public void 加能力(String... a) {
        if (a == null) return;
        能力FFFF = new String[a.length];
        for (int i = 0; i < a.length; ++i) {
            能力FFFF[i] =a[i];
        }
    }

    public void 加武器(String... a) {
        if (a == null) return;
        挂载FFFF = new String[a.length];
        for (int i = 0; i < a.length; ++i) {
            挂载FFFF[i] =a[i];
        }
    }


    public void yici() {
        能力 = 能力转换(能力FFFF);
    }

    @Override
    public void update() {
        if (m){
            gn添加聊天信息(""+Groups.unit.size());
            m  = false;
        }
        if (Groups.unit.getByID(绑定unitid) == null) {
          this.remove();
        }
        if (绑定buildid != -1) {
            build = Groups.build.getByID(绑定buildid);
            if (build == null ) {
                绑定buildid = -1;
            }
        }
        if (绑定unitid != -1) {
            unit = Groups.unit.getByID(绑定unitid);
            if (unit == null) {
                绑定unitid = -1;
                this.remove();
            }
        }

        if (z && unit != null){
        unit.abilities = new Ability[能力.length];
        for (int i = 0; i < 能力.length; ++i) {
            unit.abilities[i] = 能力[i].copy();
        }

        if (unit.mounts().length != 武器.length) {
            unit.mounts = new 武器挂载[武器.length];

            for (int i = 0; i < unit.mounts.length; ++i) {
                unit.mounts[i] = 武器[i].mountType.get(武器[i]);
            }
            武器挂载 = new 武器挂载[unit.mounts().length];
            System.arraycopy(unit.mounts, 0, 武器挂载, 0, unit.mounts.length);
        }
//            Events.run(EventType.Trigger.draw, () -> {
//                Draw.color(unit.team.color, Color.white, Mathf.clamp(1));
//                if (renderer.animateShields) {
//                    Draw.z(Layer.shields + 0.001f * 1);
//                    Fill.poly(unit.x+100, unit.y+100, 6, 100, 180);}
//            });
            //转换("注册挂载", 挂载FFFF, 武器挂载);
            gn添加聊天信息(""+id());
            gn添加聊天信息(""+绑定unitid);

//            for (Unit unit1 : Groups.unit) {
//                gn添加聊天信息(""+unit1.id());
//            }

//            gn添加聊天信息(""+能力FFFF.length);
//            gn添加聊天信息(""+能力.length);
//            gn添加聊天信息(""+unit.abilities.length);
           // gn添加聊天信息(能力[0].toString());
            z = false;
        }
    }


    @Override
    public void remove() {
        if (this.added) {
            Groups.all.removeIndex(this, this.index__all);
            this.index__all = -1;
            this.added = false;
        }
    }


    @Override
    public void add() {
        if (!this.added && unit == null) {
            this.index__all = Groups.all.addIndex(this);
            this.added = true;

        }
    }

    @Override
    public void write(Writes write) {


//        写入String(write,能力FFFF);
//        写入String(write,挂载FFFF);
//
//        TypeIO.writeAbilities(write, 能力);
//        TypeIO.writeMounts(write, 武器挂载);
       write.i(unit == null ? -1 : unit.id);
        write.i(build == null ? -1 : build.id);
    }

    @Override
    public void read(Reads read) {
        m=true;
        能力FFFF=读取String(read,能力FFFF);
        挂载FFFF=读取String(read,挂载FFFF);

        Ability[] 能力t = 能力转换(能力FFFF);




      //  转换( 挂载FFFF, 武器挂载);



        能力 = TypeIO.readAbilities(read, 能力t);
        武器挂载 = TypeIO.readMounts(read, 武器挂载);
        绑定unitid = read.i();
        绑定buildid = read.i();
    }
    public static Ability[] 能力转换(String... a) {
        Ability[] c = new Ability[a.length];
        ObjectMap<String, Ability> 注册v = 单位注册类.注册能力.注册;
        for (int i = 0; i < a.length; ++i) {
           if(注册v.containsKey(a[i])){
               c[i] = 注册v.get(a[i]);
           }
        }
        return c;
    }


    public static void 写入String(Writes write, String[] abilities) {
        write.b(abilities.length);
        int a = abilities.length;
        for (int i = 0; i < a; ++i) {
            write.str(abilities[i]);
        }

    }

    public static String[] 读取String(Reads read,String[] abilities) {
        byte len = read.b();
        abilities =  new String[len];
        for (int i = 0; i < len; ++i) {
            abilities[i] = read.str();;
        }
        return abilities;
    }

}
