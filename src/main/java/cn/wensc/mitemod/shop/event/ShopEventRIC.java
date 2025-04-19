package cn.wensc.mitemod.shop.event;

import moddedmite.rustedironcore.api.event.Handlers;

public class ShopEventRIC extends Handlers {
    public static void registerEvents() {
        PropertiesRegistry.register(new ShopPropertyRegistry());
    }
}
