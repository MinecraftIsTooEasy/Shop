package cn.wensc.mitemod.shop.client.screen;

import net.minecraft.GuiButton;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiSwitchViewButton extends GuiButton {
    public GuiSwitchViewButton(int var1, int var2, int var3) {
        super(var1, var2, var3, 22, 22, "");
    }

    @Override
    public void drawButton(Minecraft var1, int var2, int var3) {
        if (this.drawButton) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hover = (var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height);
            EnumIcon icon = hover ? EnumIcon.BUTTON_BACKGROUND_HOVER : EnumIcon.BUTTON_BACKGROUND_NORMAL;
            var1.getTextureManager().bindTexture(icon.getTexture());
            icon.renderAt(this.xPosition, this.yPosition, 0, false, false);//dummy
            EnumIcon.SWITCH_VIEW.renderAt(this.xPosition, this.yPosition, 0, false, false);
        }
    }
}
