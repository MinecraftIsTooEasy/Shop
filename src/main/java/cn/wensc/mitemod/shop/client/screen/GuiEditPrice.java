package cn.wensc.mitemod.shop.client.screen;

import cn.wensc.mitemod.shop.api.ShopApi;
import cn.wensc.mitemod.shop.api.ShopItem;
import net.minecraft.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiEditPrice extends GuiScreen {
    private final ItemStack editStack;
    private static final RenderItem itemRenderer = new RenderItem();
    private GuiTextField soldPriceTextField;
    private GuiTextField buyPriceTextField;
    private final GuiScreen parentGuiScreen;

    public GuiEditPrice(GuiScreen parentGuiScreen, ItemStack editStack) {
        this.parentGuiScreen = parentGuiScreen;
        this.editStack = editStack;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.getString("gui.done")));
        this.soldPriceTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, this.height / 2 - 20, 200, 20);
        this.soldPriceTextField.setText(String.valueOf(((ShopItem) this.editStack.getItem()).getSoldPrice(this.editStack.getItemSubtype())));
        this.buyPriceTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, this.height / 2 + 20, 200, 20);
        this.buyPriceTextField.setText(String.valueOf(((ShopItem) this.editStack.getItem()).getBuyPrice(this.editStack.getItemSubtype())));
    }

    public void updateScreen() {
        this.soldPriceTextField.updateCursorCounter();
        this.buyPriceTextField.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                ShopApi.setPrice(this.editStack, this.parsePrice(this.soldPriceTextField), this.parsePrice(this.buyPriceTextField));
                ShopApi.saveConfigToFile();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
        }
    }

    protected void keyTyped(char par1, int par2) {
        this.soldPriceTextField.textboxKeyTyped(par1, par2);
        this.buyPriceTextField.textboxKeyTyped(par1, par2);
        ((GuiButton) this.buttonList.get(0)).enabled = !this.soldPriceTextField.getText().trim().isEmpty();
        ((GuiButton) this.buttonList.get(0)).enabled = !this.buyPriceTextField.getText().trim().isEmpty();

        if (par2 == 28 || par2 == 156) {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
        super.keyTyped(par1, par2);
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.soldPriceTextField.mouseClicked(par1, par2, par3);
        this.buyPriceTextField.mouseClicked(par1, par2, par3);
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.getStringParams("shop.editPrice.title", this.editStack.getDisplayName()), this.width / 2, 15, 16777215);
        this.drawString(this.fontRenderer, I18n.getString("shop.editPrice.soldPrice"), this.width / 2 - 100, this.height / 2 - 30, 4210752);
        this.drawString(this.fontRenderer, I18n.getString("shop.editPrice.buyPrice"), this.width / 2 - 100, this.height / 2 + 10, 4210752);
        this.soldPriceTextField.drawTextBox();
        this.buyPriceTextField.drawTextBox();
        this.renderStack();
        super.drawScreen(par1, par2, par3);
    }

    public void renderStack() {
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 1.0F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableGUIStandardItemLighting();
        int renderX = (screenWidth / 2) / 2 - 8;
        int renderY = (screenHeight / 4) / 2 - 8;
        itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), this.editStack, renderX, renderY);
        itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), this.editStack, renderX, renderY);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    private double parsePrice(GuiTextField textField) {
        try {
            return Double.parseDouble(textField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
