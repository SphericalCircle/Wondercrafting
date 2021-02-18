package com.ball.wondercrafting.world;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.core.init.BlockInit;
import com.ball.wondercrafting.core.init.FeatureInit;
import com.ball.wondercrafting.world.feature.WondercraftingFeature;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class CrateGeneration {
    public static void generateCrates(final BiomeLoadingEvent event) {
        if(!event.getCategory().equals(Biome.Category.NETHER)&&!event.getCategory().equals(Biome.Category.THEEND)) {
            generateCrate(event.getGeneration(), 0.2F, 0, 90, 2);
        }
    }
    private static void generateCrate(BiomeGenerationSettingsBuilder settings, float chance, int minHeight, int maxHeight, int amount) {
        settings.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
                FeatureInit.CRATE_FEATURE.get().withConfiguration(new ProbabilityConfig(chance)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeight, 0, maxHeight))).square().func_242731_b(amount));
    }
}
