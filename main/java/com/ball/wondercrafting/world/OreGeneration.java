package com.ball.wondercrafting.world;

import com.ball.wondercrafting.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {
    public static void generateOres(final BiomeLoadingEvent event) {
        if(!event.getCategory().equals(Biome.Category.NETHER)&&!event.getCategory().equals(Biome.Category.THEEND)) {
            generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.CORPEARL_ORE.get().getDefaultState(), 3, 5, 35, 8);
        }
    }
    private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state, int veinSize, int minHeight, int maxHeight, int amount) {
        settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.ORE.withConfiguration(new OreFeatureConfig(fillerType, state, veinSize)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeight, 0, maxHeight))).square().func_242731_b(amount));
    }
}
