package cn.wensc.mitemod.shop.network.packets.C2S;

import cn.wensc.mitemod.shop.inventory.ContainerShop;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.Container;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

public class C2SShopPageIndex implements Packet {
    private final int index;

    public C2SShopPageIndex(int index) {
        this.index = index;
    }

    public C2SShopPageIndex(PacketByteBuf packetByteBuf) {
        this(packetByteBuf.readInt());
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(this.index);
    }

    @Override
    public void apply(EntityPlayer entityPlayer) {
        Container container = entityPlayer.openContainer;
        if (container instanceof ContainerShop) {
            ((ContainerShop) container).inventory.updateContent(this.index);
        }
    }

    @Override
    public ResourceLocation getChannel() {
        return ShopNetwork.ShopPageIndex;
    }
}
