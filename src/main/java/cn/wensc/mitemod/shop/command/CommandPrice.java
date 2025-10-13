package cn.wensc.mitemod.shop.command;

import cn.wensc.mitemod.shop.api.ShopApi;
import cn.wensc.mitemod.shop.util.ItemUtil;
import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;
import net.minecraft.ItemStack;

public class CommandPrice extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandName() {
        return "price";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.price.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length == 2) {
            ItemStack itemStack = CommandBase.getPlayer(iCommandSender, iCommandSender.getCommandSenderName()).getHeldItemStack();
            if (itemStack == null) return;
            if (ItemUtil.canNotTrade(itemStack.getItem())) return;
            ShopApi.setPrice(itemStack, parseDouble(iCommandSender, strings[0]), parseDouble(iCommandSender, strings[1]));
        }
    }
}
