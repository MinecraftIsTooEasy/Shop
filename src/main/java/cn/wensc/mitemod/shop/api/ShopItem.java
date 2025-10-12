package cn.wensc.mitemod.shop.api;

import net.minecraft.Item;
import net.minecraft.ItemStack;

public interface ShopItem {
    double getSoldPrice(int subtype);

    double getBuyPrice(int subtype);

    void setSoldPrice(int subtype, double soldPrice);

    void setBuyPrice(int subtype, double soldPrice);

    ShopItem setBuyPriceForAllSubs(double price);

    ShopItem setSoldPriceForAllSubs(double price);

    void clearPrice();

    static void setSoldPrice(Item item, int subtype, double soldPrice) {
        ((ShopItem) item).setSoldPrice(subtype, soldPrice);
    }

    static void setBuyPrice(Item item, int subtype, double buyPrice) {
        ((ShopItem) item).setBuyPrice(subtype, buyPrice);
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
}
