package 核心.ui类.交互窗;


import arc.Core;
import arc.Events;
import arc.files.Fi;
import arc.func.Cons;
import arc.func.Floatc;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.Texture.TextureFilter;
import arc.graphics.g2d.TextureRegion;
import arc.input.KeyCode;
import arc.scene.ui.TextButton.TextButtonStyle;
import arc.scene.ui.Tooltip;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.*;
import arc.util.Http.HttpResponse;
import arc.util.Http.HttpStatusException;
import arc.util.io.Streams;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.core.Version;
import mindustry.game.EventType;
import mindustry.game.EventType.ResizeEvent;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.io.JsonIO;
import mindustry.mod.ModListing;
import mindustry.mod.Mods.LoadedMod;
import mindustry.ui.BorderImage;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static mindustry.Vars.*;

//TODO 破晓自建Mod界面UI
public class MODS extends BaseDialog {
    private ObjectMap<String, TextureRegion> textureCache = new ObjectMap<>();

    private float modImportProgress;
    private String searchtxt = "";
    private @Nullable Seq<ModListing> modList;
    private boolean orderDate = false;
    private BaseDialog currentContent;

    private BaseDialog browser;
    private Table browserTable;
    private float scroll = 0f;
    public MODS(){
        super("@mods");
        addCloseButton();

        browser = new BaseDialog("@mods.browser");

        browser.cont.table(table -> {
            table.left();
            table.image(Icon.zoom);
            table.field(searchtxt, res -> {
                searchtxt = res;
                rebuildBrowser();
            }).growX().get();
            table.button(Icon.list, Styles.clearNonei, 32f, () -> {
                        orderDate = !orderDate;
                        rebuildBrowser();
                    }).update(b -> b.getStyle().imageUp = (orderDate ? Icon.list : Icon.star)).size(40f).get()
                    .addListener(new Tooltip(tip -> tip.label(() -> orderDate ? "@mods.browser.sortdate" : "@mods.browser.sortstars").left()));
        }).fillX().padBottom(4);

        browser.cont.row();
        browser.cont.pane(tablebrow -> {
            tablebrow.margin(10f).top();
            browserTable = tablebrow;
        }).scrollX(false);
        browser.addCloseButton();

        onResize(browser, this::rebuildBrowser);

        buttons.button("@mods.guide", Icon.link, () -> Core.app.openURI(modGuideURL)).size(210, 64f);

        if(!mobile){
            buttons.button("@mods.openfolder", Icon.link, () -> Core.app.openFolder(modDirectory.absolutePath()));
        }

        shown(this::setup);
        onResize(this::setup);

        Events.on(ResizeEvent.class, event -> {
            if(currentContent != null){
                currentContent.hide();
                currentContent = null;
            }
        });

        hidden(() -> {
            if(mods.requiresReload()){
                reload();
            }
        });

    }

    public void onResize(BaseDialog base, Runnable run) {
        Events.on(EventType.ResizeEvent.class, event -> {
            if (base.isShown() && Core.scene.getDialog() == base) {
                run.run();
                base.updateScrollFocus();
            }
        });
    }

    void modError(Throwable error){
        ui.loadfrag.hide();

        if(error instanceof NoSuchMethodError || Strings.getCauses(error).contains(t -> t.getMessage() != null && (t.getMessage().contains("trust anchor") || t.getMessage().contains("SSL") || t.getMessage().contains("protocol")))){
            ui.showErrorMessage("@feature.unsupported");
        }else if(error instanceof HttpStatusException){
            var st = (HttpStatusException) error;
            ui.showErrorMessage(Core.bundle.format("connectfail", Strings.capitalize(st.status.toString().toLowerCase())));
        }else{
            ui.showException(error);
        }
    }

