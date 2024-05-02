package HuanXiangLIB.ui类.对话框;

import arc.Core;
import arc.func.Cons;
import arc.func.Prov;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.ui.Dialog;
import arc.scene.ui.Image;
import arc.scene.ui.ImageButton;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Scaling;
import mindustry.Vars;
import mindustry.editor.MapEditor;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.PackTile;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.io.JsonIO;
import mindustry.io.MapIO;
import mindustry.maps.Maps;
import mindustry.maps.filters.FilterOption;
import mindustry.maps.filters.GenerateFilter;
import mindustry.ui.BorderImage;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.CachedTile;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Future;

public class 地图生成对话框 extends BaseDialog {
    final boolean applied;
    Pixmap pixmap;
    Texture texture;
    GenerateFilter.GenerateInput input = new GenerateFilter.GenerateInput();
    Seq<GenerateFilter> filters = new Seq();
    int scaling;
    Table filterTable;
    Future<?> result;
    boolean generating;
    long[] buffer1;
    long[] buffer2;
    Cons<Seq<GenerateFilter>> applier;
    CachedTile ctile;

    public 地图生成对话框(boolean applied) {
        super("@editor.generate");
        this.scaling = Vars.mobile ? 3 : 1;
        this.ctile = new CachedTile() {
            protected void changeBuild(Team team, Prov<Building> entityprov, int rotation) {
            }

            public void setBlock(Block type, Team team, int rotation, Prov<Building> entityprov) {
                this.block = type;
            }
        };
        this.applied = applied;
        this.shown(this::setup);
        this.addCloseListener();
        TextButton.TextButtonStyle style = Styles.flatt;
        this.buttons.defaults().size(180.0F, 64.0F).pad(2.0F);
        this.buttons.button("@back", Icon.left, this::hide);
        if (applied) {
            this.buttons.button("@editor.apply", Icon.ok, () -> {
                Vars.ui.loadAnd(() -> {
                    this.apply();
                    this.hide();
                });
            });
        }

        this.buttons.button("@editor.randomize", Icon.refresh, () -> {
            Iterator var1 = this.filters.iterator();

            while(var1.hasNext()) {
                GenerateFilter filter = (GenerateFilter)var1.next();
                filter.randomize();
            }

            this.update();
        });
        this.buttons.button("@edit", Icon.edit, () -> {
            BaseDialog dialog = new BaseDialog("@editor.export");
            dialog.cont.pane((p) -> {
                p.margin(10.0F);
                p.table(Tex.button, (in) -> {
                    in.defaults().size(280.0F, 60.0F).left();
                    in.button("@waves.copy", Icon.copy, style, () -> {
                        dialog.hide();
                        Core.app.setClipboardText(JsonIO.write(this.filters));
                    }).marginLeft(12.0F).row();
                    in.button("@waves.load", Icon.download, style, () -> {
                        dialog.hide();

                        try {
                            this.filters.set((Seq)JsonIO.read(Seq.class, Core.app.getClipboardText()));
                            this.rebuildFilters();
                            this.update();
                        } catch (Throwable var3) {
                            Vars.ui.showException(var3);
                        }

                    }).marginLeft(12.0F).disabled((b) -> {
                        return Core.app.getClipboardText() == null;
                    }).row();
                    in.button("@clear", Icon.none, style, () -> {
                        dialog.hide();
                        this.filters.clear();
                        this.rebuildFilters();
                        this.update();
                    }).marginLeft(12.0F).row();
                    if (!applied) {
                        in.button("@settings.reset", Icon.refresh, style, () -> {
                            dialog.hide();
                            this.filters.set(Vars.maps.readFilters(""));
                            this.rebuildFilters();
                            this.update();
                        }).marginLeft(12.0F).row();
                    }

                });
            });
            dialog.addCloseButton();
            dialog.show();
        });
        this.buttons.button("@add", Icon.add, this::showAdd);
        if (!applied) {
            this.hidden(this::apply);
        }

        this.onResize(this::rebuildFilters);
    }

