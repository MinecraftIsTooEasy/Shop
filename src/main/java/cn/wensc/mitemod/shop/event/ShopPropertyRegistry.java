package cn.wensc.mitemod.shop.event;

import cn.wensc.mitemod.shop.ShopConfigs;
import cn.wensc.mitemod.shop.registry.ShopRegistryImpl;
import cn.wensc.mitemod.shop.registry.ShopVanillaPlugin;
import cn.wensc.mitemod.shop.api.ShopPlugin;
import net.xiaoyu233.fml.FishModLoader;

public class ShopPropertyRegistry implements Runnable {
    @Override
    public void run() {
        ShopRegistryImpl gaRegistry = new ShopRegistryImpl();
        new ShopVanillaPlugin().register(gaRegistry);
        FishModLoader.getEntrypointContainers("shop", ShopPlugin.class)
                .forEach(container -> container.getEntrypoint().register(gaRegistry));

        postRegisterPrice();
    }

    public static void postRegisterPrice() {
        ShopConfigs.loadOrCreateFile(ShopConfigs.shopConfigFilePath, ShopConfigs::readShopConfigFromFile, ShopConfigs::generateShopConfigFile);
    }
}
