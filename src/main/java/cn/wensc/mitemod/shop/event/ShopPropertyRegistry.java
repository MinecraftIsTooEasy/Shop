package cn.wensc.mitemod.shop.event;

import cn.wensc.mitemod.shop.api.ShopPlugin;
import cn.wensc.mitemod.shop.config.ShopConfigs;
import cn.wensc.mitemod.shop.registry.InternalPlugin;
import cn.wensc.mitemod.shop.registry.ShopRegistryImpl;
import net.xiaoyu233.fml.FishModLoader;

public class ShopPropertyRegistry {
    private static boolean initialized = false;

    public static void run() {
        if (initialized) return;
        ShopRegistryImpl registry = new ShopRegistryImpl();
        new InternalPlugin().register(registry);
        FishModLoader.getEntrypointContainers("shop", ShopPlugin.class)
                .forEach(container -> container.getEntrypoint().register(registry));

        postRegisterPrice();
        initialized = true;
    }

    public static void postRegisterPrice() {
        ShopConfigs.loadOrCreate();
    }
}
