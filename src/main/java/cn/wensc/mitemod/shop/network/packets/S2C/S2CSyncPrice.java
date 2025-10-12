package cn.wensc.mitemod.shop.network.packets.S2C;

import cn.wensc.mitemod.shop.network.ShopNetwork;
import cn.wensc.mitemod.shop.util.EigenItemStack;
import cn.wensc.mitemod.shop.util.PriceItem;
import cn.wensc.mitemod.shop.util.PriceStacks;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

public class S2CSyncPrice implements Packet {
    private final Map<EigenItemStack, PriceItem> map;

    public S2CSyncPrice(Map<EigenItemStack, PriceItem> map) {
        this.map = map;
    }

    public S2CSyncPrice(PacketByteBuf packetByteBuf) {
        this(makeMap(packetByteBuf));
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        Map<EigenItemStack, PriceItem> local = this.map;
        packetByteBuf.writeInt(local.size());
        local.forEach((eigenItemStack, priceItem) -> {
            eigenItemStack.write(packetByteBuf);
            priceItem.write(packetByteBuf);
        });
    }

    @Override
    public void apply(EntityPlayer entityPlayer) {
        PriceStacks.override(this.map);
    }

    @Override
    public ResourceLocation getChannel() {
        return ShopNetwork.SyncPrice;
    }

    private static Map<EigenItemStack, PriceItem> makeMap(PacketByteBuf packetByteBuf) {
        int size = packetByteBuf.readInt();
        LinkedHashMap<EigenItemStack, PriceItem> map = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(EigenItemStack.read(packetByteBuf), PriceItem.read(packetByteBuf));
        }
        return map;
    }
}
