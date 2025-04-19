package cn.wensc.mitemod.shop.screen;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.api.ShopStack;
import cn.wensc.mitemod.shop.manager.MoneyManager;
import cn.wensc.mitemod.shop.util.PriceItem;
import net.minecraft.*;

public class SlotShop extends Slot {
    int slotIndex;

    ContainerShop containerShop;

    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return false;
    }

    public SlotShop(ContainerShop containerShop, IInventory inventory, int slot_index, int display_x, int display_y) {
        super(inventory, slot_index, display_x, display_y, false);
        setContainer(containerShop);
        this.containerShop = containerShop;
        this.slotIndex = slot_index;
    }

    @Override
    public void onSlotClicked(EntityPlayer player, int button, Container container) {
        super.onSlotClicked(player, button, container);
        if (!this.getHasStack()) return;

        MoneyManager moneyManager = ShopPlayer.getMoneyManager(player);
        ItemStack template = getStack().copy();
        PriceItem price = ((ShopStack) template).getPrice();
        final double buyPrice = price.buyPrice();

        if (button != 0 && button != 1) return;

        if (buyPrice <= 0.0D) {
            this.notify(player, "商店不支持购买此商品");
            return;
        }

        switch (button) {
            case 0 -> {
                if (moneyManager.getMoney() - buyPrice >= 0.0D) {
                    moneyManager.subMoney(buyPrice);
                    player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(template.itemID, 1, template.getItemSubtype()));
                } else {
                    this.notify(player, "余额不足");
                }
            }

            case 1 -> {
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
        }
    }

    private void notify(EntityPlayer player, String message) {
        if (player.onServer()) {
            player.addChatMessage(message);
        }
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }
}
