package cn.wensc.mitemod.shop.client.screen;

import net.minecraft.GuiButton;

public class ShopButtonBase extends GuiButton {
    protected boolean hover;

    public ShopButtonBase(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }

    public boolean isHover() {
        return this.hover;
    }
}
