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
import org.jetbrains.annotations.ApiStatus;

import java.util.*;

public class PriceStackStorage {
    private static final List<ItemStack> dirtyPriceStackList = new ArrayList<>();

    private static boolean loadingFlag = false;

    public static void clear() {
        dirtyPriceStackList.clear();
    }

    public static void beginLoading() {
        loadingFlag = true;
    }

    public static void endLoading() {
        if (!loadingFlag) {
            throw new IllegalStateException("PriceStacks: not loading");
        }
        loadingFlag = false;
        onListChanged();
    }

    public static boolean isLoading() {
        return loadingFlag;
    }

    /**
     * Only used on loading
     */
    @ApiStatus.Internal
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

    @ApiStatus.Internal
    public static void addDirtyStack(ItemStack itemStack) {
        if (isLoading()) {
            dirtyPriceStackList.add(itemStack);
        } else {
            throw new IllegalStateException("PriceStacks: not loading");
        }
    }

    public static void handleNewPrice(ItemStack itemStack, double soldPrice, double buyPrice) {
        beginLoading();

        Optional<ItemStack> current = matchItemStack(itemStack);
        current.ifPresent(dirtyPriceStackList::remove);
        setPrice(new ItemStack(itemStack.itemID, 1, itemStack.getItemSubtype()), soldPrice, buyPrice);

        endLoading();
        onListChanged();
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
        Network.sendToClient(player, new S2CSyncPrice(CODEC.toMap(dirtyPriceStackList)));
    }

    @Environment(EnvType.CLIENT)
    public static void override(Map<EigenItemStack, PriceItem> map) {
        ShopConfigs.overrideItemPrice(map);
        List<ItemStack> list = dirtyPriceStackList;
        list.clear();
        list.addAll(CODEC.toList(map));
        onListChanged();
    }

    private static void onListChanged() {
    }

    public static List<ItemStack> getDirtyStacks() {
        return Collections.unmodifiableList(dirtyPriceStackList);
    }

    private static class CODEC {
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
    }
}
