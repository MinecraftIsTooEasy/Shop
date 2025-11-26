package cn.wensc.mitemod.shop.event.listener;

import cn.wensc.mitemod.shop.ShopInit;
import cn.wensc.mitemod.shop.client.screen.GuiShop;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import cn.wensc.mitemod.shop.network.packets.C2S.C2SOpenShop;
import moddedmite.rustedironcore.api.event.listener.ITickListener;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;

public class TickListener implements ITickListener {
    @Override
    public void onClientTick(Minecraft client) {
        if (ShopInit.keyBindShop.isPressed()) handleOpenShop(client);
    }

    private void handleOpenShop(Minecraft client) {
        GuiScreen screen = client.currentScreen;
        if (screen instanceof GuiShop) {
            client.displayGuiScreen(null);
            return;
        }
        ShopNetwork.sendToServer(new C2SOpenShop());
    }
}
