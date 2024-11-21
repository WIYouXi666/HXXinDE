package MODS;

import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.world.Block;

import java.util.Objects;

import static MODS.HX_Vars.*;

public class HX_外_Building {
    public String 信息;
    public Block block;

    public boolean 配置 = false;
    public HX_外_Building_限制 限制;

    public String 主建筑标签;
    public boolean 标签限制 = false;
    public String 数据库内容;



    public HX_外_Building(Block block) {
        this.block = block;
        this.信息 = "";
        this.限制 = new HX_外_Building_限制();
        this.主建筑标签 = S_a;
        this.数据库内容 = block.getContentType().name();

        WAI_Building.put(block, this);
        数据库put(数据库内容, block);
    }



    public void 构建配置(Building build, Table table) {

    }
    public void 绘制配置(Building build) {
        build.drawConfigure();
    }





    public static class HX_外_Building_限制 {
        public Seq<Block> 前置建筑 =new Seq<>();;
        public int 建筑低数 = 1, 建筑类数 = 1;
        public Seq<String> 前置标签=new Seq<>() ;
        public int 标签低数 = 1, 标签类数 = 1;
        public float 限制数量 = -1;
        public Seq<String> 建筑标签= new Seq<>();;

        public void set标记映射(String... B) {

            Seq<String> AAAA = new Seq<>();

            for (String content : B) {
                if (Objects.equals(content, S_a)) continue;
                if (B.length == 1) break;
                AAAA.addUnique(content);
            }
            建筑标签 = AAAA;
        }


        public Seq<String> get标记映射() {

            return 建筑标签;
        }

    }


}