    public void show(Seq<GenerateFilter> filters, Cons<Seq<GenerateFilter>> applier) {
        this.filters = filters;
        this.applier = applier;
        this.show();
    }

    public void show(Cons<Seq<GenerateFilter>> applier) {
        this.show(this.filters, applier);
    }

    public void applyToEditor(Seq<GenerateFilter> filters) {
        long[] writeTiles = new long[Vars.editor.width() * Vars.editor.height()];
        Iterator var3 = filters.iterator();

        while(var3.hasNext()) {
            GenerateFilter filter = (GenerateFilter)var3.next();
            GenerateFilter.GenerateInput var10000 = this.input;
            int var10001 = Vars.editor.width();
            int var10002 = Vars.editor.height();
            MapEditor var10003 = Vars.editor;
            Objects.requireNonNull(var10003);
            var10000.begin(var10001, var10002, var10003::tile);

            for(int x = 0; x < Vars.editor.width(); ++x) {
                for(int y = 0; y < Vars.editor.height(); ++y) {
                    Tile tile = Vars.editor.tile(x, y);
                    this.input.set(x, y, tile.block(), tile.floor(), tile.overlay());
                    filter.apply(this.input);
                    writeTiles[x + y * Vars.world.width()] = PackTile.get(this.input.block.id, this.input.floor.id, this.input.overlay.id);
                }
            }

            Vars.editor.load(() -> {
                for(int i = 0; i < Vars.editor.width() * Vars.editor.height(); ++i) {
                    Tile tile = Vars.world.tiles.geti(i);
                    long write = writeTiles[i];
                    Block block = Vars.content.block(PackTile.block(write));
                    Block floor = Vars.content.block(PackTile.floor(write));
                    Block overlay = Vars.content.block(PackTile.overlay(write));
                    if (!tile.synthetic() && !block.synthetic()) {
                        tile.setBlock(block);
                    }

                    tile.setFloor((Floor)floor);
                    tile.setOverlay(overlay);
                }

            });
        }

        Vars.editor.renderer.updateAll();
        Vars.editor.clearOp();
    }

    void setup() {
        if (this.pixmap != null) {
            this.pixmap.dispose();
            this.texture.dispose();
            this.pixmap = null;
            this.texture = null;
        }

        this.pixmap = new Pixmap(Vars.editor.width() / this.scaling, Vars.editor.height() / this.scaling);
        this.texture = new Texture(this.pixmap);
        this.cont.clear();
        this.cont.table((t) -> {
            t.margin(8.0F);
            t.stack(new Element[]{new BorderImage(this.texture) {
                {
                    this.setScaling(Scaling.fit);
                }

                public void draw() {
                    super.draw();
                    Iterator var1 = 地图生成对话框.this.filters.iterator();

                    while(var1.hasNext()) {
                        GenerateFilter filter = (GenerateFilter)var1.next();
                        filter.draw(this);
                    }

                }
            }, new Stack() {
                {
                    this.add(new Image(Styles.black8));
                    this.add(new Image(Icon.refresh, Scaling.none));
                    this.visible(() -> {
                        return 地图生成对话框.this.generating && !Vars.updateEditorOnChange;
                    });
                }
            }}).uniformX().grow().padRight(10.0F);
            t.pane((p) -> {
                this.filterTable = p.marginRight(6.0F);
            }).update((pane) -> {
                if (!(Core.scene.getKeyboardFocus() instanceof Dialog) || Core.scene.getKeyboardFocus() == this) {
                    Vec2 v = pane.stageToLocalCoordinates(Core.input.mouse());
                    if (v.x >= 0.0F && v.y >= 0.0F && v.x <= pane.getWidth() && v.y <= pane.getHeight()) {
                        Core.scene.setScrollFocus(pane);
                    } else {
                        Core.scene.setScrollFocus((Element)null);
                    }

                }
            }).grow().uniformX().scrollX(false);
        }).grow();
        this.buffer1 = this.create();
        this.buffer2 = this.create();
        this.update();
        this.rebuildFilters();
    }

    long[] create() {
        return new long[Vars.editor.width() / this.scaling * (Vars.editor.height() / this.scaling)];
    }

