package HuanXiangLIB.ui类.对话框;

import arc.Core;
import arc.graphics.Color;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.scene.event.ClickListener;
import arc.scene.event.HandCursorListener;
import arc.scene.event.Touchable;
import arc.scene.ui.Image;
import arc.scene.ui.TextField;
import arc.scene.ui.Tooltip;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Scaling;
import arc.util.Time;
import mindustry.Vars;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Fonts;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.DatabaseDialog;
import mindustry.world.Block;

import static arc.Core.settings;
import static mindustry.Vars.*;

public class 核心数据库对话框 extends BaseDialog {


    public static void init() {
        if (Vars.headless) return;
        Vars.ui.database.remove();
        DatabaseDialog parent = Vars.ui.database;
        Vars.ui.database = null;
       // Vars.ui.database = (DatabaseDialog) new 核心数据库(parent);
    }
    private TextField search;
    private Table all = new Table();

    public 核心数据库对话框(){
        //super("梵蒂冈地方官");
        super("@database");
        shouldPause = true;
        addCloseButton();
        shown(this::rebuild);
        onResize(this::rebuild);

        all.margin(20).marginTop(0f);

        cont.table(s -> {
            s.image(Icon.zoom).padRight(8);
            search = s.field(null, text -> rebuild()).growX().get();
            search.setMessageText("@players.search");
        }).fillX().padBottom(4).row();

        cont.pane(all).scrollX(false);
    }

    void rebuild(){
        all.clear();
        var text = search.getText();
        all.add("sfsdfsdfsdfdsfsdfdsf");
        Seq<Content>[] allContent = Vars.content.getContentMap();

        for(int j = 0; j < allContent.length; j++){
            ContentType type = ContentType.all[j];

            Seq<UnlockableContent> array = allContent[j]
                    .select(c -> c instanceof UnlockableContent u && !u.isHidden()  &&
                            (text.isEmpty() || u.localizedName.toLowerCase().contains(text.toLowerCase()))).as();
            if(array.size == 0) continue;

            all.add("@content." + type.name() + ".name").growX().left().color(Pal.accent);
            all.row();
            all.image().growX().pad(5).padLeft(0).padRight(0).height(3).color(Pal.accent);
            all.row();
            all.table(list -> {
                list.left();

                int cols = (int) Mathf.clamp((Core.graphics.getWidth() - Scl.scl(30)) / Scl.scl(32 + 12), 1, 22);
                int count = 0;

                for(int i = 0; i < array.size; i++){
                    UnlockableContent unlock = array.get(i);

                    Image image = unlocked(unlock) ? new Image(unlock.uiIcon).setScaling(Scaling.fit) : new Image(Icon.lock, Pal.gray);

                    //banned cross
                    if(state.isGame() && (unlock instanceof UnitType u && u.isBanned() || unlock instanceof Block b && state.rules.isBanned(b))){
                        list.stack(image, new Image(Icon.cancel){{
                            setColor(Color.scarlet);
                            touchable = Touchable.disabled;
                        }}).size(8 * 4).pad(3);
                    }else{
                        list.add(image).size(8 * 4).pad(3);
                    }

                    ClickListener listener = new ClickListener();
                    image.addListener(listener);
                    if(!mobile && unlocked(unlock)){
                        image.addListener(new HandCursorListener());
                        image.update(() -> image.color.lerp(!listener.isOver() ? Color.lightGray : Color.white, Mathf.clamp(0.4f * Time.delta)));
                    }

                    if(unlocked(unlock)){
                        image.clicked(() -> {
                            if(Core.input.keyDown(KeyCode.shiftLeft) && Fonts.getUnicode(unlock.name) != 0){
                                Core.app.setClipboardText((char)Fonts.getUnicode(unlock.name) + "");
                                ui.showInfoFade("@copied");
                            }else{
                                ui.content.show(unlock);
                            }
                        });
                        image.addListener(new Tooltip(t -> t.background(Tex.button).add(unlock.localizedName + (settings.getBool("console") ? "\n[gray]" + unlock.name : ""))));
                    }

                    if((++count) % cols == 0){
                        list.row();
                    }
                }
            }).growX().left().padBottom(10);
            all.row();
        }

        if(all.getChildren().isEmpty()){
            all.add("@none.found");
        }
    }

    boolean unlocked(UnlockableContent content){
        return (!Vars.state.isCampaign() && !Vars.state.isMenu()) || content.unlocked();
    }
}
