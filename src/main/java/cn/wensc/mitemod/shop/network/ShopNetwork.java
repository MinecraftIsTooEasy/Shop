package cn.wensc.mitemod.shop.network;

import cn.wensc.mitemod.shop.ShopInit;
import cn.wensc.mitemod.shop.network.packets.C2S.C2SContainerButtonClick;
import cn.wensc.mitemod.shop.network.packets.C2S.C2SOpenShop;
import cn.wensc.mitemod.shop.network.packets.S2C.S2COpenWindow;
import cn.wensc.mitemod.shop.network.packets.S2C.S2CSyncMoney;
import cn.wensc.mitemod.shop.network.packets.S2C.S2CSyncPrice;
import cn.wensc.mitemod.shop.network.packets.S2C.S2CSyncShopInfo;
import moddedmite.rustedironcore.network.Network;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketReader;
import net.minecraft.ResourceLocation;
import net.minecraft.ServerPlayer;
import net.xiaoyu233.fml.FishModLoader;

public class ShopNetwork {
    public static final ResourceLocation OpenWindow = new ResourceLocation(ShopInit.ShopModID, "OpenWindow");
    public static final ResourceLocation OpenShop = new ResourceLocation(ShopInit.ShopModID, "OpenShop");
    public static final ResourceLocation SyncShopInfo = new ResourceLocation(ShopInit.ShopModID, "SyncShopInfo");
    public static final ResourceLocation SyncMoney = new ResourceLocation(ShopInit.ShopModID, "SyncMoney");
    public static final ResourceLocation SyncPrice = new ResourceLocation(ShopInit.ShopModID, "SyncPrice");
    public static final ResourceLocation ContainerButtonClick = new ResourceLocation(ShopInit.ShopModID, "ContainerButtonClick");

    public static void sendToClient(ServerPlayer player, Packet packet) {
        Network.sendToClient(player, packet);
    }

    public static void sendToServer(Packet packet) {
        Network.sendToServer(packet);
    }

    public static void init() {
        if (!FishModLoader.isServer()) {
            initClient();
        }
        initServer();
    }

    private static void initClient() {
        PacketReader.registerClientPacketReader(ShopNetwork.OpenWindow, S2COpenWindow::new);
        PacketReader.registerClientPacketReader(ShopNetwork.SyncShopInfo, S2CSyncShopInfo::new);
        PacketReader.registerClientPacketReader(ShopNetwork.SyncMoney, S2CSyncMoney::new);
        PacketReader.registerClientPacketReader(ShopNetwork.SyncPrice, S2CSyncPrice::new);
    }

    private static void initServer() {
        PacketReader.registerServerPacketReader(ShopNetwork.OpenShop, packetByteBuf -> new C2SOpenShop());
        PacketReader.registerServerPacketReader(ShopNetwork.ContainerButtonClick, C2SContainerButtonClick::new);
    }
}
