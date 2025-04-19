package cn.wensc.mitemod.shop.mixin;

import cn.wensc.mitemod.shop.api.ShopItem;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(Item.class)
public abstract class ItemMixin implements ShopItem {
    @Shadow public abstract boolean getHasSubtypes();
    @Shadow public abstract List getSubItems();

    @Unique private final Map<Integer, Double> soldPriceArray = new HashMap<>();
    @Unique private final Map<Integer, Double> buyPriceArray = new HashMap<>();

    @Override
    public double getSoldPrice(int subtype) {
        return this.soldPriceArray.getOrDefault(subtype, 0.0D);
    }

    @Override
    public double getBuyPrice(int subtype) {
        return this.buyPriceArray.getOrDefault(subtype, 0.0D);
    }

    @Override
    public void setSoldPrice(int subtype, double soldPrice) {
        if (soldPrice > 0) {
            this.soldPriceArray.put(subtype, soldPrice);
        }
    }

    @Override
    public void setBuyPrice(int subtype, double buyPrice) {
        if (buyPrice > 0) {
            this.buyPriceArray.put(subtype, buyPrice);
        }
    }

    @Override
    public ShopItem setBuyPriceForAllSubs(double price) {
        if (price <= 0) {
            return this;
        }
        if (getHasSubtypes()) {
            List<ItemStack> subs = getSubItems();
            for (ItemStack itemStack : subs) {
                int sub = itemStack.getItemSubtype();
                this.buyPriceArray.put(sub, price);
            }
        } else {
            this.buyPriceArray.put(0, price);
        }
        return this;
    }

    @Override
    public ShopItem setSoldPriceForAllSubs(double price) {
        if (price <= 0) {
            return this;
        }
        if (this.getHasSubtypes()) {
            List<ItemStack> subs = getSubItems();
            for (ItemStack itemStack : subs) {
                int sub = itemStack.getItemSubtype();
                this.soldPriceArray.put(sub, price);
            }
        } else {
            this.soldPriceArray.put(0, price);
        }
        return this;
    }
}
