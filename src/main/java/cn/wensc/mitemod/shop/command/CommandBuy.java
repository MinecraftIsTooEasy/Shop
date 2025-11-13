
package cn.wensc.mitemod.shop.command;

import cn.wensc.mitemod.shop.api.ShopApi;
import cn.wensc.mitemod.shop.api.ShopItem;
import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.manager.MoneyManager;
import cn.wensc.mitemod.shop.util.ItemUtil;
import net.minecraft.*;

import java.util.Arrays;
import java.util.List;

public class CommandBuy extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "buy";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.buy.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer player)) throw new PlayerNotFoundException();
        if (args.length < 1) throw new WrongUsageException(getCommandUsage(sender));

        ItemStack buyGoods;
        if (args.length == 3) buyGoods = parseItemStack(args[0], Integer.parseInt(args[2]));
        else buyGoods = parseItemStack(args[0], 0);
        if (buyGoods == null || buyGoods.getItem() == null) {
            player.addChatMessage(ChatMessageComponent.createFromTranslationKey("commands.buy.invalid_id").toString());
            return;
        }

        if (ItemUtil.canNotTrade(buyGoods.getItem())) {
            player.addChatMessage(ChatMessageComponent.createFromTranslationKey("commands.buy.untradeable_item").toString());
            return;
        }

        int amount = getAmount(args, buyGoods);
        double buyPrice = ShopApi.getBuyPrice(buyGoods);

        if (buyPrice <= 0) {
            player.addChatMessage(ChatMessageComponent.createFromTranslationKey("commands.buy.invalid_amount").toString());
            return;
        }

        double totalPrice = buyPrice * amount;
        MoneyManager moneyManager = ShopPlayer.getMoneyManager(player);
        double money = moneyManager.getMoney();

        if (money <= 0) {
            player.addChatMessage(ChatMessageComponent.createFromTranslationKey("commands.buy.no_funds").toString());
            return;
        }
        if (money < totalPrice) {
            player.addChatMessage(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.buy.insufficient_funds", money, totalPrice).toString());
            return;
        }

        buyGoods.setStackSize(amount);
        player.inventory.addItemStackToInventoryOrDropIt(buyGoods);
        moneyManager.subMoneyWithSimplify(totalPrice);
        player.addChatMessage(
                ChatMessageComponent.createFromTranslationWithSubstitutions("commands.buy.success",
                        amount, buyGoods.getDisplayName(), totalPrice, money).toString());
    }

    private static int getAmount(String[] args, ItemStack itemStack) {
        int amount = 1;
        if (args.length >= 2) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new NumberInvalidException("commands.buy.invalid_amount", args[1]);
            }
        }

        if (amount <= 0) {
            throw new NumberInvalidException("commands.buy.invalid_amount", String.valueOf(amount));
        }

        if (amount > itemStack.getMaxStackSize()) {
            amount = itemStack.getMaxStackSize();
        }
        return amount;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, getItemNames());
        }
        return null;
    }

    private ItemStack parseItemStack(String identifier, int subtype) {
        try {
            int itemId = Integer.parseInt(identifier);
            Item item = Item.getItem(itemId);
            if (item != null) {
                return new ItemStack(item);
            }
        } catch (NumberFormatException ignored) {
        }

        for (Item item : Item.itemsList) {
            if (item != null && item.getUnlocalizedName().equals(identifier)) {
                return new ItemStack(item, 0, subtype);
            }
        }

        return null;
    }

    private String[] getItemNames() {
        return Arrays.stream(Item.itemsList)
                .filter(ItemUtil::canTrade)
                .filter(item -> {
                    if (item.getHasSubtypes()) {
                        return item.getSubItems().stream()
                                .map(stack -> item)
                                .anyMatch(shopItem -> {
                                    ItemStack stack = (ItemStack) item.getSubItems().get(0);
                                    return ((ShopItem) shopItem).getBuyPrice(stack.getItemSubtype()) > 0;
                                });
                    } else {
                        return ((ShopItem) item).getBuyPrice(0) > 0;
                    }
                })
                .map(Item::getUnlocalizedName)
                .toArray(String[]::new);
    }
}