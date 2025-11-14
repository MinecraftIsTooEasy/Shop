package cn.wensc.mitemod.shop.inventory;

import cn.wensc.mitemod.shop.api.ShopApi;
import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.manager.MoneyManager;
import net.minecraft.EntityPlayer;
import net.minecraft.InventoryBasic;
import net.minecraft.ItemStack;

public class InventoryTrash extends InventoryBasic {
    private final EntityPlayer player;

    public InventoryTrash(ContainerShop containerShop) {
        super("trash", false, 1);
        this.player = containerShop.player;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (stack != null) {
            double soldPrice = ShopApi.getSoldPrice(stack);
            if (soldPrice > 0.0F && !this.player.worldObj.isRemote) {
                MoneyManager moneyManager = ShopPlayer.getMoneyManager(this.player);
                moneyManager.addMoney(stack.stackSize * soldPrice);
                moneyManager.syncToClient();
            }
            this.setInventorySlotContents(index, null);
        }
    }
}
