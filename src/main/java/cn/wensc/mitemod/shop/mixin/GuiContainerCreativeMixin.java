package cn.wensc.mitemod.shop.mixin;

import cn.wensc.mitemod.shop.api.ShopStack;
import cn.wensc.mitemod.shop.config.ShopConfigML;
import cn.wensc.mitemod.shop.screen.GuiEditPrice;
import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainerCreative.class)
public abstract class GuiContainerCreativeMixin extends InventoryEffectRenderer {
    public GuiContainerCreativeMixin(Container par1Container) {
        super(par1Container);
    }

    @Inject(method = "handleMouseClick", at = @At("TAIL"))
    private void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType, CallbackInfo ci) {
        if (ShopConfigML.EditMode.getBooleanValue() && slotIn != null) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LMENU/* left Alt */) && clickedButton == 1) {
                InventoryPlayer inventory = this.mc.thePlayer.inventory;
                if (slotIn.getHasStack()) {
                    ItemStack stack = slotIn.getStack().copy();
                    this.mc.displayGuiScreen(new GuiEditPrice(ReflectHelper.dyCast(this), stack));
                }
            }
        }
    }
}
