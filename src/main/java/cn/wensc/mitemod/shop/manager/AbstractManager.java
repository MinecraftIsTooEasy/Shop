package cn.wensc.mitemod.shop.manager;

import net.minecraft.EntityPlayer;
import net.minecraft.NBTTagCompound;

public class AbstractManager {
    protected final EntityPlayer player;

    public AbstractManager(EntityPlayer player) {
        this.player = player;
    }

    public void tick() {

    }

    public void write(NBTTagCompound nbtTagCompound) {

    }

    public void read(NBTTagCompound nbtTagCompound) {

    }

    public void clone(EntityPlayer entityPlayer) {

    }
}
