package cn.wensc.mitemod.shop.api;

public interface ShopItem {
    double getSoldPrice(int subtype);

    double getBuyPrice(int subtype);

    void setSoldPrice(int subtype, double soldPrice);

    void setBuyPrice(int subtype, double soldPrice);

    ShopItem setBuyPriceForAllSubs(double price);

    ShopItem setSoldPriceForAllSubs(double price);
}
