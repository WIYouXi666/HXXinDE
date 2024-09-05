package MODS.内容.关于科技树;

import arc.Core;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.type.SectorPreset;

public class 科技树目标 {

    public static class 研究 implements Objectives.Objective {
        public UnlockableContent content;

        public 研究(UnlockableContent content){
            this.content = content;
        }

        protected 研究(){}

        @Override
        public boolean complete(){
            return content.unlocked();
        }

        @Override
        public String display(){
            return Core.bundle.format("requirement.research",
                    (content.techNode == null || content.techNode.parent == null || content.techNode.parent.content.unlocked()) ?
                            (content.emoji() + " " + content.localizedName) : "???");
        }
    }

    public static class 生产 implements Objectives.Objective {
        public UnlockableContent content;

        public 生产(UnlockableContent content){
            this.content = content;
        }

        protected 生产(){}

        @Override
        public boolean complete(){
            return content.unlocked();
        }

        @Override
        public String display(){
            return Core.bundle.format("requirement.produce",
                    content.unlocked() ? (content.emoji() + " " + content.localizedName) : "???");
        }
    }

    public static class 占领地区 implements Objectives.Objective {
        public SectorPreset preset;

        public 占领地区(SectorPreset zone){
            this.preset = zone;
        }

        protected 占领地区(){}

        @Override
        public boolean complete(){
            return preset.sector.save != null && preset.sector.isCaptured() && preset.sector.hasBase();
        }

        @Override
        public String display(){
            return Core.bundle.format("requirement.capture", preset.localizedName);
        }
    }

    public static class 到达地区 implements Objectives.Objective {
        public SectorPreset preset;

        public 到达地区(SectorPreset zone){
            this.preset = zone;
        }

        protected 到达地区(){}

        @Override
        public boolean complete(){
            return preset.sector.hasBase();
        }

        @Override
        public String display(){
            return Core.bundle.format("requirement.onsector", preset.localizedName);
        }
    }

    public static class 到达星球 implements Objectives.Objective {
        public Planet planet;

        public 到达星球(Planet planet){
            this.planet = planet;
        }

        protected 到达星球(){}

        @Override
        public boolean complete(){
            return planet.sectors.contains(Sector::hasBase);
        }

        @Override
        public String display(){
            return Core.bundle.format("requirement.onplanet", planet.localizedName);
        }
    }
    public static class 无法解锁 implements Objectives.Objective {
        @Override
        public boolean complete(){
            return false;
        }
        protected 无法解锁(){}
        @Override
        public String display(){
            return Core.bundle.get("requirement.无法解锁1");
        }
    }
    public static class 特殊解锁 implements Objectives.Objective {
        String G ;
        public 特殊解锁(String G) {
            G=G;
        }
        protected 特殊解锁(){}
        @Override
        public boolean complete(){
            return false;
        }

        @Override
        public String display(){
            return Core.bundle.get("requirement.无法解锁2");
        }
    }
}
