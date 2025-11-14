package cn.wensc.mitemod.shop.client.screen;

import net.minecraft.GuiButton;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiPaginationButton extends GuiButton {
    private final boolean nextPage;

    public GuiPaginationButton(int var1, int var2, int var3, boolean var4) {
        super(var1, var2, var3, 12, 17, "");
        this.nextPage = var4;
    }

    public void drawButton(Minecraft var1, int var2, int var3) {
        if (this.drawButton) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hover = (var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height);
            EnumLegacyIcon icon = this.nextPage ? EnumLegacyIcon.RIGHT_PAGE : EnumLegacyIcon.LEFT_PAGE;
            var1.getTextureManager().bindTexture(icon.getTexture());
            icon.renderAt(this.xPosition, this.yPosition, 0, this.enabled, hover);
        }
    }
}
