package cn.wensc.mitemod.shop.util;

import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.Item;

public record EigenItemStack(Item item, int subtype) {
    public void write(PacketByteBuf buf) {
        buf.writeInt(item.itemID);
        buf.writeInt(subtype);
    }

    public static EigenItemStack read(PacketByteBuf buf) {
        return new EigenItemStack(Item.getItem(buf.readInt()), buf.readInt());
    }
}
