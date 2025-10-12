package cn.wensc.mitemod.shop.api;

import cn.wensc.mitemod.shop.util.PriceItem;
import net.minecraft.ItemStack;

/**
 * The price might differ from those price maps in the corresponding items
 */
public interface ShopStack {
    PriceItem getPrice();

    void setPrice(double soldPrice, double buyPrice);

    static PriceItem getPrice(ItemStack itemStack) {
        return ((ShopStack) itemStack).getPrice();
    }

    static void setPrice(ItemStack stack, double soldPrice, double buyPrice) {
        ((ShopStack) stack).setPrice(soldPrice, buyPrice);
    }

    static void setPrice(ItemStack stack, PriceItem price) {
        setPrice(stack, price.soldPrice(), price.buyPrice());
    }
}
