package cn.wensc.mitemod.shop.api;

import cn.wensc.mitemod.shop.util.PriceItem;
import net.minecraft.ItemStack;

public interface ShopStack {
    PriceItem getPrice();

    void setPrice(double soldPrice, double buyPrice);

    static PriceItem getPrice(ItemStack itemStack) {
        return ((ShopStack) itemStack).getPrice();
    }
}
