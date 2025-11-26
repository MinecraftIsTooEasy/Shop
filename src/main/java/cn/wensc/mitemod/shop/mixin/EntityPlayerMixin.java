package cn.wensc.mitemod.shop.mixin;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.manager.MoneyManager;
import net.minecraft.EntityPlayer;
import net.minecraft.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin implements ShopPlayer {

    @Unique private MoneyManager moneyManager = new MoneyManager((EntityPlayer) (Object) this);

    @Override
    public MoneyManager getMoneyManager() {
        return this.moneyManager;
    }

    @Inject(method = "clonePlayer(Lnet/minecraft/EntityPlayer;Z)V", at = @At("RETURN"))
    public void clonePlayerInject(EntityPlayer oldPlayer, boolean par2, CallbackInfo callbackInfo) {
        this.moneyManager.clone(oldPlayer);
    }

    @Inject(method = "readEntityFromNBT(Lnet/minecraft/NBTTagCompound;)V", at = @At("RETURN"))
    public void injectReadNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
        this.moneyManager.read(par1NBTTagCompound);
    }


    @Inject(method = "writeEntityToNBT(Lnet/minecraft/NBTTagCompound;)V", at = @At("RETURN"))
    public void injectWriteNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo callback) {
        this.moneyManager.write(par1NBTTagCompound);
    }
}
