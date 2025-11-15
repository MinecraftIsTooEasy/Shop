package cn.wensc.mitemod.shop.network.packets.C2S;

import cn.wensc.mitemod.shop.inventory.ContainerShop;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.Container;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

public record C2SContainerButtonClick(int containerId, int buttonId) implements Packet {
    public C2SContainerButtonClick(PacketByteBuf packetByteBuf) {
        this(packetByteBuf.readInt(), packetByteBuf.readInt());
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(this.containerId);
        packetByteBuf.writeInt(this.buttonId);
    }

    @Override
    public void apply(EntityPlayer player) {
        Container container = player.openContainer;
        if (container.windowId == this.containerId && container instanceof ContainerShop containerShop) {
            boolean success = containerShop.clickMenuButton(player, this.buttonId);
            if (success) containerShop.broadcastChanges();
        }
    }

    @Override
    public ResourceLocation getChannel() {
        return ShopNetwork.ContainerButtonClick;
    }
}
