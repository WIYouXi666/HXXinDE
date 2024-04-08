package HuanXiangLIB.记录$功能;

import arc.struct.ObjectMap;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.entities.bullet.BulletType;
import mindustry.type.*;
import mindustry.world.Block;

public class 全部内容 {

    private static final ObjectMap<String, Item> Item =  new ObjectMap<>();
    private static final ObjectMap<String, Liquid> Liquid =  new ObjectMap<>();
    private static final ObjectMap<String, Block> Block =  new ObjectMap<>();
    private static final ObjectMap<String, StatusEffect> StatusEffect = new ObjectMap<>();
    private static final ObjectMap<String, UnitType> UnitType = new ObjectMap<>();
    private static final ObjectMap<String, SectorPreset> SectorPreset =  new ObjectMap<>();
    private static final ObjectMap<String, Planet> Planet =  new ObjectMap<>();
    private static final ObjectMap<String, BulletType> BulletType =  new ObjectMap<>();

    static {
        Vars.content.items().each(item -> {
            Item.put(item.localizedName,item);
        });
        Vars.content.blocks().each(item -> {
            Block.put(item.localizedName,item);
        });
        Vars.content.liquids().each(item -> {
            Liquid.put(item.localizedName,item);
        });
        Vars.content.statusEffects().each(item -> {
            StatusEffect.put(item.localizedName,item);
        });
        Vars.content.bullets().each(item -> {
            BulletType.put(String.valueOf(item.id),item);
        });
        Vars.content.units().each(item -> {
            UnitType.put(item.localizedName,item);
        });
        Vars.content.getBy(ContentType.sector).each(item -> {
            SectorPreset.put(((SectorPreset)item).localizedName,(SectorPreset)item);
        });
        Vars.content.planets().each(item -> {
            Planet.put(item.localizedName,item);
        });
    }
    public static Item getItem(String name){
        if(Item.containsKey(name)) {
            return Item.get(name);
        }
        return null;
    }
    public static Liquid getLiquid(String name){
        if(Liquid.containsKey(name)) {
            return Liquid.get(name);
        }
        return null;
    }
    public static Block getBlock(String name){
        if(Block.containsKey(name)) {
            return Block.get(name);
        }
        return null;
    }
    public static StatusEffect getStatusEffect(String name){
        if(StatusEffect.containsKey(name)) {
            return StatusEffect.get(name);
        }
        return null;
    }
    public static UnitType getUnitType(String name){
        if(UnitType.containsKey(name)) {
            return UnitType.get(name);
        }
        return null;
    }
    public static SectorPreset getSectorPreset(String name){
        if(SectorPreset.containsKey(name)) {
            return SectorPreset.get(name);
        }
        return null;
    }
    public static Planet getPlanet(String name){
        if(Planet.containsKey(name)) {
            return Planet.get(name);
        }
        return null;
    }
    public static BulletType getBulletType(String name){
        if(BulletType.containsKey(name)) {
            return BulletType.get(name);
        }
        return null;
    }






















}
