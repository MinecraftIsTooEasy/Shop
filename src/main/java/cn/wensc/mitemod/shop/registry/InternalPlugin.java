package cn.wensc.mitemod.shop.registry;

import cn.wensc.mitemod.shop.api.ShopItem;
import cn.wensc.mitemod.shop.api.ShopPlugin;
import cn.wensc.mitemod.shop.api.ShopRegistry;
import net.minecraft.Block;
import net.minecraft.Item;

public class InternalPlugin implements ShopPlugin {
    @Override
    public void register(ShopRegistry registry) {
        this.registerPrices(registry);
    }

    private void registerPrices(ShopRegistry registry) {
        ((ShopItem) Item.manure).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.seeds).setSoldPriceForAllSubs(0.25D);
        ((ShopItem) Item.sinew).setSoldPriceForAllSubs(0.25D);
        ((ShopItem) Item.leather).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.silk).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.feather).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.flint).setSoldPriceForAllSubs(2.5D);
        ((ShopItem) Item.chipFlint).setSoldPriceForAllSubs(0.5D);
        ((ShopItem) Item.shardObsidian).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.shardEmerald).setSoldPriceForAllSubs(2.5D);
        ((ShopItem) Item.shardDiamond).setSoldPriceForAllSubs(2.5D);
        ((ShopItem) Item.redstone).setSoldPriceForAllSubs(2.5D);
        ((ShopItem) Item.coal).setSoldPriceForAllSubs(5.0D);
        ((ShopItem) Item.bone).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.gunpowder).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.rottenFlesh).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.spiderEye).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.doorWood).setSoldPriceForAllSubs(10.0D);
        ((ShopItem) Item.doorCopper).setSoldPriceForAllSubs(60.0D);
        ((ShopItem) Item.doorSilver).setSoldPriceForAllSubs(60.0D);
        ((ShopItem) Item.doorGold).setSoldPriceForAllSubs(60.0D);
        ((ShopItem) Item.doorIron).setSoldPriceForAllSubs(120.0D);
        ((ShopItem) Item.doorAncientMetal).setSoldPriceForAllSubs(180.0D);
        ((ShopItem) Item.doorMithril).setSoldPriceForAllSubs(240.0D);
        ((ShopItem) Item.doorAdamantium).setSoldPriceForAllSubs(480.0D);
        ((ShopItem) Item.ingotCopper).setSoldPriceForAllSubs(10.0D);
        ((ShopItem) Item.ingotSilver).setSoldPriceForAllSubs(10.0D);
        ((ShopItem) Item.ingotGold).setSoldPriceForAllSubs(10.0D);
        ((ShopItem) Item.ingotIron).setSoldPriceForAllSubs(20.0D);
        ((ShopItem) Item.ingotAncientMetal).setSoldPriceForAllSubs(30.0D);
        ((ShopItem) Item.ingotMithril).setSoldPriceForAllSubs(40.0D);
        ((ShopItem) Item.ingotAdamantium).setSoldPriceForAllSubs(80.0D);
        ((ShopItem) Item.minecartEmpty).setSoldPriceForAllSubs(100.0D);
        ((ShopItem) Item.saddle).setSoldPriceForAllSubs(5.0D);
        ((ShopItem) Item.copperNugget).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.silverNugget).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.goldNugget).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.ironNugget).setSoldPriceForAllSubs(2.0D);
        ((ShopItem) Item.ancientMetalNugget).setSoldPriceForAllSubs(3.0D);
        ((ShopItem) Item.mithrilNugget).setSoldPriceForAllSubs(4.0D);
        ((ShopItem) Item.adamantiumNugget).setSoldPriceForAllSubs(8.0D);
        ((ShopItem) Item.getItem(Block.plantYellow.blockID)).setSoldPriceForAllSubs(0.25D);
        ((ShopItem) Item.getItem(Block.plantRed.blockID)).setSoldPriceForAllSubs(0.25D);
        ((ShopItem) Item.getItem(Block.leaves.blockID)).setSoldPriceForAllSubs(0.5D);
        ((ShopItem) Item.getItem(Block.planks.blockID)).setSoldPriceForAllSubs(1.25D);
        ((ShopItem) Item.getItem(Block.pumpkin.blockID)).setSoldPriceForAllSubs(2.0D);
        ((ShopItem) Item.getItem(Block.dirt.blockID)).setSoldPriceForAllSubs(0.5D);
        ((ShopItem) Item.getItem(Block.sand.blockID)).setSoldPriceForAllSubs(0.5D);
        ((ShopItem) Item.getItem(Block.cobblestone.blockID)).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.getItem(Block.stone.blockID)).setSoldPriceForAllSubs(1.0D);
        ((ShopItem) Item.getItem(Block.cobblestoneWall.blockID)).setSoldPriceForAllSubs(1.5D);
        ((ShopItem) Item.getItem(Block.wood.blockID)).setSoldPriceForAllSubs(1.0D);

        ((ShopItem) Item.horseArmorMithril).setSoldPriceForAllSubs(250.0D);
        ((ShopItem) Item.horseArmorAdamantium).setSoldPriceForAllSubs(500.0D);
    }
}