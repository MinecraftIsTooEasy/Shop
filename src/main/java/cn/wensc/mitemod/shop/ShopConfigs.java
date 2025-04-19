package cn.wensc.mitemod.shop;

import cn.wensc.mitemod.shop.api.ShopItem;
import cn.wensc.mitemod.shop.api.ShopStack;
import cn.wensc.mitemod.shop.util.PriceStacks;
import moddedmite.rustedironcore.api.util.LogUtil;
import net.minecraft.*;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ShopConfigs {
    private static final Logger LOGGER = LogUtil.getLogger();

    public static final String shopConfigFilePath = "config" + File.separator + "shop.cfg";

    public static void loadOrCreateFile(String filePth, BiConsumer<File, Properties> loadAction, Consumer<File> createAction) {
        File fileObj = new File(filePth);
        if (fileObj.exists()) {
            Properties properties = new Properties();
            FileReader fr = null;
            try {
                fr = new FileReader(fileObj);
                properties.load(fr);
                fr.close();
                loadAction.accept(fileObj, properties);
            } catch (IOException e) {
                LOGGER.warn("fail loading file", e);
            }
        } else {
            if (!fileObj.getParentFile().exists()) {
                fileObj.getParentFile().mkdirs();
            }
            try {
                if (fileObj.createNewFile()) {
                    fileObj.setExecutable(true);
                    fileObj.setReadable(true);
                    fileObj.setWritable(true);
                    createAction.accept(fileObj);
                }
            } catch (IOException e) {
                LOGGER.warn("fail creating file", e);
            }
        }
    }

    public static void readShopConfigFromFile(File file_mite, Properties properties) {
        PriceStacks.beginLoading();
        try {
            FileWriter appender = new FileWriter(file_mite, true);
            for (Item item : Item.itemsList) {
                if (canTrade(item)) {
                    for (ItemStack itemStack : createVariants(item)) {
                        if (!loadPrice(properties, item, itemStack))
                            appendPriceLine(appender, item, itemStack);
                    }
                }
            }
            appender.close();
        } catch (IOException e) {
            LOGGER.warn("error reading shop config", e);
        } finally {
            PriceStacks.endLoading();
            PriceStacks.sortList();
        }
    }

    @SuppressWarnings("unchecked")
    private static List<ItemStack> createVariants(Item item) {
        if (item.getHasSubtypes()) {
            return item.getSubItems();
        } else {
            return List.of(new ItemStack(item));
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
        if (itemPrice != null) {
            String[] soldPriceAndBuyPrice = itemPrice.split(",");
            if (soldPriceAndBuyPrice.length == 2) {
                setPriceFromFile(item, sub, itemStack, Double.parseDouble(soldPriceAndBuyPrice[0]), Double.parseDouble(soldPriceAndBuyPrice[1]));
            } else {
                setPriceFromFile(item, sub, itemStack, Double.parseDouble(soldPriceAndBuyPrice[0]), 0.0D);
            }
            return true;
        }
        return false;
    }

    private static void setPriceFromFile(Item item, int sub, ItemStack itemStack, double soldPrice, double buyPrice) {
//        if (!GAConfigManyLib.PriceConfigStrongOverride.getBooleanValue()) {
            double soldPriceFromMemory = ((ShopItem) item).getSoldPrice(sub);
            if (soldPrice == 0.0D && soldPriceFromMemory > 0.0D) {
                soldPrice = soldPriceFromMemory;
            }
            double buyPriceFromMemory = ((ShopItem) item).getBuyPrice(sub);
            if (buyPrice == 0.0D && buyPriceFromMemory > 0.0D) {
                buyPrice = buyPriceFromMemory;
            }
//        }

        PriceStacks.setPrice(itemStack, soldPrice, buyPrice);
    }

    public static void appendPriceLine(FileWriter fileWriter, Item item, ItemStack itemStack) throws IOException {
        int sub = itemStack.getItemSubtype();
        double soldPrice = ((ShopItem) item).getSoldPrice(sub);
        double buyPrice = ((ShopItem) item).getBuyPrice(sub);
        ((ShopStack) itemStack).setPrice(soldPrice, buyPrice);
        if (soldPrice > 0.0D || buyPrice > 0.0D)
            PriceStacks.addStack(itemStack);
        if (item.getHasSubtypes()) {
            fileWriter.write("// " + itemStack.getDisplayName() + " ID: " + itemStack.itemID + " meta:" + sub + "\n");
            fileWriter.write(itemStack.getUnlocalizedName() + "$" + item.itemID + "$" + sub + "=" + soldPrice + "," + buyPrice + "\n\n");
        } else {
            fileWriter.write("// " + itemStack.getDisplayName() + " ID: " + item.itemID + "\n");
            fileWriter.write(itemStack.getUnlocalizedName() + "$" + item.itemID + "=" + soldPrice + "," + buyPrice + "\n\n");
        }
    }

    public static void generateShopConfigFile(File file) {
        PriceStacks.beginLoading();
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("// 商店配置文件，说明：参数之间使用英文逗号分隔，请严格遵循格式（商品英文名=售出价格,购买价格），价格小于等于0代表不可出售或者不可购买，价格可以为小数，乱改造成无法启动概不负责\n");
            for (Item item : Item.itemsList) {
                if (canTrade(item)) {
                    for (ItemStack itemStack : createVariants(item)) {
                        appendPriceLine(fileWriter, item, itemStack);
                    }
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("error while generating shop config file", e);
        } finally {
            PriceStacks.sortList();
        }
        PriceStacks.endLoading();
    }

    public static void saveShopConfigFile(File file) {
        PriceStacks.beginLoading();
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("// 商店配置文件，说明：参数之间使用英文逗号分隔，请严格遵循格式（商品英文名=售出价格,购买价格），价格小于等于0代表不可出售或者不可购买，价格可以为小数，乱改造成无法启动概不负责\n");
            for (Item item : Item.itemsList) {
                if (canTrade(item)) {
                    for (ItemStack itemStack : createVariants(item)) {
                        appendPriceLine(fileWriter, item, itemStack);
                    }
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("error while generating shop config file", e);
        } finally {
            PriceStacks.endLoading();
            PriceStacks.sortList();
        }
    }

    public static boolean canTrade(Item item) {
        if (item == null) return false;
        if (item.isBlock() && !item.getAsItemBlock().getBlock().canBeCarried()) return false;
        if (item instanceof ItemMap) return false;
        return true;
    }
}
