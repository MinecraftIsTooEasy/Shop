package cn.wensc.mitemod.shop.inventory;

import com.google.common.collect.ImmutableList;

public enum ShopOrder {
    INCREASING,
    DECREASING,
    ;
    static final ImmutableList<ShopOrder> VALUES = ImmutableList.copyOf(values());

    ShopOrder next() {
        return VALUES.get((this.ordinal() + 1) % VALUES.size());
    }
}
