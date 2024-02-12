package HuanXiang.type;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.logic.LAccess;
import mindustry.logic.Senseable;
import mindustry.type.Item;

public class 能量 extends UnlockableContent implements Senseable {










    public static final Seq<能量> all = new Seq<>();
    public int id;
    public Color color;
  /**
 * @see Item
 * 如果>0，则此项目将进行动画处理
 * */
    public int 图片数 = 0;
    public int 过渡帧 = 0;
    public float 帧时间 = 5f;
    public 能量(String name, Color color) {
        super(name);
        this.color = color;
        this.localizedName = Core.bundle.get(getContentTypehh() + "." + this.name + ".name", this.name);
        this.description = Core.bundle.getOrNull(getContentTypehh() + "." + this.name + ".description");
        this.details = Core.bundle.getOrNull(getContentTypehh() + "." + this.name + ".details");
        this.unlocked = Core.settings != null && Core.settings.getBool(this.name + "-unlocked", false);
        id = all.size;
        all.add(this);
    }
    public 能量(String name) { this(name, new Color(Color.black));}


    @Override
    public String toString() {
        return localizedName;
    }

    @Override
    public void loadIcon() {
        fullIcon =
                Core.atlas.find(getContentTypehh() + "-" + name + "-full",
                        Core.atlas.find(name + "-full",
                                Core.atlas.find(name,
                                        Core.atlas.find(getContentTypehh() + "-" + name,
                                                Core.atlas.find(name + "1")))));
        uiIcon = Core.atlas.find(getContentTypehh() + "-" + name + "-ui", fullIcon);

            if(图片数 > 0){
                TextureRegion[] regions = new TextureRegion[图片数 * (过渡帧 + 1)];

                if(过渡帧 <= 0){
                    for(int i = 1; i <= 图片数; i++){
                        regions[i - 1] = Core.atlas.find(name + i);
                    }
                }else{
                    for(int i = 0; i < 图片数; i++){
                        regions[i * (过渡帧 + 1)] = Core.atlas.find(name + (i + 1));
                        for(int j = 1; j <= 过渡帧; j++){
                            int index = i * (过渡帧 + 1) + j;
                            regions[index] = Core.atlas.find(name + "-t" + index);
                        }
                    }
                }

                fullIcon = new TextureRegion(fullIcon);
                uiIcon = new TextureRegion(uiIcon);

                Events.run(EventType.Trigger.update, () -> {
                    int frame = (int)(Time.globalTime / 帧时间) % regions.length;
                    fullIcon.set(regions[frame]);
                    uiIcon.set(regions[frame]);
                });
            }

    }

    /**
     * Returns the type name of this piece of content.
     * This should return the same value for all instances of this content type.
     */
    @Override
    public ContentType getContentType() {
        return ContentType.effect_UNUSED;
    }
    @Override
    public boolean isHidden(){
        return true;
    }
    public String getContentTypehh() {
        return this.name;
    }


    @Override
    public double sense(LAccess sensor) {
        return 0;
    }

    @Override
    public double sense(Content content) {
        return Senseable.super.sense(content);
    }

    @Override
    public Object senseObject(LAccess sensor) {
        return Senseable.super.senseObject(sensor);
    }
}