    void getModList(Cons<Seq<ModListing>> listener){
        if(modList == null){
            Http.get("https://raw.githubusercontent.com/Anuken/MindustryMods/master/mods.json", response -> {
                String strResult = response.getResultAsString();

                Core.app.post(() -> {
                    try{
                        modList = JsonIO.json.fromJson(Seq.class, ModListing.class, strResult);

                        var d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Func<String, Date> parser = text -> {
                            try{
                                return d.parse(text);
                            }catch(Exception e){
                                return new Date();
                            }
                        };

                        modList.sortComparing(m -> parser.get(m.lastUpdated)).reverse();
                        listener.get(modList);
                    }catch(Exception e){
                        e.printStackTrace();
                        ui.showException(e);
                    }
                });
            }, error -> Core.app.post(() -> modError(error)));
        }else{
            listener.get(modList);
        }
    }

    void setup(){
        float h = 110f;
        float w = Math.min(Core.graphics.getWidth() / Scl.scl(1.05f), 520f);

        cont.clear();
        cont.defaults().width(Math.min(Core.graphics.getWidth() / Scl.scl(1.05f), 556f)).pad(4);
        cont.add("@mod.reloadrequired").visible(mods::requiresReload).center().get().setAlignment(Align.center);
        cont.row();

        cont.table(buttons -> {
            buttons.left().defaults().growX().height(60f).uniformX();

            TextButtonStyle style = Styles.flatBordert;
            float margin = 12f;

            buttons.button("@mod.import", Icon.add, style, () -> {
                BaseDialog dialog = new BaseDialog("@mod.import");

                TextButtonStyle bstyle = Styles.flatt;

                dialog.cont.table(Tex.button, t -> {
                    t.defaults().size(300f, 70f);
                    t.margin(12f);

                    t.button("@mod.import.file", Icon.file, bstyle, () -> {
                        dialog.hide();

                        platform.showMultiFileChooser(file -> {
                            try{
                                mods.importMod(file);
                                setup();
                            }catch(IOException e){
                                ui.showException(e);
                                Log.err(e);
                            }
                        }, "zip", "jar");
                    }).margin(12f);

                    t.row();

                    t.button("@mod.import.github", Icon.github, bstyle, () -> {
                        dialog.hide();

                        ui.showTextInput("@mod.import.github", "", 64, Core.settings.getString("lastmod", ""), text -> {
                            //clean up the text in case somebody inputs a URL or adds random spaces
                            text = text.trim().replace(" ", "");
                            if(text.startsWith("https://github.com/")) text = text.substring("https://github.com/".length());

                            Core.settings.put("lastmod", text);
                            //there's no good way to know if it's a java mod here, so assume it's not
                            githubImportMod(text, false);
                        });
                    }).margin(12f);
                });
                dialog.addCloseButton();

                dialog.show();

            }).margin(margin);

            buttons.button("@mods.browser", Icon.menu, style, this::showModBrowser).margin(margin);
        }).width(w);

        cont.row();

        if(!mods.list().isEmpty()){
            boolean[] anyDisabled = {false};
            Table[] pane = {null};

            Cons<String> rebuild = query -> {
                pane[0].clear();
                boolean any = false;
                for(LoadedMod item : mods.list()){
                    if(Strings.matches(query, item.meta.displayName())){
                        boolean A = false;
                        boolean libmod = false;

//                        for(var lib :DawnLib.LibName){
//                            if(Objects.equals(item.meta.name, lib)){
//                                libmod = true;
//                            }
//                        }
//
//                        if (item.meta.dependencies.size != 0) {
//                            if (Objects.equals(item.meta.dependencies.get(0), XVXModName)) {
//                                A = true;
//                            }
//                        }
//
//                        if(Objects.equals(item.meta.name, XVXModName)){
//                            A = true;
//                        }

                        any = true;
                        if(!item.enabled() && !anyDisabled[0] && mods.list().size > 0){
                            anyDisabled[0] = true;
                            pane[0].row();
                            pane[0].image().growX().height(4f).pad(6f).color(Pal.gray).row();
                        }

                        boolean finalA = A;
                        boolean finalLibmod = libmod;
                        pane[0].button(t -> {
                            t.top().left();
                            t.margin(12f);

                            t.defaults().left().top();
//                            t.table(title1 -> {
//                                title1.left();
//                                if(finalA) {
//                                    DawnIcon icon = new DawnIcon(item.name);
//                                    title1.add(new DawnBorderImage() {{
//                                        update(() -> setDrawable(icon.Icon()));
//                                        border(Pal.accent);
//                                    }}).size(h - 8f).padTop(-8f).padLeft(-8f).padRight(8f);
//                                }else{
//                                    title1.add(new BorderImage(){{
//                                        if(item.iconTexture != null){
//                                            setDrawable(new TextureRegion(item.iconTexture));
//                                        }else{
//                                            setDrawable(Tex.nomap);
//                                        }
//                                        border(Pal.accent);
//                                    }}).size(h - 8f).padTop(-8f).padLeft(-8f).padRight(8f);
//                                }
//
//                                title1.table(text -> {
//                                    boolean hideDisabled = !item.isSupported() || item.hasUnmetDependencies() || item.hasContentErrors();
//                                    String shortDesc = item.meta.shortDescription();
//
//                                    if(Objects.equals(item.meta.name, XVXModName)) {
//                                        String 版本 = "\n[lightgray]v" + Strings.stripColors(trimText(item.meta.version));
//                                        if(Objects.equals(item.meta.version, "0")){
//                                            版本 = " [red](预告版)\n版本: " + DawnText;
//                                        }
//                                        if(Objects.equals(item.meta.version, "-1")){
//                                            版本 = " [yellow](测试版)\n版本: " + BEText;
//                                        }
//                                        String 版本区别 = null;
//                                        if(Version.build == MDTVersion){
//                                            版本区别 = "\n[lightgray]编译的游戏版本: V" + MDTVersion + "\n[lightgray]本版本更新时间: " + ModTime;
//                                        }
//                                        if(Version.build < MDTVersion){
//                                            版本区别 = "\n[lightgray]编译的游戏版本: V" + MDTVersion + "\n[red](警告：您的游戏版本低于编译版本,可能会出错!)[]";
//                                        }
//                                        if(Version.build > MDTVersion){
//                                            版本区别 = "\n[lightgray]编译的游戏版本: V" + MDTVersion + "\n[yellow](注意：您的游戏版本高于编译版本,也许会出错!)[]";
//                                        }
//
//                                        text.add("[accent]" + Strings.stripColors(item.meta.displayName()) +
//                                                        版本 +
//                                                        版本区别 +
//                                                        (item.enabled() || hideDisabled ? "" : Core.bundle.get("mod.disabled") + ""))
//                                                .wrap().top().width(300f).growX().left();
//                                    }else {
//                                        if (finalA & (item.enabled() || hideDisabled)) {
//                                            String MDTversion = Core.bundle.format(item.meta.name + "-" + "DawnVersion");
//                                            String Modtime = Core.bundle.format(item.meta.name + "-" + "ModTime");
//
//                                            MDTversion = 检测(MDTversion, item.meta.name + "-DawnVersion");
//                                            Modtime = 检测(Modtime, item.meta.name + "-ModTime");
//
//                                            String 附属版本区别 = "\n[lightgray]以此破晓版本制作: " + MDTversion + " \n[lightgray]本版本更新时间: " + Modtime;
//
//                                            if(finalLibmod){
//                                                text.add("[accent]" + Strings.stripColors(item.meta.displayName()) + "   [#9370DB](前置模块)[]" +
//                                                                "\n[#9370DB]版本: " + Strings.stripColors(trimText(item.meta.version)) + 附属版本区别 +
//                                                                (item.enabled() || hideDisabled ? "" : Core.bundle.get("mod.disabled") + ""))
//                                                        .wrap().top().width(450f).growX().left();
//                                            }else {
//                                                text.add("[accent]" + Strings.stripColors(item.meta.displayName()) + "   [red](破晓附属Mod)[]" +
//                                                                "\n[lightgray]v" + Strings.stripColors(trimText(item.meta.version)) + 附属版本区别 +
//                                                                (item.enabled() || hideDisabled ? "" : Core.bundle.get("mod.disabled") + ""))
//                                                        .wrap().top().width(300f).growX().left();
//                                            }
//                                        } else {
//                                            text.add("[accent]" + Strings.stripColors(item.meta.displayName()) + "\n" +
//                                                            (shortDesc.length() > 0 ? "[lightgray]" + shortDesc + "\n" : "")
//                                                            //so does anybody care about version?
//                                                            //+ "[gray]v" + Strings.stripColors(trimText(item.meta.version)) + "\n"
//                                                            + (item.enabled() || hideDisabled ? "" : Core.bundle.get("mod.disabled") + ""))
//                                                    .wrap().top().width(300f).growX().left();
//                                        }
//                                    }
//
//                                    text.row();
//
//                                    if(item.isOutdated()){
//                                        text.labelWrap("@mod.outdatedv7").growX();
//                                        text.row();
//                                    }else if(!item.isSupported()){
//                                        text.labelWrap(Core.bundle.format("mod.requiresversion", item.meta.minGameVersion)).growX();
//                                        text.row();
//                                    }else if(item.hasUnmetDependencies()){
//                                        text.labelWrap(Core.bundle.format("mod.missingdependencies", item.missingDependencies.toString(", "))).growX();
//                                        t.row();
//                                    }else if(item.hasContentErrors()){
//                                        text.labelWrap("@mod.erroredcontent").growX();
//                                        text.row();
//                                    }else if(item.meta.hidden){
//                                        text.labelWrap("@mod.multiplayer.compatible").growX();
//                                        text.row();
//                                    }
//                                }).top().growX();
//
//                                title1.add().growX();
//                            }).growX().growY().left();
//
//                            if(!finalLibmod || !DawnLib.BOOLEAN) {
//                                t.table(right -> {
//                                    right.right();
//                                    right.button(item.enabled() ? Icon.downOpen : Icon.upOpen, Styles.clearNonei, () -> {
//                                        mods.setEnabled(item, !item.enabled());
//                                        setup();
//                                    }).size(50f).disabled(!item.isSupported());
//
//                                    right.button(item.hasSteamID() ? Icon.link : Icon.trash, Styles.clearNonei, () -> {
//                                        if (!item.hasSteamID()) {
//                                            ui.showConfirm("@confirm", "@mod.remove.confirm", () -> {
//                                                mods.removeMod(item);
//                                                setup();
//                                            });
//                                        } else {
//                                            platform.viewListing(item);
//                                        }
//                                    }).size(50f);
//
//                                    if (steam && !item.hasSteamID()) {
//                                        right.row();
//                                        right.button(Icon.export, Styles.clearNonei, () -> {
//                                            platform.publish(item);
//                                        }).size(50f);
//                                    }
//                                }).growX().right().padRight(-8f).padTop(-8f);
//                            }

                        }, Styles.flatBordert, () -> showMod(item)).size(w, h).growX().pad(4f);
                        pane[0].row();
                    }
                }

                if(!any){
                    pane[0].add("@none.found").color(Color.lightGray).pad(4);
                }
            };

            if(!mobile || Core.graphics.isPortrait()){
                cont.table(search -> {
                    search.image(Icon.zoom).padRight(8f);
                    search.field("", rebuild).growX();
                }).fillX().padBottom(4);
            }

            cont.row();
            cont.pane(table1 -> {
                pane[0] = table1.margin(10f).top();
                rebuild.get("");
            }).scrollX(false).update(s -> scroll = s.getScrollY()).get().setScrollYForce(scroll);
        }else{
            cont.table(Styles.black6, t -> t.add("@mods.none")).height(80f);
        }

        cont.row();
    }

