package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.world.biomes.WonderBiomeMaker;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Wondercrafting.MOD_ID);

    public static final RegistryObject<Biome> COR_GROWTH = BIOMES.register("cor_growth",
            () -> WonderBiomeMaker.makeCorGrowthBiome(0.45f, 0.3f));

    public static RegistryKey<Biome> COR_GROWTH_KEY = RegistryKey.getOrCreateKey(
            Registry.BIOME_KEY, new ResourceLocation("cor_growth"));

    public static void biomeLoading(BiomeLoadingEvent event) {
        if (event.getName() == COR_GROWTH.get().getRegistryName()) {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM,
                    new BiomeManager.BiomeEntry(COR_GROWTH_KEY, 6));
        }
    }
}
