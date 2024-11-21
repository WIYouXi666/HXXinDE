package MODS.重要内容;

import MODS.core内容.科技树目标;
import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;
import mindustry.type.Planet;
import mindustry.type.SectorPreset;

import static mindustry.content.TechTree.node;

public class 科技树 {
    UnlockableContent parent;
    UnlockableContent content;
    public ItemStack[] requirements;
    public Seq<Objectives.Objective> objectives = new Seq();

    public 科技树(UnlockableContent parent, UnlockableContent content, ItemStack[] requirements, Seq<Objectives.Objective> objectives) {
        this.parent = parent;
        this.content = content;
        this.requirements = requirements;
        this.objectives = objectives;
    }

    public 科技树(UnlockableContent parent, UnlockableContent content, ItemStack[] requirements) {
        this.parent = parent;
        this.content = content;
        this.requirements = requirements;
    }

    public 科技树(UnlockableContent parent, UnlockableContent content, Seq<Objectives.Objective> objectives) {
        this.parent = parent;
        this.content = content;
        this.requirements = content.researchRequirements();
        this.objectives = objectives;
    }

    public 科技树(UnlockableContent parent, UnlockableContent content) {
        this.parent = parent;
        this.content = content;
    }

    public void 添加() {
        TechTree.TechNode jk = TechTree.all.find(i -> i.content == parent);
        if (jk != null) {
            jk.children.add(node(content, requirements, objectives, () -> {
            }));

        }
    }

    public void 替换(boolean 新的) {
        TechTree.TechNode jk = TechTree.all.find(i -> i.content == parent);
        if (jk != null) {
            TechTree.TechNode po = jk.children.find(i -> i.content == content);
            if (po != null) {
                jk.children.remove(po);
                TechTree.TechNode klkl = node(content, 新的 ? requirements : po.requirements, 新的 ? objectives : po.objectives, () -> {
                });
                klkl.children.addAll(po.children);
                jk.children.add(klkl);
            }
        }

    }

    public void 删除() {
        TechTree.TechNode jk = TechTree.all.find(i -> i.content == parent);
        if (jk != null) {
            TechTree.TechNode po = jk.children.find(i -> i.content == content);
            if (po != null) {
                jk.children.remove(po);
            }
        }
    }


    public static Objectives.Objective 研究目标(UnlockableContent content) {
        return new Objectives.Research(content);
    }

    public static Objectives.Objective 生产目标(UnlockableContent content) {
        return new Objectives.Produce(content);
    }

    public static Objectives.Objective 占领地图(SectorPreset zone) {
        return new Objectives.SectorComplete(zone);
    }

    public static Objectives.Objective 到达星球(Planet planet) {
        return new Objectives.OnPlanet(planet);
    }

    public static Objectives.Objective 到达地图(SectorPreset zone) {
        return new Objectives.OnSector(zone);
    }

    public static Objectives.Objective 互斥科技(UnlockableContent content) {
        return new 科技树目标.未研究(content);
    }
    public static Objectives.Objective 无法解锁() {
        return new 科技树目标.无法解锁();
    }


}
