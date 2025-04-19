package cn.wensc.mitemod.shop.event;

import cn.wensc.mitemod.shop.api.ShopPlayer;
import cn.wensc.mitemod.shop.command.CommandPrice;
import com.google.common.eventbus.Subscribe;
import net.minecraft.*;
import net.xiaoyu233.fml.reload.event.CommandRegisterEvent;
import net.xiaoyu233.fml.reload.event.HandleChatCommandEvent;

public class ShopEventFML {

    @Subscribe
    public void handleChatCommand(HandleChatCommandEvent event) {
        String par2Str = event.getCommand();
        EntityPlayer player = event.getPlayer();
        ICommandSender commandListener = event.getListener();
        if (Minecraft.inDevMode()) {
            World world = event.getWorld();

            if (par2Str.startsWith("setMoney ")) {
                double money = Double.parseDouble(par2Str.substring(9));
                ((ShopPlayer) player).getMoneyManager().setMoney(money);
                player.addChatMessage("现有余额：" + ((ShopPlayer) player).getMoneyManager().getMoney());
                event.setExecuteSuccess(true);
            }
        }
        if (par2Str.startsWith("money")) {
            player.addChatMessage("现有余额：" + String.format("%.2f", ((ShopPlayer) player).getMoneyManager().getMoney()));
            event.setExecuteSuccess(true);
        }

//        if (par2Str.startsWith("buy")) {
//            String sid = par2Str.substring(4);
//            String[] pos = sid.split(" ");
//            int[] poses = new int[3];
//            int Rx = 0;
//
//            for (int Rz = pos.length; Rx < Rz; ++Rx) {
//                String po = pos[Rx];
//                poses[Rx] = Integer.parseInt(po);
//            }
//
//            ItemStack buyGoods = null;
//            int sub = 0;
//            if (pos.length == 3) {
//                sub = poses[2];
//                buyGoods = new ItemStack(poses[0], poses[1], sub);
//            } else if (pos.length == 2) {
//                buyGoods = new ItemStack(poses[0], poses[1], 0);
//            }
//            if (buyGoods == null || buyGoods.getItem() == null) {
//                player.addChatMessage("商品ID输入错误");
//            } else {
//                if (!buyGoods.getItem().buyPriceArray.containsKey(sub) || ShopStack.getPrice(new ItemStack(sub)).soldPrice() <= 0D) {
//                    player.addChatMessage("商店暂不可兑换该商品");
//                } else
//                    if (poses[1] <= 0) {
//                    player.addChatMessage("请输入正确的商品数量");
//                } else if (poses[1] > buyGoods.getMaxStackSize()) {
//                    player.addChatMessage("超出该商品单次购买上限，最大为：" + buyGoods.getMaxStackSize());
//                } else {
//                    if (((ShopPlayer) player).getMoneyManager().getMoney() <= 0) {
//                        player.addChatMessage("钱包空空");
//                    } else if (((ShopPlayer) player).getMoneyManager().getMoney() - ShopStack.getPrice(new ItemStack(sub)).soldPrice() * poses[1] < 0) {
//                        player.addChatMessage("余额不足，无法购买");
//                    } else {
//                        ((ShopPlayer) player).getMoneyManager().setMoney(((ShopPlayer) player).getMoneyManager().getMoney() - ShopStack.getPrice(new ItemStack(sub)).soldPrice() * poses[1]);
//                        player.addChatMessage("现有余额：" + String.format("%.2f",  ((ShopPlayer) player).getMoneyManager().getMoney()));
//                        player.inventory.addItemStackToInventoryOrDropIt(buyGoods);
//                    }
//                }
//            }
//            event.setExecuteSuccess(true);
//        }
    }

    @Subscribe
    public void onCommandRegister(CommandRegisterEvent event) {
        event.register(new CommandPrice());
    }
}
