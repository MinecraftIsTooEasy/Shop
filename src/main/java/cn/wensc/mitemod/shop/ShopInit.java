package cn.wensc.mitemod.shop;

import cn.wensc.mitemod.shop.event.ShopEventFML;
import cn.wensc.mitemod.shop.event.ShopEventRIC;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import net.fabricmc.api.ModInitializer;
import net.minecraft.KeyBinding;
import net.xiaoyu233.fml.ModResourceManager;
import net.xiaoyu233.fml.reload.event.MITEEvents;

public class ShopInit implements ModInitializer {
    public static String ShopModID = "shop";
    public static KeyBinding keyBindShop;

    @Override
    public void onInitialize() {
        ShopEventRIC.registerEvents();
        MITEEvents.MITE_EVENT_BUS.register(new ShopEventFML());
        ShopNetwork.init();
        ModResourceManager.addResourcePackDomain(ShopModID);
    }
}
