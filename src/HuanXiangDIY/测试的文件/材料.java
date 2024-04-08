package HuanXiangDIY.测试的文件;

import arc.Events;
import arc.scene.Group;
import arc.scene.style.Drawable;
import mindustry.content.Items;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.ui.fragments.MenuFragment;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.ui;
import static mindustry.Vars.world;

public class 材料 {

    public static void 目标(int pos,Floor a,Floor overlay,Block block,Team 团队, int 角度) {
        //(x,y)
        world.build(pos);//目标Building
        world.tile(pos);//目标Tile

        world.tile(pos).floor();//检测地板
        world.tile(pos).overlay();//检测覆盖
        world.tile(pos).block();//检测块

        world.tile(pos).setAir();//设置空气
        world.tile(pos).setFloor(a);//设置地板
        world.tile(pos).setOverlay(overlay);//设置覆盖
        world.tile(pos).setBlock(block, 团队, 角度);//设置块

    }

    public static void 触发事件() {

        Events.fire(EventType.Trigger.teamCoreDamage);
        Events.fire(EventType.Trigger.thoriumReactorOverheat);

    }
    private void 信息栏(UnlockableContent 内容) {

        String text = null;
        Drawable icon = null;
        Runnable callback = null;
        MenuFragment.MenuButton button = null;
        Group sfcd = null;



        ui.showLabel("FHGFH",0.0000000001F,0, 0);//显示世界中的标签。这个标签是一切的幕后推手。不褪色

        //MenuFragment
        ui.menufrag.build(sfcd);//创建菜单？？？
        ui.menufrag.addButton(text, icon, callback);//菜单添加按钮
        ui.menufrag.addButton(text, callback);//菜单添加按钮
        ui.menufrag.addButton(button);//菜单添加按钮


        //MinimapFragment// 小地图
        //PlayerListFragment// 玩家列表
        //HintsFragment 提示片段
        //PlacementFragment 放置片段
    }
//
//    public static class aaa {
//        Category 了;
//    }

//    public static class TableT extends Table {
//    }
//
//    public static class BaseDialogT extends BaseDialog {
//
//        public BaseDialogT(String A) {
//            super(A);
//        }
//    }


public static class 表 {
    public Item item;
    public float amount = 0;

    public 表(Item item, float amount) {
        if (item == null) item = Items.copper;
        this.item = item;
        this.amount = amount;
    }

    public static 表[] with(Object... items) {
        var stacks = new 表[items.length / 2];
        for (int i = 0; i < items.length; i += 2) {
            stacks[i / 2] = new 表((Item) items[i], ((Number) items[i + 1]).intValue());
        }
        return stacks;
    }
}

    public void 设置地板空气 ( int p, Block floor){
        world.tile(p).setFloorNet(floor);
    }

    public void 设置地板空气 ( int x, int y, Block floor){
        world.tile(x, y).setFloorNet(floor);
    }

    public void 设置地板覆盖 ( int p, Block floor, Block overlay){
        world.tile(p).setFloorNet(floor, overlay);
    }

    public void 设置地板覆盖 ( int x, int y, Block floor, Block overlay){
        world.tile(x, y).setFloorNet(floor, overlay);
    }

    public void 设置地板保覆盖 ( int p, Floor floor){
        world.tile(p).setFloorUnder(floor);
    }

    public void 设置地板保覆盖 ( int x, int y, Floor floor){
        world.tile(x, y).setFloorUnder(floor);
    }

    public void 检测地板 () {
        int a = 0, b = 0, c = 0, d = 0;
        Block s = null, ssdfg = null, sdfs = null;
        Team x = null;
        Floor ghn = null;

        world.tile(a).setFloorNet(s);//设置Floor + 空气
        world.tile(a).setFloorNet(s, s);//设置Floor +overlay
        world.tile(a).setFloorUnder(ghn);//设置地板，保留覆盖

//        world.tile(a).setNet();
//        world.tile(a).setOverlay();
//        world.tile(a).setOverlayNet();
//        world.tile(a).setOverlayQuiet(s);
        Team team = null;
        world.tile(a).setTeam(team);


    }
}
