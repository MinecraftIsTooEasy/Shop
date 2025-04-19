package cn.wensc.mitemod.shop.network.packets.C2S;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

public class C2SOpenShop implements Packet {
    public C2SOpenShop() {
    }

    public C2SOpenShop(PacketByteBuf packetByteBuf) {

    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {

    }

    @Override
    public void apply(EntityPlayer entityPlayer) {
//        if (Configs.wenscConfig.isCloseShop.ConfigValue) return;
        ((ShopPlayer) entityPlayer).displayGUIShop();
//        entityPlayer.triggerAchievement(Achievements.openShop);
    }

    @Override
    public ResourceLocation getChannel() {
        return ShopNetwork.OpenShop;
    }
}
