package MODS.一些方法.工具;

import arc.input.KeyCode;
import arc.scene.Element;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.util.Tmp;

public class 兼容对话框拖动模块 extends InputListener {
public Element 元素;
public float X = 400.0f;
public float lasty = 1.0f;


@Override
public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button){
    var v = 元素.localToParentCoordinates(Tmp.v1.set(x, y));
    this.X = v.x;
    this.lasty = v.y;
    return true;
}

@Override
public void touchDragged(InputEvent event, float x, float y, int pointer){
    var v = 元素.localToParentCoordinates(Tmp.v1.set(x, y));
    元素.translation.add(v.x - this.X, v.y - this.lasty);
    this.X = v.x;
    this.lasty = v.y;
}}