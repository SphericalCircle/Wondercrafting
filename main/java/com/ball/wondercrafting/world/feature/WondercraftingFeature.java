package com.ball.wondercrafting.world.feature;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.feature.*;

public abstract class WondercraftingFeature<FC extends IFeatureConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<net.minecraft.world.gen.feature.Feature<?>> {

    public static final Feature<ProbabilityConfig> CRATE = register("crate", new CrateFeature(ProbabilityConfig.CODEC));
    public static final Feature<ProbabilityConfig> URN = register("urn", new UrnFeature(ProbabilityConfig.CODEC));

    private static <C extends IFeatureConfig, F extends net.minecraft.world.gen.feature.Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }

    protected void setBlockState(IWorldWriter world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, 3);
    }


}
