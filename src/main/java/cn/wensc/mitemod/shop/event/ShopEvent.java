package cn.wensc.mitemod.shop.event;

import cn.wensc.mitemod.shop.ShopInit;
import cn.wensc.mitemod.shop.event.listener.CommandRegister;
import cn.wensc.mitemod.shop.event.listener.PlayerEventListener;
import cn.wensc.mitemod.shop.event.listener.TickListener;
import moddedmite.rustedironcore.api.event.Handlers;
import moddedmite.rustedironcore.api.event.listener.IKeybindingListener;
import moddedmite.rustedironcore.api.event.listener.IWorldLoadListener;
import net.minecraft.KeyBinding;
import net.minecraft.WorldClient;
import org.lwjgl.input.Keyboard;

import java.util.function.Consumer;

public class ShopEvent {

    public static void register() {
        Handlers.Tick.register(new TickListener());
        Handlers.PlayerEvent.register(new PlayerEventListener());
        Handlers.WorldLoad.register(new IWorldLoadListener() {
            @Override
            public void onWorldLoad(WorldClient world) {
                ShopPropertyRegistry.run();
            }
        });
        Handlers.Command.register(new CommandRegister());
        Handlers.Keybinding.register(new IKeybindingListener() {
            @Override
            public void onKeybindingRegister(Consumer<KeyBinding> registry) {
                ShopInit.keyBindShop = new KeyBinding("key.openShop", Keyboard.KEY_V);
                registry.accept(ShopInit.keyBindShop);
            }
        });
    }
}
