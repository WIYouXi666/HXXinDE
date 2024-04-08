package HuanXiangLIB.Unit$武器$能力;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

import static HuanXiangLIB.libxx.gn添加聊天信息;

public class adcf {
    public static ObjectMap<Integer, adcf> alll = new ObjectMap<>();
    public static final Seq<adcf> all = new Seq<>();
    public transient Ability[] 能力 = new Ability[0];
    public transient WeaponMount[] 武器 = new WeaponMount[0];
    public transient Unit unit;


    public adcf setZ(boolean z) {
        this.z = z;
        return this;
    }

    boolean z = true;
    int id;

    public adcf(Unit unit, Ability[] 能力, WeaponMount[] 武器) {
        this.能力 = 能力;
        this.武器 = 武器;
        this.unit = unit;
        alll.put(unit.id(),this);
        id = all.size;
        all.add(this);

    }


    public void setUnit(Unit unit) {
        this.unit = unit;
    }



    public void update() {


      //  if (Groups.unit.getByID(id - 1) == null) this.remove();
        gn添加聊天信息("" + unit);
        // set(unit.tileX(),unit.tileY());

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

}
