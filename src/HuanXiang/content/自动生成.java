package HuanXiang.content;

import arc.Core;
import arc.graphics.g2d.TextureAtlas;
import arc.graphics.g2d.TextureRegion;
import mindustry.Vars;
import mindustry.type.Item;

public class 自动生成 {

    public static void load() {
        Vars.content.liquids().each(l -> {
            if (!l.gas) {
                //液体桶
                new Item(l.name + "-tong", l.color) {
                    {
                        hidden = true;
                        localizedName = l.localizedName + "桶";
                        description = "抽象";
                    }
                    @Override
                    public void loadIcon() {
                        super.loadIcon();
                            TextureAtlas.AtlasRegion laser = Core.atlas.find("liquid-"+l.name);
                            fullIcon = new TextureRegion(laser);
                            uiIcon = new TextureRegion(laser);
                    }
                };
//                new Floor(l.name + "-diban-q",0) {
//                    {
//                        speedMultiplier = 1-l.viscosity;//简单粗暴
////                        if(true) {
////                            this.status = StatusEffects.wet;
////                            this.statusDuration = 140.0F;
////                        }
//                        liquidDrop = l;
//                        isLiquid = true;
//                        cacheLayer = CacheLayer.water;
//                        albedo = l.viscosity;
//                        supportsOverlay = true;
//                        shallow = true;
//                        localizedName = l.localizedName + "地板(浅)";
//                    }
//                    @Override
//                    public void loadIcon() {
//                        super.loadIcon();
//                        TextureAtlas.AtlasRegion laser = Core.atlas.find(name);
//                        fullIcon = new TextureRegion(laser);
//                        uiIcon = new TextureRegion(laser);
//                    }
//                };
//                new Floor(l.name + "-diban-s",0) {
//                    {
//                        speedMultiplier = 1-l.viscosity;//简单粗暴
//                        variants = 0;
////                        if(true) {
////                            this.status = StatusEffects.wet;
////                            this.statusDuration = 140.0F;
////                        }
//                        drownTime = 200.0F;
//                        liquidDrop = l;
//                        isLiquid = true;
//                        cacheLayer = CacheLayer.water;
//                        albedo = l.viscosity;
//                        supportsOverlay = true;
//                        shallow = false;
//                        localizedName = l.localizedName + "地板( 深)";
//                    }
//                    @Override
//                    public void loadIcon() {
//                        super.loadIcon();
//                        TextureAtlas.AtlasRegion laser = Core.atlas.find(name);
//                        fullIcon = new TextureRegion(laser);
//                        uiIcon = new TextureRegion(laser);
//                    }
//                };
                // 喷口
            }
            if (l.gas) {
                //气体罐子
                new Item(l.name + "-guan", l.color) {
                    {
                        hidden = true;
                        localizedName = l.localizedName + "罐";
                        description = "抽象";
                    }
                    @Override
                    public void loadIcon() {
                        super.loadIcon();
                            TextureAtlas.AtlasRegion laser = Core.atlas.find("liquid-"+l.name);
                            fullIcon = new TextureRegion(laser);
                            uiIcon = new TextureRegion(laser);
                    }
                };

            }
            // 喷口
        });
    }
}