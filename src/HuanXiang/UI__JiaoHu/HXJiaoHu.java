package HuanXiang.UI__JiaoHu;

import HuanXiang.content.HXPlanets;
import HuanXiang.核心内容.事件.自建事件;
import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.input.KeyCode;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.ui.ImageButton;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.ItemDisplay;
import mindustry.ui.LiquidDisplay;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

import java.util.concurrent.atomic.AtomicInteger;

import static HuanXiang.KeNengYongDao.*;
import static HuanXiang.UI__JiaoHu.JiaoHuJuNei.Chang;
import static HuanXiang.UI__JiaoHu.JiaoHuJuNei.项目列数;
import static HuanXiang.UI__JiaoHu.JiaoHuKaiPing.DawnDialog;
import static HuanXiang.主要.颜色.金色传说;
import static HuanXiang.核心内容.游戏内数据方法.获取;
import static HuanXiang.核心内容.游戏内数据方法.设置;
import static mindustry.Vars.*;
import static rhino.ScriptRuntime.*;

public class HXJiaoHu {


    public static void load() {
        new JiaoHu("JiaoHuDanWei") {
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
            public void JiaZaiSheZhi(Table ue) {

                ue.pane(e -> {
                    ue.table(Tex.windowEmpty, ooo -> {
                        ooo.add("数量: ");
                        ooo.field("0", text -> {
                            amount = (int) toInteger(text);
                        }).size(Chang / 2 - 45, 45).valid(val -> validNumber(val) && validPosInt(val)).get();
                        ;
                        ooo.add("队伍: ");
                        ooo.field("" + team.id, text -> {
                            int id = (int) toInteger(text);
                            team = Team.get(id);
                        }).size(Chang / 2 - 45, 45).valid(val -> validPosInt(val) && toInteger(val) < Team.all.length).get();
                        ooo.row();
                        //}).row();
                        //  ue.table(Tex.windowEmpty, ppp -> {
                        ooo.add("x: ");
                        xField = ooo.field("" + player.x / 8, i -> {
                        }).size(Chang / 2 - 45, 45).valid(this::validNumber).get();
                        ;
                        ooo.add("y: ");
                        yField = ooo.field("" + player.y / 8, i -> {
                        }).size(Chang / 2 - 45, 45).valid(this::validNumber).get();
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


        };

        new JiaoHu("WuPin") {
            @Override
            public void JiaZaiSheZhi(Table ie) {
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
        };

        new JiaoHu("特定属性") {
            @Override
            public void JiaZaiSheZhi(Table table) {
                table.add("时间 : ").size(45);
                // table.add("").update(l -> {l.setText(String.valueOf(时间));}).size(jjjj/2-45,45);
                table.row();
                table.add("sdfsf" + ui.planet.getChildren().toString());

            }
        };
        new JiaoHu("dgfd") {
            @Override
            public void JiaZaiSheZhi(Table ggg) {
                ggg.pane(Cggg -> {
                    Cggg.button("sdfsd", JiaoHuKaiPing::show);
                });
            }

        };
        new JiaoHu("dgfsafdsad") {
            @Override
            public void JiaZaiSheZhi(Table ggg) {
                ggg.pane(Cggg -> {
                    Cggg.button("召唤单位", () -> {
                        Events.fire(new 自建事件.功能事件(HXPlanets.dd));
                    });
                    Cggg.button("掠夺", () -> {
                        Events.fire(new 自建事件.功能事件(HXPlanets.adsf));
                    });
                    Cggg.button("补给", () -> {
                        Events.fire(new 自建事件.功能事件(HXPlanets.ddCSC));
                    });
                    Cggg.button("力墙", () -> {
                        Events.fire(new 自建事件.功能事件(HXPlanets.asfdsaf));//new 力墙()/这样不可以
                    });
                    Cggg.button("gtjufgy", () -> {
                        ui.chatfrag.addMessage("x" + 世界宽度);
                        ui.chatfrag.addMessage("y" + 世界高度);
                    });
                });
            }
        };
        new JiaoHu("gfdsg") {

            @Override
            public void JiaZaiSheZhi(Table ggg) {
                ggg.pane(Cggg -> {
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
        new JiaoHu("adsads") {

            @Override
            public void JiaZaiSheZhi(Table ggg) {
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
        new JiaoHu("fghjf") {

            @Override
            public void JiaZaiSheZhi(Table ggg) {

                ggg.pane(Cggg -> {
                    Cggg.button("设置ghjgj", this::jklj);
                });
            }
            public void jklj() {









                BaseDialog dialog = new BaseDialog("---");
                ImageButton.ImageButtonStyle style = Styles.cleari;
                if (Core.scene.hasDialog()) {
                    dialog.setStyle(Styles.fullDialog);
                } else {
                    dialog.titleTable.remove();
                    dialog.setStyle(DawnDialog);
                }
                dialog.cont.pane(part -> {
                    part.button("加载翻译", JiaoHuKaiPing::加载).size(210.0F, 64.0F);
                    part.row();

                    part.add("[accent]" + Stat.input.localized()).expandX().left().row();
                        part.table(row -> {
                            row.add(new ItemDisplay(Items.copper, 10, true)).padRight(5);
                        }).left().row();
                        part.table(row -> {
                            row.add(new LiquidDisplay(Liquids.oil, 60, false));
                        }).left().row();
                        part.table(row -> {
                            row.add("[lightgray]" + Stat.powerUse.localized() + ":[]").padRight(4);
                            (StatValues.number(600, StatUnit.powerSecond)).display(row);
                        }).left().row();

                        part.add("[accent]" + Stat.output.localized()).left().row();
                        part.table(row -> {
                            row.add(new ItemDisplay(Items.copper, 10, true)).padRight(5);
                        }).left().row();
                        part.table(row -> {
                            row.add(new LiquidDisplay(Liquids.oil, 60, false));
                        }).left().row();
                        part.table(row -> {
                            row.add("[lightgray]" + Stat.basePowerGeneration.localized() + ":[]").padRight(4);
                            (StatValues.number(600, StatUnit.powerSecond)).display(row);
                        }).left().row();

                        part.table(row -> {
                            row.add("[lightgray]" + Stat.productionTime.localized() + ":[]").padRight(4);
                            (StatValues.number(80, StatUnit.seconds)).display(row);
                        }).left().row();
                        //if (typeof this["customDisplay"] == = "function")this.customDisplay(part, i);
                }).color(金色传说).left().growX();;
                dialog.buttons.button("@close", dialog::hide).size(210.0F, 64.0F);
                dialog.show();
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
