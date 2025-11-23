package cn.wensc.mitemod.shop.client;

import cn.wensc.mitemod.shop.network.packets.C2S.C2SContainerButtonClick;
import moddedmite.rustedironcore.network.Network;

public class MultiPlayerGameMode {
    /**
     * Network action
     */
    public static void handleInventoryButtonClick(int containerId, int buttonId) {
        Network.sendToServer(new C2SContainerButtonClick(containerId, buttonId));
    }
}
