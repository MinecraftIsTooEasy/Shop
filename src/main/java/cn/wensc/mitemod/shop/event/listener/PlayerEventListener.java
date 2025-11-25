package cn.wensc.mitemod.shop.event.listener;

import cn.wensc.mitemod.shop.config.ShopConfigML;
import cn.wensc.mitemod.shop.util.PriceStackStorage;
import moddedmite.rustedironcore.api.event.events.PlayerLoggedInEvent;
import moddedmite.rustedironcore.api.event.listener.IPlayerEventListener;
import net.minecraft.server.MinecraftServer;

public class PlayerEventListener implements IPlayerEventListener {
    @Override
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (ShopConfigML.SyncPrice.getBooleanValue() && MinecraftServer.getServer().isDedicatedServer()) {
            PriceStackStorage.sync(event.player());
        }
    }
}
