package cn.wensc.mitemod.shop.screen;

import cn.wensc.mitemod.shop.util.PriceStacks;
import net.minecraft.InventoryBasic;
import net.minecraft.ItemStack;

import java.util.List;

public class InventoryShop extends InventoryBasic {
    ContainerShop containerShop;

    public int pageIndex = 0;

    public static int pageSize = 45;

    public InventoryShop(ContainerShop containerShop) {
        super("temp", true, pageSize);
        this.containerShop = containerShop;
    }

    public void initItemList() {
        if (this.pageIndex * pageSize < PriceStacks.getMerchandiseSize()) {
            List<ItemStack> currentPageList = PriceStacks.subList(this.pageIndex * pageSize, Math.min(this.pageIndex * pageSize + pageSize, PriceStacks.getMerchandiseSize()));
            if (currentPageList.size() > 0)
                for (int i = 0; i < 45; i++) {
                    if (i < currentPageList.size()) {
                        setInventorySlotContents(i, ((ItemStack) currentPageList.get(i)).copy());
                    } else {
                        setInventorySlotContents(i, null);
                    }
                }
        }
        this.containerShop.updatePlayerInventory(this.containerShop.player);
    }
}
