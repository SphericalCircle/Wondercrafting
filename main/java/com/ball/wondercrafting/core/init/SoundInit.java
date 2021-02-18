package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Wondercrafting.MOD_ID);

    public static final RegistryObject<SoundEvent> LOCATOR_BEEP = SOUNDS.register("locator.beep",
            () -> new SoundEvent(new ResourceLocation(Wondercrafting.MOD_ID, "locator.beep")));
    public static final RegistryObject<SoundEvent> BLIGHTWETA_HIT = SOUNDS.register("blightweta.hit",
            () -> new SoundEvent(new ResourceLocation(Wondercrafting.MOD_ID, "blightweta.hit")));
}
