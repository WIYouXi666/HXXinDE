package HuanXiangLIB.ui类;

import HuanXiangLIB.接口.ui兼容类;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.scene.Group;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

import static mindustry.Vars.ui;

//ApplicationCore
public class SAFEDS implements ui兼容类 {

    public  class sfvb extends TextureRegionDrawable {
        public sfvb() {
            region = (TextureRegion) Tex.whiteui;
        }

        @Override
        public void draw(float x, float y, float width, float height) {

        }
    }









    Color unfinish = new Color(0, 0, 0, 0.3f);
    Color finish = new Color(0, 0, 0, 0.8f);
    Color disabledColor = new Color(1, 1, 1, 0.4f);
    ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();

    {
        {
            imageStyle.down = Styles.flatDown;
            imageStyle.up = Styles.black;
            imageStyle.over = Styles.flatOver;
            imageStyle.imageDisabledColor = disabledColor;
            imageStyle.imageUpColor = Color.white;
            imageStyle.disabled = Styles.black3;
            imageStyle.checked = Styles.flatDown;
            imageStyle.disabled = new sfvb();
        }
    }



    Table 记录1;
    public void 重置(){
        if (ui.hudGroup.find("kkk") != null){
            int a = 记录1.getZIndex();
            Group b = 记录1.parent;
            记录1.remove();
            build(b);
            记录1.setZIndex(a);
        }
    }
    @Override
    public void build(Group parent) {
        parent.fill(i -> {
            i.name = "kkk";
            记录1 = i;







        });
    }
}
