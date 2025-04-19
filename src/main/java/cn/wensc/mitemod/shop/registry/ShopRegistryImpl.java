package cn.wensc.mitemod.shop.registry;

import cn.wensc.mitemod.shop.api.ShopItem;
import cn.wensc.mitemod.shop.api.ShopRegistry;
import net.minecraft.Item;

public class ShopRegistryImpl implements ShopRegistry {
    @Override
    public void registerSoldPrice(Item item, double soldPrice) {
        ((ShopItem) item).setSoldPriceForAllSubs(soldPrice);
    }

    @Override
    public void registerBuyPrice(Item item, double buyPrice) {
        ((ShopItem) item).setBuyPriceForAllSubs(buyPrice);
    }

    @Override
    public void registerSoldPrice(Item item, int sub, double soldPrice) {
        ((ShopItem) item).setSoldPrice(sub, soldPrice);
    }

    @Override
    public void registerBuyPrice(Item item, int sub, double buyPrice) {
        ((ShopItem) item).setBuyPrice(sub, buyPrice);
    }
}
