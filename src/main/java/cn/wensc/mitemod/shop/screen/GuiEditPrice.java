package cn.wensc.mitemod.shop.screen;

import cn.wensc.mitemod.shop.api.ShopApi;
import cn.wensc.mitemod.shop.api.ShopStack;
import net.minecraft.*;
import org.lwjgl.input.Keyboard;

public class GuiEditPrice extends GuiScreen {
    private ItemStack editStack;
    private static RenderItem itemRenderer = new RenderItem();
    private Minecraft client = Minecraft.getMinecraft();
    private GuiTextField theGuiTextField;
    private GuiScreen parentGuiScreen;

    public GuiEditPrice(GuiScreen parentGuiScreen, ItemStack editStack) {
        this.parentGuiScreen = parentGuiScreen;
        this.editStack = editStack;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.getString("gui.done")));
        this.theGuiTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
        this.theGuiTextField.setFocused(true);
        this.theGuiTextField.setText("售价");
        itemRenderer.renderItemOverlayIntoGUI(client.fontRenderer, client.getTextureManager(), this.editStack, this.width / 2, this.height / 2);
    }

    public void updateScreen() {
        this.theGuiTextField.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                ShopApi.setPrice(this.editStack, this.parseSoldPrice(), 0);
                System.out.println(((ShopStack) this.editStack).getPrice());
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
        }
    }

    protected void keyTyped(char par1, int par2) {
        this.theGuiTextField.textboxKeyTyped(par1, par2);
        ((GuiButton) this.buttonList.get(0)).enabled = this.theGuiTextField.getText().trim().length() > 0;

        if (par2 == 28 || par2 == 156) {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.theGuiTextField.mouseClicked(par1, par2, par3);
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.theGuiTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    private double parseSoldPrice() {
        try {
            return Double.parseDouble(this.theGuiTextField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
