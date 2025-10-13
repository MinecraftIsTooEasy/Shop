package cn.wensc.mitemod.shop.util;

import net.minecraft.Item;
import net.minecraft.ItemMap;
import net.minecraft.ItemStack;

import java.util.List;

public class ItemUtil {
    @SuppressWarnings("unchecked")
    public static List<ItemStack> createVariants(Item item) {
        if (item.getHasSubtypes()) {
            return item.getSubItems();
        } else {
            return List.of(new ItemStack(item));
        }
    }

    public static boolean canTrade(Item item) {
        if (item == null) return false;
        if (item.isBlock() && !item.getAsItemBlock().getBlock().canBeCarried()) return false;
        if (item instanceof ItemMap) return false;
        return true;
    }

    public static boolean canNotTrade(Item item) {
        return !canTrade(item);
    }
}
