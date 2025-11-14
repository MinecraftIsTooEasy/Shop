package cn.wensc.mitemod.shop.inventory;

import cn.wensc.mitemod.shop.util.PriceStacks;
import net.minecraft.InventoryBasic;
import net.minecraft.ItemStack;

import java.util.List;

public class InventoryShop extends InventoryBasic {
    ContainerShop containerShop;

    public static final int pageSize = 45;

    public InventoryShop(ContainerShop containerShop) {
        super("temp", true, pageSize);
        this.containerShop = containerShop;
    }

    public void updateContent(int pageIndex) {
        List<ItemStack> list = PriceStacks.getPurchasableStacks();

        int merchandiseSize = list.size();
        int entryIndex = pageIndex * pageSize;
        if (entryIndex < merchandiseSize) {
            List<ItemStack> currentPageList = list.subList(entryIndex, Math.min(entryIndex + pageSize, merchandiseSize));
            if (currentPageList.size() > 0) {
                for (int i = 0; i < 45; i++) {
                    if (i < currentPageList.size()) {
                        setInventorySlotContents(i, ((ItemStack) currentPageList.get(i)).copy());
                    } else {
                        setInventorySlotContents(i, null);
                    }
                }
            }
        }
        this.containerShop.updatePlayerInventory(this.containerShop.player);
    }
}
