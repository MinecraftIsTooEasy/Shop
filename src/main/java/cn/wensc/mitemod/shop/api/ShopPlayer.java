package cn.wensc.mitemod.shop.api;

import cn.wensc.mitemod.shop.manager.MoneyManager;
import net.minecraft.EntityPlayer;

public interface ShopPlayer {
    MoneyManager getMoneyManager();

    static MoneyManager getMoneyManager(EntityPlayer player) {
        return ((ShopPlayer) player).getMoneyManager();
    }

    void displayGUIShop();
}
