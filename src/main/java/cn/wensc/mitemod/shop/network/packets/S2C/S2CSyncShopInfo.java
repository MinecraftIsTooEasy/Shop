package cn.wensc.mitemod.shop.network.packets.S2C;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import cn.wensc.mitemod.shop.util.PriceStacks;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

public class S2CSyncShopInfo implements Packet {
    private final int shopSize;

    private final double money;

    public S2CSyncShopInfo(int shopSize, double money) {
        this.shopSize = shopSize;
        this.money = money;
    }

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
        PriceStacks.setShopSize(shopSize);
        ShopPlayer.getMoneyManager(entityPlayer).setMoney(this.money);
    }

    @Override
    public ResourceLocation getChannel() {
        return ShopNetwork.SyncShopInfo;
    }
}
