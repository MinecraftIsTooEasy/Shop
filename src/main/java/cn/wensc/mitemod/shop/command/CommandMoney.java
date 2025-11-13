package cn.wensc.mitemod.shop.command;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import net.minecraft.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CommandMoney extends CommandBase {
    @Override
    public String getCommandName() {
        return "money";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.money.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer player)) throw new PlayerNotFoundException();
        if (args.length < 1) {
            player.addChatMessage(
                    ChatMessageComponent.createFromTranslationWithSubstitutions("commands.money.funds",
                            ((ShopPlayer) player).getMoneyManager().getMoney()).toString());
            return;
        }
        if (Objects.equals(args[0], "set") && args.length == 2) {
            double money = Double.parseDouble(args[1]);
            ((ShopPlayer) player).getMoneyManager().setMoney(money);
            player.addChatMessage(
                    ChatMessageComponent.createFromTranslationWithSubstitutions("commands.money.funds",
                            ((ShopPlayer) player).getMoneyManager().getMoney()).toString());        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("set");
        }
        return null;
    }
}
