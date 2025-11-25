package cn.wensc.mitemod.shop.localization;

import net.minecraft.I18n;

public interface LocalizationEnum {
    String getKey();

    default String translate() {
        return I18n.getString(this.getKey());
    }

    default String translate(Object... params) {
        return I18n.getStringParams(this.getKey(), params);
    }
}
