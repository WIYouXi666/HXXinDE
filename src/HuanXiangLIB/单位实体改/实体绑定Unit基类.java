package HuanXiangLIB.单位实体改;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.EntityGroup;
import mindustry.gen.EntityMapping;
import mindustry.gen.Entityc;
import mindustry.gen.Groups;
import mindustry.gen.Unitc;

public class 实体绑定Unit基类 implements Entityc{
    public transient int id = EntityGroup.nextId();

    protected transient boolean added;

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

   @Override
    public boolean isAdded() {
        return this.added;
    }
@Override
    public boolean isLocal() {
        return true;
    }
@Override
    public boolean isNull() {
        return false;
    }
@Override
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
        return EntityMapping.register(this.toString(), 实体绑定Unit基类::new);
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
            Groups.all.addIndex(this);
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
            Groups.all.remove(this);
            this.added = false;
        }
    }


    @Override
    public void update() {

    }






    @Override
    public void write(Writes write) {


    }

    @Override
    public void read(Reads read) {


    }




}

