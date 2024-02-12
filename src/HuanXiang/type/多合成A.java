package HuanXiang.type;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.ui.ItemImage;
import mindustry.ui.ReqImage;
import mindustry.ui.Styles;
import mindustry.world.meta.BlockStatus;
import mindustry.world.meta.Stat;
//TODO   要重写
public class 多合成A extends 核心工厂 {
	/**
	 *新视界  大型粉碎机
	 * sandCracker = new MultiCrafter("sand-cracker"){{
	 * 			size = 2;
	 * 			requirements(Category.crafting, ItemStack.with(Items.lead, 40, Items.copper, 60, Items.graphite, 45));
	 * 			health = 320;
	 * 			craftTime = 45f;
	 * 			itemCapacity = 20;
	 * 			hasPower = hasItems = true;
	 * 			craftEffect = NHFx.hugeSmokeGray;
	 * 			updateEffect = new Effect(80f, e -> {
	 * 				Fx.rand.setSeed(e.id);
	 * 				Draw.color(Color.lightGray, Color.gray, e.fin());
	 * 				Angles.randLenVectors(e.id, 4, 2.0F + 12.0F * e.fin(Interp.pow3Out), (x, y) -> {
	 * 					Fill.circle(e.x + x, e.y + y, e.fout() * Fx.rand.random(1, 2.5f));
	 *                                });* 			}).layer(Layer.blockOver + 1);
	 * 			drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawFrames(), new DrawArcSmelt(), new DrawDefault());
	 * 			consumePower(1.5f);
	 * 			setExchangeMap(Items.copper, 1, 1, Items.lead, 1, 1, Items.titanium, 1, 2, Items.thorium, 1, 3, Items.scrap, 2, 5, Items.beryllium, 2, 3, Items.tungsten, 1, 2);
	 * 			setOutput(Items.sand);
	 * 		}};
	 * **/
	public ObjectMap<ItemStack, Integer> 配方集合 = new ObjectMap<>();

	public 多合成A(String name){
		super(name);
	}
	
	public void 设置输出(Item item){
		outputItem = new ItemStack(item, 0);
	}
	
	public void 设置配方(Object... items){
		for(int i = 0; i < items.length; i += 3){
			配方集合.put(new ItemStack((Item)items[i], ((Number)items[i + 1]).intValue()), ((Number)items[i + 2]).intValue());
		}
	}
	public Table exchangeTable(Building building){
		int index = 0;
		Table table = new Table();
		
		for(ItemStack stack : 配方集合.keys()){
			table.table(Styles.grayPanel, i -> {
				i.add(new ReqImage(
						new ItemImage(stack.item.uiIcon, stack.amount),
						() -> building == null || building.items != null && building.items.has(stack.item, stack.amount)
				)).growX().height(40f).left();
				i.add(" -> ").growX().height(40f);
				i.add(new ItemImage(outputItem.item.uiIcon, 配方集合.get(stack))).growX().height(40f).right();
			}).grow().padRight(16f);
			if((++index % 2) == 0)table.row();
		}
		return table;
	}
	
	@Override
	public void setStats(){
		super.setStats();
		
		stats.remove(Stat.output);
		stats.add(Stat.output, t -> t.add(exchangeTable(null)));
	}
	
	@Override
	public void init(){
		consumeItems(配方集合.keys().toSeq().toArray(ItemStack.class)).optional(true, false);
		super.init();
	}
	
	
	
	public class MultiCrafterBuild extends GenericCrafterBuild{
		
		@Override
		public BlockStatus status(){
			if(!shouldConsume()){
				return BlockStatus.noOutput;
			}
			
			if(!isValid() || !productionValid() || !consValid()){
				return BlockStatus.noInput;
			}
			
			return BlockStatus.active;
		}
		
		@Override
		public boolean acceptItem(Building source, Item item){
			return block.consumesItem(item) && items.get(item) < getMaximumAccepted(item);
		}
		
		public int count(){
			
			int out = 0;
			for(ItemStack stack : 配方集合.keys()){
				if(items.has(stack.item, stack.amount))
					out += 配方集合.get(stack);
			}
			
			return out;
		}
		
		@Override
		public void updateTile(){
			if(efficiency > 0){
				progress += getProgressIncrease(craftTime);
				totalProgress += delta();
				warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
				
				if(Mathf.chanceDelta(updateEffectChance)){
					updateEffect.at(getX() + Mathf.range(size * 4f), getY() + Mathf.range(size * 4));
				}
			}else{
				warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
			}
			
			if(progress >= 1f){
				craft();
			}
			
			if(outputItems != null && timer(timerDump, dumpTime / timeScale)){
				for(ItemStack output : outputItems){
					dump(output.item);
				}
			}
		}
		
		@Override
		public void craft(){
			int out = count();
			
			consume();
			
			for(int i = 0; i < out; i++){
				offload(outputItem.item);
			}
			
			if(wasVisible){
				craftEffect.at(x, y);
			}
			
			progress %= 1;
		}
		
		@Deprecated
		public boolean consValid(){
			return enabled && count() > 0 && shouldConsume() && efficiency > 0;
		}
		
		@Override
		public boolean shouldConsume(){
			if(outputItems != null){
				int out = count();
				for(ItemStack output : outputItems){
					if(items.get(output.item) + count() > itemCapacity){
						return false;
					}
				}
			}
			
			return (outputLiquid == null || !(liquids.get(outputLiquid.liquid) >= liquidCapacity - 0.001f)) && enabled;
		}
	}
}
