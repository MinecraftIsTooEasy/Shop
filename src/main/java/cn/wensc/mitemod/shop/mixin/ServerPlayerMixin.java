package cn.wensc.mitemod.shop.mixin;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import cn.wensc.mitemod.shop.network.packets.S2C.S2COpenWindow;
import cn.wensc.mitemod.shop.network.packets.S2C.S2CSyncShopInfo;
import cn.wensc.mitemod.shop.screen.ContainerShop;
import cn.wensc.mitemod.shop.util.PriceStacks;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends EntityPlayer implements ICrafting, ShopPlayer {
    @Shadow
    private int currentWindowId;

    public ServerPlayerMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Override
    public void displayGUIShop() {
        incrementWindowID();
        ShopNetwork.sendToClient(this.getAsEntityPlayerMP(), new S2COpenWindow(this.currentWindowId, S2COpenWindow.Shop, "shop", 45, false));
        this.openContainer = new ContainerShop(this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
        ShopNetwork.sendToClient(this.getAsEntityPlayerMP(), new S2CSyncShopInfo(PriceStacks.getStackListSize(), ShopPlayer.getMoneyManager(this).getMoney()));
    }

    @Shadow
    protected abstract void incrementWindowID();
}
