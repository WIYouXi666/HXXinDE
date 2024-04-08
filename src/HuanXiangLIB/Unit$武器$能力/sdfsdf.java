package HuanXiangLIB.Unit$武器$能力;

import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.serialization.Json;
import arc.util.serialization.JsonValue;
import mindustry.entities.EntityGroup;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.io.TypeIO;

import static HuanXiangLIB.libxx.gn添加聊天信息;

public class sdfsdf implements Entityc, Json.JsonSerializable{
    public transient int id = EntityGroup.nextId();
    protected transient boolean added;
    protected transient int index__all = -1;
    public transient Ability[] 能力 = new Ability[0];
    public transient WeaponMount[] 武器 = new WeaponMount[0];
    public transient Unit unit;
    boolean z = true;
    float h= 1;

    @Override
    public <T extends Entityc> T self() {
        return (T) this;
    }

    @Override
    public <T> T as() {
        return (T) this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean isAdded() {
        return this.added;
    }

    public boolean isLocal() {
        return true;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isRemote() {
        boolean var10000;
        if (this instanceof Unitc) {
            Unitc u = (Unitc) this;
            if (u.isPlayer() && !this.isLocal()) {
                var10000 = true;
                return var10000;
            }
        }

        var10000 = false;
        return var10000;
    }

    /**
     * @return
     */
    @Override
    public boolean serialize() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public int classId() {
        return EntityMapping.register(this.toString(), sdfsdf::new);
    }

    @Override
    public int id() {
        return id;
    }

    /**
     *
     */
    @Override
    public void add() {
        if (!this.added) {
            this.index__all = Groups.all.addIndex(this);
            this.added = true;

        }
    }


    @Override
    public void afterRead() {

    }


    @Override
    public void id(int i) {
        this.id = i;
    }



    @Override
    public void remove() {
        if (this.added) {
            Groups.all.removeIndex(this, this.index__all);
//            Groups.sync.remove(this);
            this.index__all = -1;
            this.added = false;
        }
    }


    @Override
    public void update() {
h++;

       // if (Groups.unit.getByID(id - 1) == null) this.remove();
       // gn添加聊天信息("" + id + " ");
        gn添加聊天信息("" + h);
        // set(unit.tileX(),unit.tileY());
        if (h>600) remove();
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

    public void zz() {
        unit = Groups.unit.getByID(id - 1);
    }

    @Override
    public void write(Writes write) {
        TypeIO.writeAbilities(write, 能力);
        TypeIO.writeMounts(write, 武器);

    }

    @Override
    public void read(Reads read) {
        zz();
        能力 = TypeIO.readAbilities(read, 能力);
        武器 = TypeIO.readMounts(read, 武器);

    }



    @Override
    public void write(Json json) {
        json.writeValue("能力长",能力.length);
        for (int i = 0; i<能力.length;i++){
            json.writeValue("能力"+i,能力[i]);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        能力 = new Ability[jsonValue.getInt("能力长")];
        for (int i = 0; i<能力.length;i++){
            能力[i] = json.readValue("能力"+i, Ability.class, jsonValue);
        }
    }
}

