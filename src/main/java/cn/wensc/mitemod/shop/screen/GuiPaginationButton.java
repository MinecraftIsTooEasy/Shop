package cn.wensc.mitemod.shop.screen;

import cn.wensc.mitemod.shop.ShopInit;
import net.minecraft.GuiButton;
import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiPaginationButton extends GuiButton {
    private static final ResourceLocation resourceLocation = new ResourceLocation(ShopInit.ShopModID, "textures/gui/container/gui_shop.png");

    private final boolean nextPage;

    public GuiPaginationButton(int var1, int var2, int var3, boolean var4) {
        super(var1, var2, var3, 12, 19, "");
        this.nextPage = var4;
    }

    public void drawButton(Minecraft var1, int var2, int var3) {
        if (this.drawButton) {
            var1.getTextureManager().bindTexture(resourceLocation);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var4 = (var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height);
            int var5 = 0;
            int var6 = 176;
            if (!this.enabled) {
                var6 += this.width * 2;
            } else if (var4) {
                var6 += this.width;
            }
            if (!this.nextPage)
                var5 += this.height;
            drawTexturedModalRect(this.xPosition, this.yPosition, var6, var5, this.width, this.height);
        }
    }
}
