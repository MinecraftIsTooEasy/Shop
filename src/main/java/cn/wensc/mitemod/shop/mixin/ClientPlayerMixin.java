package cn.wensc.mitemod.shop.mixin;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.screen.GuiShop;
import net.minecraft.ClientPlayer;
import net.minecraft.EntityPlayer;
import net.minecraft.Minecraft;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayer.class)
public abstract class ClientPlayerMixin extends EntityPlayer implements ShopPlayer {
    @Shadow protected Minecraft mc;

    public ClientPlayerMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Override
    public void displayGUIShop() {
        this.mc.displayGuiScreen(new GuiShop(this));
    }
}
