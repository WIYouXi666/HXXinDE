package 核心.GongJu;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
//TODO
public class 图片 {
    TextureRegion uiIcon;
    public 图片(String name) {
        uiIcon  = Core.atlas.find(name);
    }
}
