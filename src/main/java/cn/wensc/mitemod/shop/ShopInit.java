package cn.wensc.mitemod.shop;

import cn.wensc.mitemod.shop.config.ShopConfigML;
import cn.wensc.mitemod.shop.event.ShopEvent;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import fi.dy.masa.malilib.config.ConfigManager;
import moddedmite.rustedironcore.api.util.LogUtil;
import net.fabricmc.api.ModInitializer;
import net.minecraft.KeyBinding;
import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.Logger;

public class ShopInit implements ModInitializer {
    public static String ShopModID = "shop";
    public static KeyBinding keyBindShop;
    public static final Logger LOGGER = LogUtil.getLogger();

    @Override
    public void onInitialize() {
        ShopEvent.register();
        ShopNetwork.init();
        ModResourceManager.addResourcePackDomain(ShopModID);
        ShopConfigML.getInstance().load();
        ConfigManager.getInstance().registerConfig(ShopConfigML.getInstance());
    }
}
