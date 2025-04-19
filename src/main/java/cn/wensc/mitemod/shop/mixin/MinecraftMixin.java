package cn.wensc.mitemod.shop.mixin;

import cn.wensc.mitemod.shop.ShopInit;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import cn.wensc.mitemod.shop.network.packets.C2S.C2SOpenShop;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At("TAIL"))
    private void onKeyEvent(CallbackInfo ci) {
        if (ShopInit.keyBindShop.isPressed())
            ShopNetwork.sendToServer(new C2SOpenShop());
    }
}
