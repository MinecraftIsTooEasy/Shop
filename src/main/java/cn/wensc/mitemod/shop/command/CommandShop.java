package cn.wensc.mitemod.shop.command;

import cn.wensc.mitemod.shop.config.ShopConfigs;
import cn.wensc.mitemod.shop.util.PriceStacks;
import net.minecraft.ChatMessageComponent;
import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;
import net.minecraft.WrongUsageException;

import java.util.List;

public class CommandShop extends CommandBase {
    private static final String RELOAD = "reload";
    private static final String SAVE = "save";
    private static final String SORT = "sort";

    @Override
    public String getCommandName() {
        return "shop";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.shop.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        int length = strings.length;
        if (length != 1) throw new WrongUsageException(getCommandUsage(iCommandSender));
        String string = strings[0];
        switch (string) {
            case RELOAD -> {
                ShopConfigs.loadOrCreate();
                iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText("重载成功"));
            }
            case SORT -> {
                PriceStacks.sortList();
                iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText("排序成功"));
            }
            case SAVE -> {
                ShopConfigs.saveShopConfigFile(ShopConfigs.ShopConfigFile);
                iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText("保存成功"));
            }
            default -> throw new WrongUsageException(getCommandUsage(iCommandSender));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] params) {
        int length = params.length;
        if (length == 1) {
            return getListOfStringsMatchingLastWord(params, RELOAD, SORT, SAVE);
        }
        return null;
    }
}
