package cn.wensc.mitemod.shop.mixin;

import cn.wensc.mitemod.shop.ShopInit;
import net.minecraft.GameSettings;
import net.minecraft.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(GameSettings.class)
public class GameSettingMixin {
    @Shadow public KeyBinding[] keyBindings;


    @Inject(method = "initKeybindings", at = @At("TAIL"))
    public void registerWailaKeybindings(CallbackInfo ci) {
        List<KeyBinding> list = new ArrayList<>();
        ShopInit.keyBindShop = new KeyBinding("key.openShop", Keyboard.KEY_V);
        list.add(ShopInit.keyBindShop);
        this.keyBindings = ArrayUtils.addAll(this.keyBindings, list.toArray(KeyBinding[]::new));
    }
}
