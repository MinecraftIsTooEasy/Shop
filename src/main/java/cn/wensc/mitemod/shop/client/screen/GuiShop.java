package cn.wensc.mitemod.shop.client.screen;

import cn.wensc.mitemod.shop.ShopInit;
import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.config.ShopConfigML;
import cn.wensc.mitemod.shop.inventory.ContainerShop;
import cn.wensc.mitemod.shop.inventory.InventoryShop;
import cn.wensc.mitemod.shop.network.ShopNetwork;
import cn.wensc.mitemod.shop.network.packets.C2S.C2SShopPageIndex;
import net.minecraft.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiShop extends GuiContainer {
    public static int shopSize;

    private GuiPaginationButton left;
    private GuiPaginationButton right;
    private GuiSwitchViewButton switchView;

    private int pageIndex = 0;

    public GuiShop(EntityPlayer player) {
        super(new ContainerShop(player));
        this.xSize = 195;// 176
        this.ySize = 222;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(this.left = new GuiPaginationButton(1, this.guiLeft + 6, this.height / 2 - 4, false));
        this.buttonList.add(this.right = new GuiPaginationButton(2, this.guiLeft + 176 - 19, this.height / 2 - 4, true));
        this.buttonList.add(this.switchView = new GuiSwitchViewButton(3, this.guiLeft - 26, this.guiTop + 34));
        this.left.enabled = false;
        ShopNetwork.sendToServer(new C2SShopPageIndex(this.pageIndex));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        int wheelStatus = Mouse.getDWheel();
        if (wheelStatus == 0) return;
        if (wheelStatus < 0) {
            this.pageDown();
        } else {
            this.pageUp();
        }
    }

    protected void actionPerformed(GuiButton var1) {
        switch (var1.id) {
            case 1 -> pageUp();
            case 2 -> pageDown();
            case 3 -> switchView();
        }
    }

    private void switchView() {
        // TODO
    }

    private void pageDown() {
        if (!this.canPageDown()) return;
        this.pageIndex++;
        if (this.pageIndex == (double) (shopSize / InventoryShop.pageSize)) this.right.enabled = false;
        this.left.enabled = true;
        ShopNetwork.sendToServer(new C2SShopPageIndex(this.pageIndex));
    }

    private void pageUp() {
        if (!this.canPageUp()) return;
        this.pageIndex--;
        if (this.pageIndex == 0) this.left.enabled = false;
        this.right.enabled = true;
        ShopNetwork.sendToServer(new C2SShopPageIndex(this.pageIndex));
    }

    private boolean canPageUp() {
        return this.left.enabled;
    }

    private boolean canPageDown() {
        return this.right.enabled;
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
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
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
                }
            }
        }
        super.handleMouseClick(slotIn, slotId, clickedButton, clickType);
    }

    public int[] getEmiExclusiveArea() {
        return new int[]{this.switchView.xPosition - 4, this.switchView.yPosition - 4, 30, 30};
    }
}
