package cn.wensc.mitemod.shop.network.packets.S2C;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.inventory.ContainerShop;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

public record S2CSyncShopInfo(int shopSize, double money) implements Packet {
    public S2CSyncShopInfo(PacketByteBuf packetByteBuf) {
        this(packetByteBuf.readInt(), packetByteBuf.readDouble());
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(this.shopSize);
        packetByteBuf.writeDouble(this.money);
    }

    @Override
    public void apply(EntityPlayer entityPlayer) {
        if (entityPlayer.openContainer instanceof ContainerShop containerShop) {
            containerShop.setShopSize(this.shopSize);
        }
        ShopPlayer.getMoneyManager(entityPlayer).setMoney(this.money);
    }

    @Override
    public ResourceLocation getChannel() {
        return ShopNetwork.SyncShopInfo;
    }
}
