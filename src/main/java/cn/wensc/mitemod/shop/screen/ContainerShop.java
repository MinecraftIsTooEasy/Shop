package cn.wensc.mitemod.shop.screen;

import cn.wensc.mitemod.shop.api.ShopItem;
import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.api.ShopStack;
import cn.wensc.mitemod.shop.manager.MoneyManager;
import cn.wensc.mitemod.shop.util.PriceItem;
import net.minecraft.*;

import java.util.ArrayList;

public class ContainerShop extends Container {
    public InventoryShop inventory = new InventoryShop(this);

    public ContainerShop(EntityPlayer player) {
        super(player);
        int row;
        for (row = 0; row < 5; row++) {
            for (int i = 0; i < 9; i++)
                addSlotToContainer(new SlotShop(this, this.inventory, i + row * 9, 8 + i * 18, 18 + row * 18));
        }
        for (row = 0; row < 3; row++) {
            for (int i = 0; i < 9; i++)
                addSlotToContainer(new Slot(player.inventory, i + row * 9 + 9, 8 + i * 18, 140 + row * 18));
        }
        for (int col = 0; col < 9; col++)
            addSlotToContainer(new Slot(player.inventory, col, 8 + col * 18, 198));
    }

    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
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
        double soldPrice = ShopItem.getSoldPrice(stack);
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
        PriceItem price = ((ShopStack) template).getPrice();
        final double buyPrice = price.buyPrice();

        if (buyPrice <= 0.0D) {
            this.notify(player, "商店不支持购买此商品");
            return;
        }

        double totalMoney = template.getMaxStackSize() * buyPrice;
        if (moneyManager.getMoney() >= totalMoney) {
            player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(template.itemID, template.getMaxStackSize(), template.getItemSubtype()));
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

    public void updatePlayerInventory(EntityPlayer player) {
        ArrayList<ItemStack> itemList = new ArrayList<>();
        for (int index = 0; index < player.openContainer.inventorySlots.size(); index++)
            itemList.add(((Slot) player.openContainer.inventorySlots.get(index)).getStack());
        ((ServerPlayer) player).sendContainerAndContentsToPlayer(player.openContainer, itemList);
    }

    private void notify(EntityPlayer player, String message) {
        if (player.onServer()) {
            player.addChatMessage(message);
        }
    }
}
