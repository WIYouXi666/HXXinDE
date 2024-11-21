package MODS;


import arc.scene.ui.layout.Table;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Tex;
import mindustry.ui.Styles;


import static MODS.HX_Vars.保存s;
import static mindustry.Vars.control;

public class 测试 {
    public static final Table table = new Table();
    public static final String DSAFSF = "newDFGDGDGJNFNDGFDGDGDFG();";

    public static void rebuild() {
        table.clear();
        table.visibility = () -> Vars.ui.hudfrag.shown && !Vars.ui.minimapfrag.shown();
        table.defaults().left();
        table.background(Styles.black6);
        table.row();
        table.table(buttons -> {
            buttons.background(Tex.buttonTrans);


            buttons.button("添加测试", Styles.togglet, () -> {
                Vars.state.rules.tags.put(DSAFSF,  Vars.state.map.name());
            }).size(120, 30).row();
            buttons.button("读取测试", Styles.togglet, () -> {
                Log.info(Vars.state.rules.tags.get(DSAFSF));
            }).size(120, 30).row();
            buttons.button("添加hhhh测试", Styles.togglet, () -> {



                Log.info(保存s.保存的信息.toString());
            }).size(120, 30).row();
            buttons.button("hhhh测试读取", Styles.togglet, () -> {
                Log.info(保存s.保存的信息.toString());
            }).size(120, 30).row();
            buttons.button("hhhh测试读取", Styles.togglet, () -> {
                Log.info(保存s.保存的信息.get("666"));
            }).size(120, 30).row();
            buttons.button("输入", Styles.togglet,() -> {
                Log.info(control.input.config.isShown());
            }).size(120, 30).row();
        });
        Vars.ui.hudGroup.fill(t -> {
            t.right().name = "fuzhu";
            t.add(table);
        });
    }


}



