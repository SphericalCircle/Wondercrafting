package com.ball.wondercrafting.world.feature;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

public class CorhiveFormation extends Tree {
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        if (randomIn.nextInt(3) == 0) {
            return WondercraftingFeatures.CORHIVE_FORMATION;
        }
        else {
            return WondercraftingFeatures.CORHIVE_FORMATION_SMALL;
        }

    }
}
