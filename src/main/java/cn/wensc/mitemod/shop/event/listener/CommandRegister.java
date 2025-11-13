package cn.wensc.mitemod.shop.event.listener;

import cn.wensc.mitemod.shop.command.CommandBuy;
import cn.wensc.mitemod.shop.command.CommandMoney;
import cn.wensc.mitemod.shop.command.CommandPrice;
import cn.wensc.mitemod.shop.command.CommandShop;
import moddedmite.rustedironcore.api.event.events.CommandRegisterEvent;

import java.util.function.Consumer;

public class CommandRegister implements Consumer<CommandRegisterEvent> {
    @Override
    public void accept(CommandRegisterEvent event) {
        event.register(new CommandBuy());
        event.register(new CommandMoney());
        event.register(new CommandPrice());
        event.register(new CommandShop());
    }
}
