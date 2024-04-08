package HuanXiangLIB.Unit$武器$能力;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.EntityGroup;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.io.TypeIO;

import static HuanXiangLIB.libxx.gn添加聊天信息;

public class 武器$能力实体 extends Bullet {
    protected transient boolean added;
    public transient int id = EntityGroup.nextId();
    public Ability[] 能力 = new Ability[0];
    public WeaponMount[] 武器 = new WeaponMount[0];

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public transient Unit unit;
    boolean z = false;

    @Override
    public void update() {


        this.vel.set(x, y);
        if (Groups.unit.getByID(id - 1) == null) this.remove();
        gn添加聊天信息("" + id + " " + x + y);
        gn添加聊天信息("" + unit);
        // set(unit.tileX(),unit.tileY());
        x = unit.x;
        y = unit.y;

        if (z) {

            unit.abilities = new Ability[能力.length];
            for (int i = 0; i < 能力.length; i++) {
                unit.abilities[i] = 能力[i].copy();
            }


//            unit.mounts = new WeaponMount[能力.length];
//            System.arraycopy(武器, 0, unit.mounts, 0, 能力.length);
            z = false;
        }
    }


    public void 加能力(Ability... a) {
        if (a == null) return;
        能力 = new Ability[a.length];
        for (int i = 0; i < a.length; i++) {
            能力[i] = a[i];
        }
    }

    public void 加武器(WeaponMount... a) {
        if (a == null) return;
        武器 = new WeaponMount[a.length];
        for (int i = 0; i < a.length; i++) {
            武器[i] = a[i];
        }
    }

    @Override
    public void write(Writes write) {
        super.write(write);
        TypeIO.writeAbilities(write, 能力);
        TypeIO.writeMounts(write, 武器);

    }

    @Override
    public void read(Reads read) {
        super.read(read);
        unit = Groups.unit.getByID(id - 1);
        能力 = TypeIO.readAbilities(read, 能力);
        武器 = TypeIO.readMounts(read, 武器);

    }
    //////单位武器vxcv


    //Syncc
    //记录单位的东西


}
