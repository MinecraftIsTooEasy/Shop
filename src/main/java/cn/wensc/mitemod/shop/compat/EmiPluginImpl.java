package cn.wensc.mitemod.shop.compat;

import cn.wensc.mitemod.shop.client.screen.GuiShop;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.widget.Bounds;

import java.util.function.Consumer;

public class EmiPluginImpl implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addExclusionArea(GuiShop.class, EmiPluginImpl::addExclusionArea);
    }

    private static void addExclusionArea(GuiShop guiShop, Consumer<Bounds> consumer) {
        guiShop.registerEmiExclusiveArea((x, y, width, height) -> consumer.accept(new Bounds(x, y, width, height)));
    }

    @FunctionalInterface
    public interface BoundRegistry {
        void register(int x, int y, int width, int height);
    }
}
