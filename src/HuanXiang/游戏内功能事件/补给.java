package HuanXiang.游戏内功能事件;

import HuanXiang.核心内容.事件.局内事件主类;
import arc.struct.ObjectMap;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.type.Item;

public class 补给 extends 局内事件主类 {
    public ObjectMap<Item, Integer> all = new ObjectMap<>();
    public Team team = Team.baseTeams[1];
    public 补给() {
        super("补给");
    }
    public 补给(Team team,ObjectMap<Item, Integer> all) {
        super("补给");
        this.team = team;
        this.all = all;
    }
    public 补给(Team team,Object[] all) {
        super("补给" + 局内事件主类.all.size);
        this.team = team;
        if(all.length/2 ==0){this.all = new ObjectMap<>();}else {
            for (int i=0; i<all.length;i+=2) {
                this.all.put((Item) all[i], (Integer) all[i+1]);
            }
        }
    }
    @Override
    public void 执行() {
        if (Vars.state.isGame()) {
            for (ObjectMap.Entry<Item, Integer> item : all) {
                team.core().items.add(item.key,item.value);
            }
        }
    }
}
