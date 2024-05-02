package HuanXiangDIY.内容.局内ui内容;

import HuanXiangLIB.Unit$武器$能力.创建单位改;
import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.input.KeyCode;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.ui.TextArea;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Tex;
import mindustry.gen.Unitc;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

import java.util.concurrent.atomic.AtomicInteger;


import static HuanXiangLIB.ui类.局内ui主体.*;
import static HuanXiangLIB.工具方法.TRD;
import static HuanXiangLIB.工具方法.validPosInt;
import static mindustry.Vars.player;
import static rhino.ScriptRuntime.*;

public class 单位 extends uilib {


    public 单位() {
        super("单位");
        设置禁用 = Vars.state.rules.infiniteResources || Vars.state.rules.editor;
    }


    final Element el = new Element();
    public TextField xField, yField;
    public String 能力 ;
    final InputListener listener = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
            Core.scene.removeListener(this);
            Vec2 vec2 = Core.camera.unproject(x, y);
            setX(vec2.x);
            setY(vec2.y);
            el.remove();
            return false;
        }
    };
    public Team team = Team.derelict;
    public Table unitCont;
    float x, y;
    int amount = 0;

    {
        el.fillParent = true;
    }

    public void setX(float x) {
        xField.setText(String.valueOf(x));
        //		swapnX = x;
    }

    public void setY(float y) {
        yField.setText(String.valueOf(y));
        //		swapnY = y;
    }

    public boolean validNumber(String str) {
        try {
            double d = toNumber(str);
            return Math.abs(d) < 1E6 && !isNaN(d);
        } catch (Exception ignored) {
        }
        return false;
    }

    public boolean isOk() {
        return xField.isValid() && yField.isValid();
    }

    @Override
    public void 加载(Table ue) {

        ue.pane(e -> {
            ue.table(Tex.windowEmpty, ooo -> {
                ooo.add("数量: ");
                ooo.field("0", text -> {
                    amount = (int) toInteger(text);
                }).size(长 / 2 - 45, 45).valid(val -> validNumber(val) && validPosInt(val)).get();
                ;
                ooo.add("队伍: ");
                ooo.add(new TextArea("1")).update(
                        i->{
                            int id = (int) toInteger(i.getText());
                            team = Team.get(id);
                        }
                ).size(长 / 2 - 45, 45).valid(val -> validPosInt(val) && toInteger(val) < Team.all.length).get();

//                ooo.field("" + team.id, text -> {
//                    int id = (int) toInteger(text);
//                    team = Team.get(id);
//                }).size(长 / 2 - 45, 45).valid(val -> validPosInt(val) && toInteger(val) < Team.all.length).get();
                ooo.row();
                //}).row();
                //  ue.table(Tex.windowEmpty, ppp -> {
                ooo.add("x: ");
                xField = ooo.field("" + player.x / 8, i -> {
                }).size(长 / 2 - 45, 45).valid(this::validNumber).get();
                ;
                ooo.add("y: ");
                yField = ooo.field("" + player.y / 8, i -> {
                }).size(长 / 2 - 45, 45).valid(this::validNumber).get();
                ;
                ooo.row();

                ooo.add("能力: ");
                ooo.add(new TextArea("测试1")).update(
                        i->{
                            能力 = i.getText();
                        }
                ).size(长 / 2 - 45, 45);

                ooo.row();
                ooo.button(TRD(UnitTypes.gamma.uiIcon), Styles.flati, 40, () -> {
                    setX(player.x);
                    setY(player.y);
                }).size(45);
                ooo.button(TRD(UnitTypes.gamma.uiIcon), Styles.flati, 40, () -> {
                    if (el.parent == null) {
                        Core.scene.addListener(listener);
                        Core.scene.add(el);
                    } else {
                        el.remove();
                    }
                }).size(45).update(b -> {
                    b.setChecked(el.parent != null);
                }).row();
            }).row();

            AtomicInteger ooo = new AtomicInteger();
            Vars.content.units().each(unit -> {
                if (!unit.internal) {
                    e.button(TRD(unit.uiIcon), Styles.flati, 40, () -> {

                        for (float i = 0; i < amount; i++) {
                            float x = (float) toNumber(xField.getText());
                            float y = (float) toNumber(yField.getText());
                            创建单位改.创建单位(x, y, unit, Team.get(1),能力);

                        }
                    }).size(45);
                    ooo.getAndIncrement();
                    if (ooo.get() % 项目列数 == 0) e.row();
                }
            });
            e.row();
            e.button("清除", () -> {
                Groups.unit.each(Unitc::kill);
            }).size(45);



        });

        Events.run(EventType.Trigger.draw, () -> {
            if (!isOk()) return;

            float x = (float) toNumber(xField.getText());
            float y = (float) toNumber(yField.getText());

            Draw.z(Layer.overlayUI);
            Draw.color(Pal.accent);
            Lines.stroke(2);
            Lines.circle(x, y, 5);
            Draw.color();
        });

    }

};
