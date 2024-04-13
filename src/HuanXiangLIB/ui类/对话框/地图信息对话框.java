package HuanXiangLIB.ui类.对话框;

import arc.scene.ui.TextArea;
import arc.scene.ui.TextButton;
import arc.scene.ui.TextField;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.editor.MapObjectivesDialog;
import mindustry.editor.WaveInfoDialog;
import mindustry.game.Rules;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.io.JsonIO;
import mindustry.maps.filters.GenerateFilter;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.CustomRulesDialog;

import java.util.Objects;

public class 地图信息对话框 extends BaseDialog {
    private final WaveInfoDialog waveInfo = new WaveInfoDialog();
    private final 地图生成对话框 generate = new 地图生成对话框(false);
    private final CustomRulesDialog ruleInfo = new CustomRulesDialog();
    private final MapObjectivesDialog objectives = new MapObjectivesDialog();

    public 地图信息对话框() {
        super("@editor.mapinfo");
        this.addCloseButton();
        this.shown(this::setup);
    }

    private void setup() {
        this.cont.clear();
        ObjectMap<String, String> tags = Vars.editor.tags;
        this.cont.pane((t) -> {
            t.add("@editor.mapname").padRight(8.0F).left();
            t.defaults().padTop(15.0F);
            TextField name = (TextField)t.field((String)tags.get("name", ""), (text) -> {
                tags.put("name", text);
            }).size(400.0F, 55.0F).maxTextLength(50).get();
            name.setMessageText("@unknown");
            t.row();
            t.add("@editor.description").padRight(8.0F).left();
            TextArea description = (TextArea)t.area((String)tags.get("description", ""), Styles.areaField, (text) -> {
                tags.put("description", text);
            }).size(400.0F, 140.0F).maxTextLength(1000).get();
            t.row();
            t.add("@editor.author").padRight(8.0F).left();
            TextField author = (TextField)t.field((String)tags.get("author", ""), (text) -> {
                tags.put("author", text);
            }).size(400.0F, 55.0F).maxTextLength(50).get();
            author.setMessageText("@unknown");
            t.row();
            t.table(Tex.button, (r) -> {
                r.defaults().width(230.0F).height(60.0F);
                TextButton.TextButtonStyle style = Styles.flatt;
                r.button("@editor.rules", Icon.list, style, () -> {
                    this.ruleInfo.show(Vars.state.rules, () -> {
                        return Vars.state.rules = new Rules();
                    });
                    this.hide();
                }).marginLeft(10.0F);
                r.button("@editor.waves", Icon.units, style, () -> {
                    this.waveInfo.show();
                    this.hide();
                }).marginLeft(10.0F);
                r.row();
                r.button("@editor.objectives", Icon.info, style, () -> {
                    MapObjectivesDialog var10000 = this.objectives;
                    Seq var10001 = Vars.state.rules.objectives.all;
                    Seq var10002 = Vars.state.rules.objectives.all;
                    Objects.requireNonNull(var10002);
                    var10000.show(var10001, var10002::set);
                    this.hide();
                }).marginLeft(10.0F);
                r.button("@editor.generation", Icon.terrain, style, () -> {
                    Seq<GenerateFilter> res = Vars.maps.readFilters((String)Vars.editor.tags.get("genfilters", ""));
                    res.each(GenerateFilter::randomize);
                    this.generate.show(res, (filters) -> {
                        filters.each((f) -> {
                            f.seed = 0;
                        });
                        Vars.editor.tags.put("genfilters", JsonIO.write(filters));
                    });
                    this.hide();
                }).marginLeft(10.0F);
            }).colspan(2).center();
            name.change();
            description.change();
            author.change();
            t.margin(16.0F);
        });
    }
}