    private void reload(){
        ui.showInfoOnHidden("@mods.reloadexit", () -> {
            Log.info("Exiting to reload mods.");
            Core.app.exit();
        });
    }

    private void showMod(LoadedMod mod){
        BaseDialog dialog = new BaseDialog(mod.meta.displayName());

        dialog.addCloseButton();

        if(!mobile){
            dialog.buttons.button("@mods.openfolder", Icon.link, () -> Core.app.openFolder(mod.file.absolutePath()));
        }

        if(mod.getRepo() != null) {
            boolean showImport = !mod.hasSteamID();

//            if (!Objects.equals(mod.meta.name, XVXModName)) {
//                boolean yyy = true;
//
//                for(var a :mod.meta.dependencies){
//                    if(Objects.equals(a, XVXModName)){
//                        yyy = false;
//                    }
//                }
//
//                if(yyy) {
//                    dialog.buttons.button("@mods.github.open", Icon.link, () -> Core.app.openURI("https://github.com/" + mod.getRepo()));
//                }
//            }

            if (mobile && showImport) dialog.buttons.row();
            if (showImport) {
                dialog.buttons.button("@mods.browser.reinstall", Icon.download, () -> githubImportMod(mod.getRepo(), mod.isJava()));
            }
        }

        dialog.cont.pane(desc -> {
            desc.center();
            desc.defaults().padTop(10).left();

            desc.add("@editor.name").padRight(10).color(Color.gray).padTop(0);
            desc.row();
            desc.add(mod.meta.displayName()).growX().wrap().padTop(2);
            desc.row();
            if(mod.meta.author != null){
                desc.add("@editor.author").padRight(10).color(Color.gray);
                desc.row();
                desc.add(mod.meta.author).growX().wrap().padTop(2);
                desc.row();
            }
            if(mod.meta.description != null){
                desc.add("@editor.description").padRight(10).color(Color.gray).top();
                desc.row();
                desc.add(mod.meta.description).growX().wrap().padTop(2);
                desc.row();
            }

        }).width(400f);

//     Seq<UnlockableContent> all = Seq.with(content.getContentMap()).<Content>flatten().select(c -> c.minfo.mod == mod && c instanceof UnlockableContent && !(c instanceof DawnConstructBlock) && !(c instanceof DevourSpiritKingBlock)).as();
//        if(all.any()){
//            dialog.cont.row();
//            dialog.cont.button("@mods.viewcontent", Icon.book, () -> {
//                BaseDialog d = new BaseDialog(mod.meta.displayName());
//                d.cont.pane(cs -> {
//                    int i = 0;
//                    for(UnlockableContent c : all){
//                        cs.button(new TextureRegionDrawable(c.uiIcon), Styles.flati, iconMed, () -> {
//                            ui.content.show(c);
//                        }).size(50f).with(im -> {
//                            var click = im.getClickListener();
//                            im.update(() -> im.getImage().color.lerp(!click.isOver() ? Color.lightGray : Color.white, 0.4f * Time.delta));
//
//                        }).tooltip(c.localizedName);
//
//                        if(++i % (int)Math.min(Core.graphics.getWidth() / Scl.scl(110), 14) == 0) cs.row();
//                    }
//                }).grow();
//                d.addCloseButton();
//                d.show();
//                currentContent = d;
//            }).size(300, 50).pad(4);
//
//            if (Objects.equals(mod.meta.name, XVXModName)) {
//                dialog.cont.row();
//                dialog.cont.button("启动MOD多人游戏兼容性([red]警告！不稳定内容！[])", () -> {
//                    mod.meta.hidden = true;
//                }).size(XVXDawnMod.UI窗口按钮大小, 50).pad(4);
//                dialog.cont.row();
//                dialog.row();
//                dialog.button(Core.bundle.format("UI.UI.破晓群"), Icon.link, () -> {
//                    if (!Core.app.openURI("https://jq.qq.com/?_wv=1027&k=L1YteWIT")) {
//                        ui.showErrorMessage("@linkfail");
//                        Core.app.setClipboardText("https://jq.qq.com/?_wv=1027&k=L1YteWIT");
//                    }
//                }).size(XVXDawnMod.UI窗口按钮大小, 50).pad(4);
//                dialog.row();
//                dialog.button(Core.bundle.format("UI.UI.光耀群"), Icon.link, () -> {
//                    if (!Core.app.openURI("https://jq.qq.com/?_wv=1027&k=vkLwRc65")) {
//                        ui.showErrorMessage("@linkfail");
//                        Core.app.setClipboardText("https://jq.qq.com/?_wv=1027&k=vkLwRc65");
//                    }
//                }).size(XVXDawnMod.UI窗口按钮大小, 50).pad(4);
//                dialog.row();
//                dialog.button(Core.bundle.format("UI.UI.萌新服"), Icon.link, () -> {
//                    服务器();
//                    关闭对话框();
//                }).size(XVXDawnMod.UI窗口按钮大小, 50).pad(4);
//                dialog.row();
//
//                DawnIcon icon = new DawnIcon(mod.name);
//                dialog.add(new DawnBorderImage() {{
//                    update(() -> setDrawable(icon.Icon()));
//                    setDrawable(Tex.nomap);
//                    border(Pal.accent);
//                }}).size(128).scaling(Scaling.fit);
//            }
//        }

        dialog.show();
    }

