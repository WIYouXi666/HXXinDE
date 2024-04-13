package HuanXiangLIB.单位实体改;

import mindustry.entities.units.WeaponMount;
import mindustry.type.Weapon;

public class 武器挂载 extends WeaponMount {

    public 武器挂载(Weapon weapon ,float x,float y) {
        super(weapon);
        weapon.y = y;
        weapon.x = x;
    }
}
