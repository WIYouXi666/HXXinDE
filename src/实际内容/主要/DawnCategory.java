package 实际内容.主要;

import arc.struct.Seq;
import mindustry.type.Category;

public class DawnCategory {
    public static final Seq<DawnCategory> all = new Seq<>();

    public String name;
    public int id;

    public DawnCategory(String name) {
        id = all.size;
        this.name = name;
        all.add(this);
    }

    public static void My(){
        for(var c : Category.all){
            new DawnCategory(c.name());
        }
    }

    public DawnCategory prev() {
        return all.get((id - 1 + all.size) % all.size);
    }

    public DawnCategory next() {
        return all.get((id + 1) % all.size);
    }

    public static DawnCategory get(Category c){
        return all.get(c.ordinal());
    }

}
