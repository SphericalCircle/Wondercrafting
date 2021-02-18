package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.common.effects.CorToxin;
import net.minecraft.block.Block;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionInit {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Wondercrafting.MOD_ID);
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Wondercrafting.MOD_ID);

    public static final RegistryObject<Effect> COR_TOXIN = EFFECTS.register("cor_toxin",
            () -> new CorToxin());

    public static final RegistryObject<Potion> COR_TOXIN_POTION = POTIONS.register("cor_toxin",
            () -> new Potion(new EffectInstance(COR_TOXIN.get(), 900, 1)));

}
