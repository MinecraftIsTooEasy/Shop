package cn.wensc.mitemod.shop.api;

import cn.wensc.mitemod.shop.config.ShopConfigs;
import cn.wensc.mitemod.shop.util.PriceItem;
import cn.wensc.mitemod.shop.util.PriceStackStorage;
import net.minecraft.Item;
import net.minecraft.ItemStack;

public interface ShopApi {
    /**
     * Hot reloading
     */
    static void setPrice(ItemStack itemStack, double soldPrice, double buyPrice) {
        PriceStackStorage.handleNewPrice(itemStack, soldPrice, buyPrice);
    }

    /**
     * Only used in shop gui
     */
    static PriceItem getCustomPrice(ItemStack itemStack) {
        return ShopStack.getPrice(itemStack);
    }

    static double getSoldPrice(Item item, int subtype) {
        return ((ShopItem) item).getSoldPrice(subtype);
    }

    static double getBuyPrice(Item item, int subtype) {
        return ((ShopItem) item).getBuyPrice(subtype);
    }

    static double getSoldPrice(ItemStack itemStack) {
        return getSoldPrice(itemStack.getItem(), itemStack.getItemSubtype());
    }

    static double getBuyPrice(ItemStack itemStack) {
        return getBuyPrice(itemStack.getItem(), itemStack.getItemSubtype());
    }

    static void saveConfigToFile() {
        ShopConfigs.saveToFile(ShopConfigs.ShopConfigFile);
    }
}
