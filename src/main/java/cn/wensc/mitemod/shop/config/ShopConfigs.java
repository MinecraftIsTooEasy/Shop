package cn.wensc.mitemod.shop.config;

import cn.wensc.mitemod.shop.api.ShopItem;
import cn.wensc.mitemod.shop.api.ShopStack;
import cn.wensc.mitemod.shop.util.*;
import moddedmite.rustedironcore.api.util.LogUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class ShopConfigs {
    private static final Logger LOGGER = LogUtil.getLogger();

    public static final String ShopConfigFilePath = "config" + File.separator + "shop.cfg";

    public static final File ShopConfigFile = new File(ShopConfigFilePath);

    public static void loadOrCreate() {
        FileUtil.loadOrCreateFile(ShopConfigs.ShopConfigFile, ShopConfigs::readAndAppendMissing, ShopConfigs::generateFile);
    }

    public static void readAndAppendMissing(File file_mite, Properties properties) {
        PriceStacks.beginLoading();
        try {
            FileWriter appender = new FileWriter(file_mite, true);
            for (Item item : Item.itemsList) {
                if (ItemUtil.canNotTrade(item)) continue;
                for (ItemStack itemStack : ItemUtil.createVariants(item)) {
                    boolean exist = loadPrice(properties, item, itemStack);
                    if (!exist) appendPriceLine(appender, itemStack);
                }
            }
            appender.close();
        } catch (IOException e) {
            LOGGER.warn("error reading shop config", e);
        } finally {
            PriceStacks.endLoading();
        }
    }

    private static boolean loadPrice(Properties properties, Item item, ItemStack itemStack) {
        int sub = itemStack.getItemSubtype();
        String name;
        if (item.getHasSubtypes()) {
            name = itemStack.getUnlocalizedName() + "$" + itemStack.itemID + "$" + sub;
        } else {
            name = itemStack.getUnlocalizedName() + "$" + itemStack.itemID;
        }
        String itemPrice = (String) properties.get(name);
        if (itemPrice == null) return false;

        String[] soldPriceAndBuyPrice = itemPrice.split(",");

        double soldPrice, buyPrice;
        if (soldPriceAndBuyPrice.length == 2) {
            soldPrice = Double.parseDouble(soldPriceAndBuyPrice[0]);
            buyPrice = Double.parseDouble(soldPriceAndBuyPrice[1]);
        } else {
            soldPrice = Double.parseDouble(soldPriceAndBuyPrice[0]);
            buyPrice = 0.0D;
        }

        PriceStacks.setPrice(itemStack, soldPrice, buyPrice);
        return true;
    }

    public static void appendPriceLine(FileWriter fileWriter, ItemStack itemStack) throws IOException {
        Item item = itemStack.getItem();
        int sub = itemStack.getItemSubtype();
        double soldPrice = ((ShopItem) item).getSoldPrice(sub);
        double buyPrice = ((ShopItem) item).getBuyPrice(sub);
        ((ShopStack) itemStack).setPrice(soldPrice, buyPrice);
        if (soldPrice > 0.0D || buyPrice > 0.0D) PriceStacks.addDirtyStack(itemStack);
        if (item.getHasSubtypes()) {
            fileWriter.write("// " + itemStack.getDisplayName() + " ID: " + itemStack.itemID + " meta:" + sub + "\n");
            fileWriter.write(itemStack.getUnlocalizedName() + "$" + item.itemID + "$" + sub + "=" + soldPrice + "," + buyPrice + "\n\n");
        } else {
            fileWriter.write("// " + itemStack.getDisplayName() + " ID: " + item.itemID + "\n");
            fileWriter.write(itemStack.getUnlocalizedName() + "$" + item.itemID + "=" + soldPrice + "," + buyPrice + "\n\n");
        }
    }

    public static void generateFile(File file) {
        PriceStacks.beginLoading();
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("// 商店配置文件，说明：参数之间使用英文逗号分隔，请严格遵循格式（商品英文名=售出价格,购买价格），价格小于等于0代表不可出售或者不可购买，价格可以为小数，乱改造成无法启动概不负责\n");
            for (Item item : Item.itemsList) {
                if (ItemUtil.canNotTrade(item)) continue;
                for (ItemStack itemStack : ItemUtil.createVariants(item)) {
                    appendPriceLine(fileWriter, itemStack);
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("error while generating shop config file", e);
        } finally {
            PriceStacks.endLoading();
        }
    }

    public static void saveToFile(File file) {
        generateFile(file);// found no difference
    }

    @Environment(EnvType.CLIENT)
    public static void overrideItemPrice(Map<EigenItemStack, PriceItem> map) {
        for (Item item : Item.itemsList) {
            if (ItemUtil.canNotTrade(item)) continue;
            ((ShopItem) item).clearPrice();
        }
        map.forEach((eigenItemStack, priceItem) -> {
            Item item = eigenItemStack.item();
            int subtype = eigenItemStack.subtype();
            ShopItem.setBuyPrice(item, subtype, priceItem.buyPrice());
            ShopItem.setSoldPrice(item, subtype, priceItem.soldPrice());
        });
    }
}
