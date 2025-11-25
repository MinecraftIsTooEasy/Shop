package cn.wensc.mitemod.shop.inventory;

import com.google.common.collect.ImmutableList;

public enum ShopCategory {
    PURCHASABLE,
    SELLABLE,
    BOTH,
    ;
    static final ImmutableList<ShopCategory> VALUES = ImmutableList.copyOf(values());

    ShopCategory next() {
        return VALUES.get((this.ordinal() + 1) % VALUES.size());
    }
}
