package 实际内容.type;

import mindustry.gen.*;
import mindustry.type.UnitType;

import static mindustry.Vars.ui;

public class XinUnits extends UnitType {
    public XinUnits(String name) {
        super(name);

    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        ui.chatfrag.addMessage(String.valueOf(this.id));//添加聊天信息
        ui.chatfrag.addMessage(String.valueOf(this.constructor));//添加聊天信息
    }
}
