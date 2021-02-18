package com.ball.wondercrafting.world.feature;

import com.ball.wondercrafting.core.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class WondercraftingFeatures {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CORHIVE_FORMATION = register("corhive_formation", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockInit.ROTWOOD_LOG.get().getDefaultState()), new SimpleBlockStateProvider(BlockInit.CORHIVE.get().getDefaultState()), new BushFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CORHIVE_FORMATION_SMALL = register("corhive_formation_small", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockInit.CORHIVE.get().getDefaultState()), new SimpleBlockStateProvider(BlockInit.CORHIVE.get().getDefaultState()), new BushFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), 0), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
