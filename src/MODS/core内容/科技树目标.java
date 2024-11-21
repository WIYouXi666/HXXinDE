package MODS.core内容;

import arc.Core;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;

public class 科技树目标 {





    public static class 未研究 implements Objectives.Objective {
        public UnlockableContent content;

        public 未研究(UnlockableContent content){
            this.content = content;
        }

        protected 未研究(){}

        @Override
        public boolean complete(){
            return !content.unlocked();
        }

        @Override
        public String display(){
            return Core.bundle.format("requirement.不要研究1",
                    (content.techNode == null || content.techNode.parent == null || content.techNode.parent.content.unlocked()) ?
                            (content.emoji() + " " + content.localizedName) : "???");
        }
    }

    public static class 无法解锁 implements Objectives.Objective {
        @Override
        public boolean complete(){
            return false;
        }
        public 无法解锁(){}
        @Override
        public String display(){
            return Core.bundle.get("requirement.无法解锁1");
        }
    }

}