    void rebuildFilters() {
        int cols = Math.max((int)((float)Core.graphics.getWidth() / 2.0F / Scl.scl(290.0F)), 1);
        this.filterTable.clearChildren();
        this.filterTable.top().left();
        int i = 0;
        Iterator var3 = this.filters.iterator();

        while(var3.hasNext()) {
            GenerateFilter filter = (GenerateFilter)var3.next();
            this.filterTable.table(Tex.pane, (c) -> {
                c.margin(0.0F);
                c.table(Tex.whiteui, (t) -> {
                    t.setColor(Pal.gray);
                    t.top().left();
                    t.add(filter.name()).left().padLeft(6.0F).width(100.0F).wrap();
                    t.add().growX();
                    ImageButton.ImageButtonStyle style = Styles.geni;
                    t.defaults().size(42.0F).padLeft(-5.0F);
                    t.button(Icon.refresh, style, () -> {
                        filter.randomize();
                        this.update();
                    }).padLeft(-16.0F).tooltip("@editor.randomize");
                    if (filter != this.filters.first()) {
                        t.button(Icon.upOpen, style, () -> {
                            int idx = this.filters.indexOf(filter);
                            this.filters.swap(idx, Math.max(0, idx - 1));
                            this.rebuildFilters();
                            this.update();
                        }).tooltip("@editor.moveup");
                    }

                    if (filter != this.filters.peek()) {
                        t.button(Icon.downOpen, style, () -> {
                            int idx = this.filters.indexOf(filter);
                            this.filters.swap(idx, Math.min(this.filters.size - 1, idx + 1));
                            this.rebuildFilters();
                            this.update();
                        }).tooltip("@editor.movedown");
                    }

                    t.button(Icon.copy, style, () -> {
                        GenerateFilter copy = filter.copy();
                        copy.randomize();
                        this.filters.insert(this.filters.indexOf(filter) + 1, copy);
                        this.rebuildFilters();
                        this.update();
                    }).tooltip("@editor.copy");
                    t.button(Icon.cancel, style, () -> {
                        this.filters.remove(filter);
                        this.rebuildFilters();
                        this.update();
                    }).tooltip("@waves.remove");
                }).growX();
                c.row();
                c.table((f) -> {
                    f.left().top();
                    FilterOption[] aaa = filter.options();
                    int var4 = aaa.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        FilterOption option = aaa[var5];
                        option.changed = this::update;
                        f.table((t) -> {
                            t.left();
                            option.build(t);
                        }).growX().left();
                        f.row();
                    }

                }).grow().left().pad(6.0F).top();
            }).width(280.0F).pad(3.0F).top().left().fillY();
            ++i;
            if (i % cols == 0) {
                this.filterTable.row();
            }
        }

