package cn.wensc.mitemod.shop.util;

import moddedmite.rustedironcore.network.PacketByteBuf;

public record PriceItem(double soldPrice, double buyPrice) {
    public static final PriceItem EMPTY = new PriceItem(0, 0);

    public void write(PacketByteBuf buf) {
        buf.writeDouble(soldPrice);
        buf.writeDouble(buyPrice);
    }

    public static PriceItem read(PacketByteBuf buf) {
        return new PriceItem(buf.readDouble(), buf.readDouble());
    }
}
