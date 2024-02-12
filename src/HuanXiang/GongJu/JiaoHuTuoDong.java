package HuanXiang.GongJu;

import arc.input.KeyCode;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.ui.layout.Table;
import arc.util.Tmp;

public class JiaoHuTuoDong extends InputListener {
    /**
     * {@link Table#addListener}table.addListener(new JiaoHuTuoDong) 添加侦测器
     */
    public Table t;
    public float lastx = 0F;
    public float lasty = 0F;

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button){
        var v = t.localToParentCoordinates(Tmp.v1.set(x, y));
        lastx = v.x;
        lasty = v.y;
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer){
        var v = t.localToParentCoordinates(Tmp.v1.set(x, y));
        t.translation.add(v.x - this.lastx, v.y - this.lasty);
        lastx = v.x;
        lasty = v.y;
    }
}
