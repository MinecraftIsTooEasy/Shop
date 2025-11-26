package cn.wensc.mitemod.shop.client.screen;

import cn.wensc.mitemod.shop.ShopInit;
import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.client.MultiPlayerGameMode;
import cn.wensc.mitemod.shop.compat.EmiPluginImpl;
import cn.wensc.mitemod.shop.config.ShopConfigML;
import cn.wensc.mitemod.shop.inventory.ContainerShop;
import cn.wensc.mitemod.shop.localization.ShopCategoryText;
import cn.wensc.mitemod.shop.localization.ShopOrderText;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiShop extends GuiContainer {
    private GuiPaginationButton left;
    private GuiPaginationButton right;
    private ShopButtonBase switchView;
    private ShopButtonBase toggleOrder;

    public GuiShop(EntityPlayer player) {
        super(new ContainerShop(player));
        this.xSize = 176;//total 195 but I keep the original
        this.ySize = 222;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(this.left = new GuiPaginationButton(1, this.guiLeft + 6, this.height / 2 - 4, false));
        this.buttonList.add(this.right = new GuiPaginationButton(2, this.guiLeft + 176 - 19, this.height / 2 - 4, true));
        this.buttonList.add(this.switchView = new ShopButtonBase(3, this.guiLeft - 26, this.guiTop + 34, EnumIcon.SWITCH_VIEW));
        this.buttonList.add(this.toggleOrder = new ShopButtonBase(4, this.guiLeft - 26, this.guiTop + 64, EnumIcon.TOGGLE_ORDER));
        this.left.enabled = false;
        this.right.enabled = false;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        ContainerShop container = this.getContainer();
        this.left.enabled = container.canPageUp();
        this.right.enabled = container.canPageDown();

        int wheelStatus = Mouse.getDWheel();
        if (wheelStatus == 0) return;
        if (wheelStatus < 0) {
            this.actionPerformed(this.right);
        } else {
            this.actionPerformed(this.left);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (this.getContainer().clickMenuButton(this.mc.thePlayer, button.id)) {
            MultiPlayerGameMode.handleInventoryButtonClick(this.inventorySlots.windowId, button.id);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int par1, int par2) {
        String shopContainerName = I18n.getString("container.shop");
        this.fontRenderer.drawString(shopContainerName, this.xSize / 2 - this.fontRenderer.getStringWidth(shopContainerName) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        String moneyText = ShopPlayer.getMoneyManager(this.inventorySlots.player).getMoneyText();
        this.fontRenderer.drawString(moneyText, this.xSize / 2 - this.fontRenderer.getStringWidth(moneyText) / 2, this.ySize - 110, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.TEXTURE);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 195, this.height);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        DrawContext drawContext = new DrawContext();
        if (this.switchView.isHover()) {
            String tooltip = ShopCategoryText.from(this.getContainer().getCategory()).translate();
            RenderUtils.drawCreativeTabHoveringText(tooltip, par1, par2, drawContext);
        }
        if (this.toggleOrder.isHover()) {
            String tooltip = ShopOrderText.fromOrder(this.getContainer().getOrder()).translate();
            RenderUtils.drawCreativeTabHoveringText(tooltip, par1, par2, drawContext);
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == ShopInit.keyBindShop.keyCode) {
            this.mc.thePlayer.closeScreen();
        }
        super.keyTyped(par1, par2);
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType) {
        if (ShopConfigML.EditMode.getBooleanValue() && slotIn != null) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LMENU/* left Alt */) && clickedButton == 1) {
                if (slotIn.getHasStack()) {
                    ItemStack stack = slotIn.getStack().copy();
                    this.mc.displayGuiScreen(new GuiEditPrice(this, stack));
                    return;
                }
            }
        }
        super.handleMouseClick(slotIn, slotId, clickedButton, clickType);
    }

    public void registerEmiExclusiveArea(EmiPluginImpl.BoundRegistry registry) {
        registry.register(this.guiLeft - 30, this.guiTop + 30, 30, 60);
        registry.register(this.guiLeft + 176, this.guiTop, 19, 137);
    }

    private ContainerShop getContainer() {
        return (ContainerShop) this.inventorySlots;
    }
}