        if (this.filters.isEmpty()) {
            this.filterTable.add("@filters.empty").wrap().width(200.0F);
        }

    }

    void showAdd() {
        BaseDialog selection = new BaseDialog("@add");
        selection.cont.pane((p) -> {
            p.background(Tex.button);
            p.marginRight(14.0F);
            p.defaults().size(195.0F, 56.0F);
            int i = 0;
            Prov[] var4 = Maps.allFilterTypes;
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Prov<GenerateFilter> gen = var4[var6];
                GenerateFilter filter = (GenerateFilter)gen.get();
                char icon = filter.icon();
                if (!filter.isPost() || !this.applied) {
                    ((TextButton)p.button((icon == 0 ? "" : icon + " ") + filter.name(), Styles.flatt, () -> {
                        filter.randomize();
                        this.filters.add(filter);
                        this.rebuildFilters();
                        this.update();
                        selection.hide();
                    }).with(Table::left).get()).getLabelCell().growX().left().padLeft(5.0F).labelAlign(8);
                    ++i;
                    if (i % 3 == 0) {
                        p.row();
                    }
                }
            }

            ((TextButton)p.button("\ue86a " + Core.bundle.get("filter.defaultores"), Styles.flatt, () -> {
                Vars.maps.addDefaultOres(this.filters);
                this.rebuildFilters();
                this.update();
                selection.hide();
            }).with(Table::left).get()).getLabelCell().growX().left().padLeft(5.0F).labelAlign(8);
        }).scrollX(false);
        selection.addCloseButton();
        selection.show();
    }

    long pack(Tile tile) {
        return PackTile.get(tile.blockID(), tile.floorID(), tile.overlayID());
    }

    Tile unpack(long tile) {
        this.ctile.setFloor((Floor)Vars.content.block(PackTile.floor(tile)));
        this.ctile.setBlock(Vars.content.block(PackTile.block(tile)));
        this.ctile.setOverlay(Vars.content.block(PackTile.overlay(tile)));
        return this.ctile;
    }

    void apply() {
        if (this.result != null) {
            try {
                this.result.get();
            } catch (Exception var2) {
            }
        }

        this.buffer1 = null;
        this.buffer2 = null;
        this.generating = false;
        if (this.pixmap != null) {
            this.pixmap.dispose();
            this.texture.dispose();
            this.pixmap = null;
            this.texture = null;
        }

        this.applier.get(this.filters);
    }

    void update() {
        if (!this.generating) {
            Seq<GenerateFilter> copy = this.filters.copy();
            this.result = Vars.mainExecutor.submit(() -> {
                try {
                    int w = this.pixmap.width;
                    Vars.world.setGenerating(true);
                    this.generating = true;
                    int px;
                    int py;
                    if (!this.filters.isEmpty()) {
                        for(px = 0; px < this.pixmap.width; ++px) {
                            for(py = 0; py < this.pixmap.height; ++py) {
                                this.buffer1[px + py * w] = this.pack(Vars.editor.tile(px * this.scaling, py * this.scaling));
                            }
                        }
                    }

                    Iterator var9 = copy.iterator();

                    while(var9.hasNext()) {
                        GenerateFilter filter = (GenerateFilter)var9.next();
                        this.input.begin(Vars.editor.width(), Vars.editor.height(), (x, y) -> {
                            return this.unpack(this.buffer1[Mathf.clamp(x / this.scaling, 0, this.pixmap.width - 1) + w * Mathf.clamp(y / this.scaling, 0, this.pixmap.height - 1)]);
                        });
                        this.pixmap.each((pxx, pyx) -> {
                            int x = pxx * this.scaling;
                            int y = pyx * this.scaling;
                            long tile = this.buffer1[pxx + pyx * w];
                            this.input.set(x, y, Vars.content.block(PackTile.block(tile)), Vars.content.block(PackTile.floor(tile)), Vars.content.block(PackTile.overlay(tile)));
                            filter.apply(this.input);
                            this.buffer2[pxx + pyx * w] = PackTile.get(this.input.block.id, this.input.floor.id, this.input.overlay.id);
                        });
                        this.pixmap.each((pxx, pyx) -> {
                            this.buffer1[pxx + pyx * w] = this.buffer2[pxx + pyx * w];
                        });
                    }

                    for(px = 0; px < this.pixmap.width; ++px) {
                        for(py = 0; py < this.pixmap.height; ++py) {
                            int color;
                            if (this.filters.isEmpty()) {
                                Tile tilex = Vars.editor.tile(px * this.scaling, py * this.scaling);
                                color = MapIO.colorFor(tilex.block(), tilex.floor(), tilex.overlay(), Team.derelict);
                            } else {
                                long tile = this.buffer1[px + py * w];
                                color = MapIO.colorFor(Vars.content.block(PackTile.block(tile)), Vars.content.block(PackTile.floor(tile)), Vars.content.block(PackTile.overlay(tile)), Team.derelict);
                            }

                            this.pixmap.set(px, this.pixmap.height - 1 - py, color);
                        }
                    }

                    Core.app.post(() -> {
                        if (this.pixmap != null && this.texture != null) {
                            this.texture.draw(this.pixmap);
                            this.generating = false;
                        }
                    });
                } catch (Exception var8) {
                    this.generating = false;
                    Log.err(var8);
                }

                Vars.world.setGenerating(false);
            });
        }
    }
}