    private void showModBrowser(){
        rebuildBrowser();
        browser.show();
    }

    private void rebuildBrowser(){
        ObjectSet<String> installed = mods.list().map(m -> m.getRepo()).asSet();

        browserTable.clear();
        browserTable.add("@loading");

        int cols = (int)Math.max(Core.graphics.getWidth() / Scl.scl(480), 1);

        getModList(rlistings -> {
            browserTable.clear();
            int i = 0;

            var listings = rlistings;
            if(!orderDate){
                listings = rlistings.copy();
                listings.sortComparing(m1 -> -m1.stars);
            }

            for(ModListing mod : listings){
                if((mod.hasJava && Vars.ios) || (!Strings.matches(searchtxt, mod.name) && !Strings.matches(searchtxt, mod.repo)) || (Vars.ios && mod.hasScripts)) continue;

                float s = 64f;

                browserTable.button(con -> {
                    con.margin(0f);
                    con.left();

                    String repo = mod.repo;
                    con.add(new BorderImage(){
                        TextureRegion last;

                        {
                            border(installed.contains(repo) ? Pal.accent : Color.lightGray);
                            setDrawable(Tex.nomap);
                            pad = Scl.scl(4f);
                        }

                        @Override
                        public void draw(){
                            super.draw();

                            //textures are only requested when the rendering happens; this assists with culling
                            if(!textureCache.containsKey(repo)){
                                textureCache.put(repo, last = Core.atlas.find("nomap"));
                                Http.get("https://raw.githubusercontent.com/Anuken/MindustryMods/master/icons/" + repo.replace("/", "_"), res -> {
                                    Pixmap pix = new Pixmap(res.getResult());
                                    Core.app.post(() -> {
                                        try{
                                            var tex = new Texture(pix);
                                            tex.setFilter(TextureFilter.linear);
                                            textureCache.put(repo, new TextureRegion(tex));
                                            pix.dispose();
                                        }catch(Exception e){
                                            Log.err(e);
                                        }
                                    });
                                }, err -> {});
                            }

                            var next = textureCache.get(repo);
                            if(last != next){
                                last = next;
                                setDrawable(next);
                            }
                        }
                    }).size(s).pad(4f * 2f);

                    con.add(
                                    "[accent]" + mod.name.replace("\n", "") +
                                            (installed.contains(mod.repo) ? "\n[lightgray]" + Core.bundle.get("mod.installed") : "") +
                                            "\n[lightgray]\uE809 " + mod.stars +
                                            (Version.isAtLeast(mod.minGameVersion) ? mod.subtitle == null ? "" : "\n[lightgray]" + Strings.truncate(mod.subtitle, maxModSubtitleLength) :
                                                    "\n" + Core.bundle.format("mod.requiresversion", mod.minGameVersion)))
                            .width(358f).wrap().grow().pad(4f, 2f, 4f, 6f).top().left().labelAlign(Align.topLeft);

                }, Styles.flatBordert, () -> {
                    var sel = new BaseDialog(mod.name);
                    sel.cont.pane(p -> p.add(mod.description + "\n\n[accent]" + Core.bundle.get("editor.author") + "[lightgray] " + mod.author)
                            .width(mobile ? 400f : 500f).wrap().pad(4f).labelAlign(Align.center, Align.left)).grow();
                    sel.buttons.defaults().size(150f, 54f).pad(2f);
                    sel.buttons.button("@back", Icon.left, () -> {
                        sel.clear();
                        sel.hide();
                    });

                    var found = mods.list().find(l -> mod.repo != null && mod.repo.equals(l.getRepo()));
                    sel.buttons.button(found == null ? "@mods.browser.add" : "@mods.browser.reinstall", Icon.download, () -> {
                        sel.hide();
                        githubImportMod(mod.repo, mod.hasJava);
                    });

//                    if(!Objects.equals(mod.name, XVXModName)) {
//                        sel.buttons.button("@mods.github.open", Icon.link, () -> {
//                            Core.app.openURI("https://github.com/" + mod.repo);
//                        });
//                    }

                    sel.keyDown(KeyCode.escape, sel::hide);
                    sel.keyDown(KeyCode.back, sel::hide);
                    sel.show();
                }).width(438f).pad(4).growX().left().height(s + 8*2f).fillY();

                if(++i % cols == 0) browserTable.row();
            }
        });
    }

