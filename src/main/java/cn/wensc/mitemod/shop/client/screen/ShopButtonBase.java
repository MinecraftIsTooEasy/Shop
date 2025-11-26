package cn.wensc.mitemod.shop.client.screen;

import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import net.minecraft.GuiButton;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class ShopButtonBase extends GuiButton {
    private final IGuiIcon icon;
    protected boolean hover;

    public ShopButtonBase(int id, int x, int y, IGuiIcon icon) {
        super(id, x, y, 22, 22, "");
        this.icon = icon;
    }

    @Override
    public void drawButton(Minecraft var1, int var2, int var3) {
        if (this.drawButton) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hover = (var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height);
            this.hover = hover;
            EnumIcon icon = hover ? EnumIcon.BUTTON_BACKGROUND_HOVER : EnumIcon.BUTTON_BACKGROUND_NORMAL;
            var1.getTextureManager().bindTexture(icon.getTexture());
            icon.renderAt(this.xPosition, this.yPosition, 0, false, false);//dummy
            this.icon.renderAt(this.xPosition, this.yPosition, 0, false, false);
        }
    }

    public boolean isHover() {
        return this.hover;
    }
}
