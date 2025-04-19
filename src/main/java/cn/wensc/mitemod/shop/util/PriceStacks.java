package cn.wensc.mitemod.shop.util;

import cn.wensc.mitemod.shop.api.ShopItem;
import cn.wensc.mitemod.shop.api.ShopStack;
import net.minecraft.Item;
import net.minecraft.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriceStacks {
    private static final List<ItemStack> priceStackList = new ArrayList<>();
    public static int shopSize = 0;

    private static boolean loadingFlag = false;

    public static void beginLoading() {
        priceStackList.clear();
        loadingFlag = true;
    }

    public static void setPrice(ItemStack itemStack, double soldPrice, double buyPrice) {
        Item item = itemStack.getItem();
        int sub = itemStack.getItemSubtype();
        ((ShopItem) item).setSoldPrice(sub, soldPrice);
        ((ShopItem) item).setBuyPrice(sub, buyPrice);
        ((ShopStack) itemStack).setPrice(soldPrice, buyPrice);
        if (soldPrice > 0.0D || buyPrice > 0.0D) {
            PriceStacks.addStack(itemStack);
        }
    }

    public static void addStack(ItemStack itemStack) {
        if (loadingFlag) {
            priceStackList.add(itemStack);
        } else {
            throw new IllegalStateException("PriceStacks: not loading");
        }
    }

    public static void endLoading() {
        loadingFlag = false;
    }

    public static void sortList() {
        priceStackList.sort((o1, o2) -> {
            double offset;
            double o1Buy = ShopStack.getPrice(o1).buyPrice();
            double o2Buy = ShopStack.getPrice(o2).buyPrice();
            if (o2Buy > 0.0D && o1Buy > 0.0D) {
                offset = o1Buy - o2Buy;
            } else {
                offset = o2Buy - o1Buy;
            }
            return Double.compare(offset, 0.0D);
        });
    }

    public static int getStackListSize() {
        return priceStackList.size();
    }

    public static List<ItemStack> subList(int from, int to) {
        return priceStackList.subList(from, to);
    }

    public static void handlePriceCommand(ItemStack itemStack, double soldPrice, double buyPrice) {
        Optional<ItemStack> current = matchItemStack(itemStack);
        current.ifPresent(priceStackList::remove);
        loadingFlag = true;
        setPrice(new ItemStack(itemStack.itemID, 1, itemStack.getItemSubtype()), soldPrice, buyPrice);
        loadingFlag = false;
    }

    public static Optional<ItemStack> matchItemStack(ItemStack itemStack) {
        int itemID = itemStack.itemID;
        int itemSubtype = itemStack.getItemSubtype();
        for (ItemStack stack : priceStackList) {
            if (stack.itemID == itemID && stack.getItemSubtype() == itemSubtype) return Optional.of(stack);
        }
        return Optional.empty();
    }
}
