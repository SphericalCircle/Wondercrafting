package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.world.feature.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureInit {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Wondercrafting.MOD_ID);

    public static final RegistryObject<Feature<ProbabilityConfig>> CRATE_FEATURE = FEATURES
            .register("crate", () -> new CrateFeature(
                    ProbabilityConfig.CODEC));
    public static final RegistryObject<Feature<ProbabilityConfig>> URN_FEATURE = FEATURES
            .register("urn", () -> new UrnFeature(
                    ProbabilityConfig.CODEC));
    public static final RegistryObject<Feature<ProbabilityConfig>> NETHER_CRATE_FEATURE = FEATURES
            .register("nether_crate", () -> new NetherCrateFeature(
                    ProbabilityConfig.CODEC));
    public static final RegistryObject<Feature<ProbabilityConfig>> WARPED_CRATE_FEATURE = FEATURES
            .register("warped_crate", () -> new WarpedCrateFeature(
                    ProbabilityConfig.CODEC));
    public static final RegistryObject<Feature<ProbabilityConfig>> WONDER_FEATURE = FEATURES
            .register("wonder", () -> new WonderFeature(
                    ProbabilityConfig.CODEC));
}
