package 核心.内容;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.input.KeyCode;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;
import 核心.ui类.ui内容主体;

import java.util.concurrent.atomic.AtomicInteger;

import static mindustry.Vars.*;
import static rhino.ScriptRuntime.*;
import static 实际内容.核心内容.游戏内数据方法.获取;
import static 实际内容.核心内容.游戏内数据方法.设置;
import static 核心.ui类.局内ui主体.长;
import static 核心.ui类.局内ui主体.项目列数;
import static 核心.工具方法.TRD;
import static 核心.工具方法.validPosInt;
import static 核心.快捷方法.*;

public class 局内ui内容 {


    public static void load() {
        new ui内容主体("JiaoHuDanWei") {
            final Element el = new Element();
            public TextField xField, yField;
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
                        ooo.field("" + team.id, text -> {
                            int id = (int) toInteger(text);
                            team = Team.get(id);
                        }).size(长 / 2 - 45, 45).valid(val -> validPosInt(val) && toInteger(val) < Team.all.length).get();
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
                                    unit.spawn(team, x, y);
                                }
                            }).size(45);
                            ooo.getAndIncrement();
                            if (ooo.get() % 项目列数 == 0) e.row();
                        }
                    });
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


            {
                设置禁用 =Vars.state.rules.infiniteResources||Vars.state.rules.editor;
            }};

        new ui内容主体("WuPin") {
            @Override
            public void 加载(Table ie) {
                ie.pane(e -> {
                    AtomicInteger i = new AtomicInteger();
                    Vars.content.items().each(item -> {
                        e.button(TRD(item.uiIcon), Styles.flati, 40, () -> {
                            int fire = Vars.state.rules.coreIncinerates ? 20 : 1;
                            Vars.player.core().items.set(item, Vars.player.core().getMaximumAccepted(item) / fire);
                        }).size(45);
                        i.getAndIncrement();
                        if (i.get() % 项目列数 == 0) e.row();
                    });
                });
            }
            { 设置禁用 = Vars.state.rules.infiniteResources || Vars.state.rules.editor;
            }};

        new ui内容主体("特定属性") {
            @Override
            public void 加载(Table table) {
                table.add("时间 : ").size(45);
                // table.add("").update(l -> {l.setText(String.valueOf(时间));}).size(jjjj/2-45,45);
                table.row();
                table.add("sdfsf" + ui.planet.getChildren().toString());

            }
        };
        new ui内容主体("gfdsg") {

            @Override
            public void 加载(Table ggg) {
                ggg.pane(Cggg -> {
                    Cggg.button(Icon.chat, () -> {
                        gn添加聊天信息(String.valueOf(get计时器()));
                    }).row();

                    Cggg.add(""+get世界宽度()+" h h "+get世界高度()).update(
                            i->{
                                i.setText(""+get世界宽度()+" h h "+get世界高度()+"  \n"+get相机位置());
                            }
                    ).row();
                    Cggg.button(Icon.chat, () -> {
                        gn控制相机位置(100,100,0.2f,10);
                    });
                    Cggg.button(Icon.chat, () -> {
                        gn关闭控制相机位置();
                    }).row();
                    Cggg.button(Icon.chat, () -> {
                        if (ui.chatfrag.shown()) {
                            ui.chatfrag.hide();
                        } else {
                            ui.chatfrag.toggle();
                        }
                    });
                    Cggg.button(Icon.tree, () -> {
                        ui.research.show();
                    });
                    Cggg.button(Icon.book, () -> {
                        ui.database.show();
                    });
                    Cggg.row();
                    Cggg.buttonRow("@hostserver.mobile", Icon.host, ui.host::show).disabled(b -> net.active());

                });
            }
        };
        new ui内容主体("adsads") {

            @Override
            public void 加载(Table ggg) {
                ggg.pane(Cggg -> {
                    Cggg.button("设置", () -> {
                        设置("66", "66");
                    });
                    Cggg.button("获取", () -> {
                        ui.chatfrag.addMessage(获取("66"));
                    });
                });
            }
        };



//        new JiaoHu("ghjgj") {
//// 135 新视界的 fsfdfsdgfdh
//            @Override
//            public void JiaZaiSheZhi(Table ggg) {
//                ggg.row();
//                dfgfdgf(ggg,"fhgfh",300,Pal.accent);
//                dfgfdgf(ggg,"fhgfh",600,Pal.accent);
//                dfgfdgf(ggg,"fhgfh",900,Pal.accent);
//                dfgfdgf(ggg,"fhgfh",1200,Pal.accent);
//                dfgfdgf(ggg,"fhgfh",1500,Pal.accent);
//                dfgfdgf(ggg,"fhgfh",1800,Pal.accent);
//            }
//        };

    }


}
