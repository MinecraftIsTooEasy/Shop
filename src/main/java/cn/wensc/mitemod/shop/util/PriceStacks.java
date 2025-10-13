package cn.wensc.mitemod.shop.util;

import cn.wensc.mitemod.shop.api.ShopItem;
import cn.wensc.mitemod.shop.api.ShopStack;
import cn.wensc.mitemod.shop.config.ShopConfigs;
import cn.wensc.mitemod.shop.event.ShopPropertyRegistry;
import cn.wensc.mitemod.shop.network.packets.S2C.S2CSyncPrice;
import moddedmite.rustedironcore.network.Network;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.ServerPlayer;

import java.util.*;

public class PriceStacks {
    private static final List<ItemStack> merchandise = new ArrayList<>();

    private static final List<ItemStack> dirtyPriceStackList = new ArrayList<>();

    private static int shopSize = 0;

    private static boolean loadingFlag = false;

    public static void beginLoading() {
        dirtyPriceStackList.clear();
        loadingFlag = true;
    }

    public static void endLoading() {
        if (!loadingFlag) {
            throw new IllegalStateException("PriceStacks: not loading");
        }
        loadingFlag = false;
        PriceStacks.sortList();
    }

    public static boolean isLoading() {
        return loadingFlag;
    }

    /**
     * Only used on loading
     */
    public static void setPrice(ItemStack itemStack, double soldPrice, double buyPrice) {
        Item item = itemStack.getItem();
        int sub = itemStack.getItemSubtype();
        ((ShopItem) item).setSoldPrice(sub, soldPrice);
        ((ShopItem) item).setBuyPrice(sub, buyPrice);
        ShopStack.setPrice(itemStack, soldPrice, buyPrice);
        if (soldPrice > 0.0D || buyPrice > 0.0D) {
            addDirtyStack(itemStack);
        }
    }

    public static void addDirtyStack(ItemStack itemStack) {
        if (isLoading()) {
            dirtyPriceStackList.add(itemStack);
        } else {
            throw new IllegalStateException("PriceStacks: not loading");
        }
    }

    public static void sortList() {
        dirtyPriceStackList.sort((o1, o2) -> {
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
        refreshMerchandise();
    }

    public static int getMerchandiseSize() {
        return merchandise.size();
    }

    public static List<ItemStack> subList(int from, int to) {
        return merchandise.subList(from, to);
    }

    public static void handleNewPrice(ItemStack itemStack, double soldPrice, double buyPrice) {
        Optional<ItemStack> current = matchItemStack(itemStack);
        current.ifPresent(dirtyPriceStackList::remove);
        beginLoading();
        setPrice(new ItemStack(itemStack.itemID, 1, itemStack.getItemSubtype()), soldPrice, buyPrice);
        endLoading();
    }

    private static Optional<ItemStack> matchItemStack(ItemStack itemStack) {
        int itemID = itemStack.itemID;
        int itemSubtype = itemStack.getItemSubtype();
        for (ItemStack stack : dirtyPriceStackList) {
            if (stack.itemID == itemID && stack.getItemSubtype() == itemSubtype) return Optional.of(stack);
        }
        return Optional.empty();
    }

    @Environment(EnvType.SERVER)
    public static void sync(ServerPlayer player) {
        ShopPropertyRegistry.run();
        Network.sendToClient(player, new S2CSyncPrice(toMap(dirtyPriceStackList)));
    }

    @Environment(EnvType.CLIENT)
    public static void override(Map<EigenItemStack, PriceItem> map) {
        ShopConfigs.overrideItemPrice(map);
        List<ItemStack> list = dirtyPriceStackList;
        list.clear();
        list.addAll(toList(map));
        refreshMerchandise();
    }

    public static int getShopSize() {
        return shopSize;
    }

    public static void setShopSize(int shopSize) {
        PriceStacks.shopSize = shopSize;
    }

    private static List<ItemStack> toList(Map<EigenItemStack, PriceItem> map) {
        return map.entrySet().stream().map(entry -> {
            EigenItemStack eigenItemStack = entry.getKey();
            PriceItem priceItem = entry.getValue();
            ItemStack itemStack = new ItemStack(eigenItemStack.item(), 1, eigenItemStack.subtype());
            ShopStack.setPrice(itemStack, priceItem);
            return itemStack;
        }).toList();
    }

    private static Map<EigenItemStack, PriceItem> toMap(List<ItemStack> list) {
        Map<EigenItemStack, PriceItem> map = new LinkedHashMap<>();
        for (ItemStack itemStack : list) {
            map.put(new EigenItemStack(itemStack.getItem(), itemStack.getItemSubtype()), ShopStack.getPrice(itemStack));
        }
        return map;
    }

    private static void refreshMerchandise() {
        merchandise.clear();
        for (ItemStack itemStack : dirtyPriceStackList) {
            PriceItem price = ShopStack.getPrice(itemStack);
            if (price.buyPrice() > 0) merchandise.add(itemStack);
        }
    }
}
