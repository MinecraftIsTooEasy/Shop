package cn.wensc.mitemod.shop.inventory;

import cn.wensc.mitemod.shop.api.ShopApi;
import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.api.ShopStack;
import cn.wensc.mitemod.shop.manager.MoneyManager;
import cn.wensc.mitemod.shop.util.PriceItem;
import cn.wensc.mitemod.shop.util.PriceStacks;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.*;

import java.util.List;

public class ContainerShop extends Container {
    public static final int pageSize = 45;
    public InventoryBasic display = new InventoryBasic("display", false, 45);

    private int shopSize = 0;
    private View view = View.PURCHASABLE;
    private int pageIndex = 0;

    public ContainerShop(EntityPlayer player) {
        super(player);
        int row;
        for (row = 0; row < 5; row++) {
            for (int i = 0; i < 9; i++)
                addSlotToContainer(new SlotShop(this.display, i + row * 9, 8 + i * 18, 18 + row * 18));
        }
        for (row = 0; row < 3; row++) {
            for (int i = 0; i < 9; i++)
                addSlotToContainer(new Slot(player.inventory, i + row * 9 + 9, 8 + i * 18, 140 + row * 18));
        }
        for (int col = 0; col < 9; col++)
            addSlotToContainer(new Slot(player.inventory, col, 8 + col * 18, 198));
        IInventory trash = new InventoryTrash(this);
        addSlotToContainer(new Slot(trash, 0, 173, 112));// not sure the x,y
    }

    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return true;
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotIndex) {
        Slot slot = (Slot) this.inventorySlots.get(slotIndex);
        if (slot == null) return null;
        if (!slot.getHasStack()) return null;
        if (slotIndex < 45) {
            handleBuyStack(slot);
            return null;
        }
        handleSellStack(slot);
        return null;
    }

    private void handleSellStack(Slot slot) {
        ItemStack stack = slot.getStack();
        double soldPrice = ShopApi.getSoldPrice(stack);
        if (soldPrice > 0.0D) {
            double totalMoney = stack.stackSize * soldPrice;
            MoneyManager moneyManager = ShopPlayer.getMoneyManager(this.player);
            moneyManager.addMoneyWithSimplify(totalMoney);
            slot.putStack(null);
        }
    }

    private void handleBuyStack(Slot slot) {
        MoneyManager moneyManager = ShopPlayer.getMoneyManager(player);
        ItemStack template = slot.getStack().copy();
        PriceItem price = ShopStack.getPrice(template);
        final double buyPrice = price.buyPrice();

        if (buyPrice <= 0.0D) {
            this.notify(player, "商店不支持购买此商品");
            return;
        }

        double totalMoney = template.getMaxStackSize() * buyPrice;
        if (moneyManager.getMoney() >= totalMoney) {
            ItemStack out = new ItemStack(template.itemID, template.getMaxStackSize(), template.getItemSubtype());
            player.inventory.addItemStackToInventoryOrDropIt(out);
            moneyManager.subMoneyWithSimplify(totalMoney);
        } else {
            int maxStackSize = (int) Math.floor(moneyManager.getMoney() / buyPrice);
            if (maxStackSize > 0) {
                totalMoney = maxStackSize * buyPrice;
                moneyManager.subMoneyWithSimplify(totalMoney);
                template.setStackSize(maxStackSize);
                player.inventory.addItemStackToInventoryOrDropIt(template);
            } else {
                this.notify(player, "余额不足");
            }
        }
    }

    private void notify(EntityPlayer player, String message) {
        if (player.onServer()) {
            player.addChatMessage(message);
        }
    }

    public boolean canPageUp() {
        return this.pageIndex > 0;
    }

    public boolean canPageDown() {
        return this.pageIndex < this.shopSize / pageSize;
    }

    public void setShopSize(int shopSize) {
        this.shopSize = shopSize;
    }

    public View getView() {
        return this.view;
    }

    public boolean clickMenuButton(EntityPlayer player, int id) {
        if (id == 1 && this.canPageUp()) {
            this.pageIndex--;
            if (player.onServer()) this.updateDisplay();
            return true;
        }
        if (id == 2 && this.canPageDown()) {
            this.pageIndex++;
            if (player.onServer()) this.updateDisplay();
            return true;
        }
        if (id == 3) {
            this.switchView();
            return true;
        }
        return false;
    }

    private void switchView() {
        this.view = this.view.next();
        // TODO
    }

    @Environment(EnvType.SERVER)
    public void broadcastChanges() {
    }

    @Environment(EnvType.SERVER)
    public void updateDisplay() {
        List<ItemStack> list = PriceStacks.getPurchasableStacks();
        int merchandiseSize = list.size();
        this.shopSize = merchandiseSize;
        int entryIndex = this.pageIndex * pageSize;
        if (entryIndex < merchandiseSize) {
            List<ItemStack> currentPageList = list.subList(entryIndex, Math.min(entryIndex + pageSize, merchandiseSize));
            if (currentPageList.size() > 0) {
                for (int i = 0; i < 45; i++) {
                    if (i < currentPageList.size()) {
                        this.display.setInventorySlotContents(i, currentPageList.get(i).copy());
                    } else {
                        this.display.setInventorySlotContents(i, null);
                    }
                }
            }
        }

        ((ServerPlayer) this.player).sendContainerToPlayer(this);
    }

    public enum View {
        PURCHASABLE,
        SELLABLE,
        BOTH,
        ;
        static final ImmutableList<View> VALUES = ImmutableList.copyOf(values());

        View next() {
            return VALUES.get((this.ordinal() + 1) % VALUES.size());
        }
    }
}
