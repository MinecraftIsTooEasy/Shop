package cn.wensc.mitemod.shop.util;

import cn.wensc.mitemod.shop.api.ShopStack;
import cn.wensc.mitemod.shop.inventory.ShopCategory;
import cn.wensc.mitemod.shop.inventory.ShopOrder;
import net.minecraft.ItemStack;

import java.util.Comparator;
import java.util.function.Predicate;

public class PriceStackView {
    public static final Predicate<ItemStack> PURCHASABLE = x -> ShopStack.getPrice(x).buyPrice() > 0;
    public static final Predicate<ItemStack> SELLABLE = x -> ShopStack.getPrice(x).soldPrice() > 0;
    public static final Predicate<ItemStack> EMPTY = x -> true;

    public static Predicate<ItemStack> filter(ShopCategory category) {
        return switch (category) {
            case PURCHASABLE -> PriceStackView.PURCHASABLE;
            case SELLABLE -> PriceStackView.SELLABLE;
            case BOTH -> EMPTY;
        };
    }

    public static Comparator<ItemStack> sort(ShopCategory category, ShopOrder order) {
        Comparator<ItemStack> comparator = switch (category) {
            case PURCHASABLE, BOTH -> Comparator.comparingDouble(x -> ShopStack.getPrice(x).buyPrice());
            case SELLABLE -> Comparator.comparingDouble(x -> ShopStack.getPrice(x).soldPrice());
        };
        return switch (order) {
            case INCREASING -> comparator;
            case DECREASING -> comparator.reversed();
        };
    }
}
