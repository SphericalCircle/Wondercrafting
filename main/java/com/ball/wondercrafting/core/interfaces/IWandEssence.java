package com.ball.wondercrafting.core.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IWandEssence {

    int getMys(ItemStack stack);

    int getCor(ItemStack stack);

    int getCho(ItemStack stack);

    int getTotal(ItemStack stack);

    int getScale();

    static void setEssence(ItemStack stack, int mysIn, int corIn, int choIn, int totalIn) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putInt("Mys", mysIn);
        compoundnbt.putInt("Cor", corIn);
        compoundnbt.putInt("Cho", choIn);
        compoundnbt.putInt("Total", totalIn);
    }
    ;
}
