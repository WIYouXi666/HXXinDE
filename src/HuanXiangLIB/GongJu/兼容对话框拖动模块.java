package HuanXiangLIB.GongJu;

import arc.input.KeyCode;
import arc.scene.Element;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.util.Tmp;

public class 兼容对话框拖动模块 extends InputListener {
public Element t;
public float lastx = 400.0f;
public float lasty = 1.0f;

@Override
public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button){
    var v = t.localToParentCoordinates(Tmp.v1.set(x, y));
    this.lastx = v.x;
    this.lasty = v.y;
    return true;
}

@Override
public void touchDragged(InputEvent event, float x, float y, int pointer){
    var v = t.localToParentCoordinates(Tmp.v1.set(x, y));
    t.translation.add(v.x - this.lastx, v.y - this.lasty);
    this.lastx = v.x;
    this.lasty = v.y;
}}