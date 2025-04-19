package cn.wensc.mitemod.shop.network.packets.S2C;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;

public class S2COpenWindow implements Packet {
    public int windowId;
    public int inventoryType;
    public String windowTitle;
    public int slotsCount;
    public int x;
    public int y;
    public int z;
    public boolean has_set_coords;
    public boolean useProvidedWindowTitle;

    public S2COpenWindow(int par1, int par2, String par3Str, int par4, boolean par5) {
        if (par3Str == null) {
            par3Str = "";
        }

        this.windowId = par1;
        this.inventoryType = par2;
        this.windowTitle = par3Str;
        this.slotsCount = par4;
        this.useProvidedWindowTitle = par5;
    }

    public S2COpenWindow(PacketByteBuf packetByteBuf) {
        this.windowId = packetByteBuf.readByte() & 255;
        this.inventoryType = packetByteBuf.readByte() & 255;
        this.windowTitle = packetByteBuf.readString();
        this.slotsCount = packetByteBuf.readByte() & 255;
        this.useProvidedWindowTitle = packetByteBuf.readBoolean();

        if (this.hasCoords()) {
            this.x = packetByteBuf.readInt();
            this.y = packetByteBuf.readInt();
            this.z = packetByteBuf.readInt();
        }
    }

    public S2COpenWindow setCoords(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.has_set_coords = true;
        return this;
    }

    public boolean hasCoords() {
        return this.inventoryType == 0 || this.inventoryType == 1 || this.inventoryType == 2 || this.inventoryType == 3 || this.inventoryType == 4 || this.inventoryType == 5 || this.inventoryType == 7 || this.inventoryType == 8 || this.inventoryType == 9 || this.inventoryType == 10;
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeByte(this.windowId & 255);
        packetByteBuf.writeByte(this.inventoryType & 255);
        packetByteBuf.writeString(this.windowTitle);
        packetByteBuf.writeByte(this.slotsCount & 255);
        packetByteBuf.writeBoolean(this.useProvidedWindowTitle);

        if (this.hasCoords()) {
            if (!this.has_set_coords) {
                Minecraft.setErrorMessage("GAHigher$S2COpenWindow: coords not set for type " + this.inventoryType);
            }

            packetByteBuf.writeInt(this.x);
            packetByteBuf.writeInt(this.y);
            packetByteBuf.writeInt(this.z);
        }
    }

    @Override
    public void apply(EntityPlayer player) {
        switch (this.inventoryType) {
            case Shop -> {
                ((ShopPlayer) player).displayGUIShop();
                player.openContainer.windowId = this.windowId;
            }
        }
    }

    @Override
    public ResourceLocation getChannel() {
        return ShopNetwork.OpenWindow;
    }

    public static final int Shop = 16;
}
