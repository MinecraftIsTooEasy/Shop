package cn.wensc.mitemod.shop.localization;

import cn.wensc.mitemod.shop.inventory.ShopOrder;

public enum ShopOrderText implements LocalizationEnum {
    INCREASING,
    DECREASING,
    ;

    public static LocalizationEnum fromOrder(ShopOrder order) {
        return switch (order) {
            case INCREASING -> INCREASING;
            case DECREASING -> DECREASING;
        };
    }

    @Override
    public String getKey() {
        return "shop.order." + this.name().toLowerCase();
    }
}
