package com.ball.wondercrafting.core.interfaces;

import net.minecraft.item.ItemStack;

public interface IWandDisplay {

    default boolean shouldDisplay(ItemStack stack){
        return true;
    }
}