    private String trimText(String text){
        if(text == null) return "";
        if(text.contains("\n")){
            return text.substring(0, text.indexOf("\n"));
        }
        return text;
    }

    private void handleMod(String repo, HttpResponse result){
        try{
            Fi file = tmpDirectory.child(repo.replace("/", "") + ".zip");
            long len = result.getContentLength();
            Floatc cons = len <= 0 ? f -> {} : p -> modImportProgress = p;

            Streams.copyProgress(result.getResultAsStream(), file.write(false), len, 4096, cons);

            var mod = mods.importMod(file);
            mod.setRepo(repo);
            file.delete();
            Core.app.post(() -> {

                try{
                    setup();
                    ui.loadfrag.hide();
                }catch(Throwable e){
                    ui.showException(e);
                }
            });
        }catch(Throwable e){
            modError(e);
        }
    }

    private void importFail(Throwable t){
        Core.app.post(() -> modError(t));
    }

    private void githubImportMod(String repo, boolean isJava){
        Log.info(repo);
        modImportProgress = 0f;
        ui.loadfrag.show("@downloading");
        ui.loadfrag.setProgress(() -> modImportProgress);

        if(isJava){
            githubImportJavaMod(repo);
        }else{
            Http.get(ghApi + "/repos/" + repo, res -> {
                var json = Jval.read(res.getResultAsString());
                String mainBranch = json.getString("default_branch");
                String language = json.getString("language", "<none>");

                //this is a crude heuristic for class mods; only required for direct github import
                //TODO make a more reliable way to distinguish java mod repos
                if(language.equals("Java") || language.equals("Kotlin")){
                    githubImportJavaMod(repo);
                }else{
                    githubImportBranch(mainBranch, repo);
                }
            }, this::importFail);
        }
    }

