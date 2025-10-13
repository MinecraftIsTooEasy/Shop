package cn.wensc.mitemod.shop.api;

import net.minecraft.Item;

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
}
