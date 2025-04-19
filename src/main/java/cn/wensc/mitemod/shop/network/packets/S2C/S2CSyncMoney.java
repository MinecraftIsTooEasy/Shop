package cn.wensc.mitemod.shop.network.packets.S2C;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

public class S2CSyncMoney implements Packet {
    private final double money;

    public S2CSyncMoney(double money) {
        this.money = money;
    }

    public S2CSyncMoney(PacketByteBuf packetByteBuf) {
        this(packetByteBuf.readDouble());
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeDouble(this.money);
    }

    @Override
    public void apply(EntityPlayer entityPlayer) {
        ShopPlayer.getMoneyManager(entityPlayer).setMoney(this.money);
    }

    @Override
    public ResourceLocation getChannel() {
        return ShopNetwork.SyncMoney;
    }
}
