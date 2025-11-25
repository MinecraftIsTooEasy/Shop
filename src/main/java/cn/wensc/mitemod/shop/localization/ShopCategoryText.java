package cn.wensc.mitemod.shop.localization;

import cn.wensc.mitemod.shop.inventory.ShopCategory;

public enum ShopCategoryText implements LocalizationEnum {
    PURCHASABLE,
    SELLABLE,
    BOTH;

    public static ShopCategoryText from(ShopCategory category) {
        return switch (category) {
            case PURCHASABLE -> PURCHASABLE;
            case SELLABLE -> SELLABLE;
            case BOTH -> BOTH;
        };
    }

    @Override
    public String getKey() {
        return "shop.category." + this.name().toLowerCase();
    }
}
