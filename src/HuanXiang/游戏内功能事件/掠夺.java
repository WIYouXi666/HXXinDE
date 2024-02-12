package HuanXiang.游戏内功能事件;

import HuanXiang.核心内容.事件.局内事件主类;
import arc.struct.ObjectMap;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.type.Item;

public class 掠夺 extends 局内事件主类 {
    public ObjectMap<Item, Integer> all = new ObjectMap<>();
    public Team team = Team.baseTeams[1];
    public 掠夺() {
        super("掠夺");
    }
    public 掠夺(Team team,ObjectMap<Item, Integer> all) {
        super("掠夺");
        this.team = team;
        this.all = all;
    }
    public 掠夺(Integer dghf) {
        super("掠夺");
    }
    @Override
    public void 执行() {
        if (Vars.state.isGame()) {
            try {
                for (Team teams : Team.baseTeams) {
                    if (teams.core() == null) {
                        continue;
                    }
                    if (teams == Team.baseTeams[0]) {
                        continue;
                    }
                    if (teams == team) {
                        continue;
                    }
                    for (ObjectMap.Entry<Item, Integer> item : all) {
                        int add = teams.core().items.get(item.key);
                        add = add>item.value?item.value:add;
                        teams.core().items.remove(item.key, add);
                        team.core().items.add(item.key, add);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
