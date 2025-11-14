package cn.wensc.mitemod.shop.mixin;

import cn.wensc.mitemod.shop.inventory.SlotShop;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.Container;
import net.minecraft.EntityPlayer;
import net.minecraft.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Container.class)
public class ContainerMixin {
    @WrapOperation(method = "slotClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/Slot;canTakeStack(Lnet/minecraft/EntityPlayer;)Z", ordinal = 0))
    private boolean allowShop(Slot instance, EntityPlayer par1EntityPlayer, Operation<Boolean> original) {
        if (instance instanceof SlotShop) return true;
        return original.call(instance, par1EntityPlayer);
    }
}
