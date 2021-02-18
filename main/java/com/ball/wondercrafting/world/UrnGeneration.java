package com.ball.wondercrafting.world;

import com.ball.wondercrafting.core.init.FeatureInit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class UrnGeneration {
    public static void generateUrns(final BiomeLoadingEvent event) {
        if(!event.getCategory().equals(Biome.Category.NETHER)&&!event.getCategory().equals(Biome.Category.THEEND)) {
            generateUrn(event.getGeneration(), 0.2F, 0, 90, 2);
        }
        if(event.getCategory().equals(Biome.Category.NETHER)) {
            generateUrn(event.getGeneration(), 0.2F, 30, 120, 4);
        }
    }
    private static void generateUrn(BiomeGenerationSettingsBuilder settings, float chance, int minHeight, int maxHeight, int amount) {
        settings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
                FeatureInit.URN_FEATURE.get().withConfiguration(new ProbabilityConfig(chance)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeight, 0, maxHeight))).square().func_242731_b(amount));
    }
}
