package cn.wensc.mitemod.shop.event;

import cn.wensc.mitemod.shop.event.listener.PlayerEventListener;
import cn.wensc.mitemod.shop.event.listener.TickListener;
import moddedmite.rustedironcore.api.event.Handlers;
import moddedmite.rustedironcore.api.event.listener.IWorldLoadListener;
import net.minecraft.WorldClient;

public class ShopEventRIC {
    public static void register() {
        Handlers.Tick.register(new TickListener());
        Handlers.PlayerEvent.register(new PlayerEventListener());
        Handlers.WorldLoad.register(new IWorldLoadListener() {
            @Override
            public void onWorldLoad(WorldClient world) {
                ShopPropertyRegistry.run();
            }
        });
    }
}