    private void githubImportJavaMod(String repo){
        //grab latest release
        Http.get(ghApi + "/repos/" + repo + "/releases/latest", res -> {
            var json = Jval.read(res.getResultAsString());
            var assets = json.get("assets").asArray();

            //prioritize dexed jar, as that's what Sonnicon's mod template outputs
            var dexedAsset = assets.find(j -> j.getString("name").startsWith("dexed") && j.getString("name").endsWith(".jar"));
            var asset = dexedAsset == null ? assets.find(j -> j.getString("name").endsWith(".jar")) : dexedAsset;

            if(asset != null){
                //grab actual file
                var url = asset.getString("browser_download_url");

                Http.get(url, result -> handleMod(repo, result), this::importFail);
            }else{
                throw new ArcRuntimeException("No JAR file found in releases. Make sure you have a valid jar file in the mod's latest Github Release.");
            }
        }, this::importFail);
    }

    private void githubImportBranch(String branch, String repo){
        Http.get(ghApi + "/repos/" + repo + "/zipball/" + branch, loc -> {
            if(loc.getHeader("Location") != null){
                Http.get(loc.getHeader("Location"), result -> {
                    handleMod(repo, result);
                }, this::importFail);
            }else{
                handleMod(repo, loc);
            }
        }, this::importFail);
    }

    public static String 检测(String 内容, String 正确){
        if(Objects.equals(内容, "???" + 正确 + "???")){
            return "未知";
        }
        return 内容;
    }
}
