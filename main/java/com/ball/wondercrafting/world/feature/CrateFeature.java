package com.ball.wondercrafting.world.feature;

import com.ball.wondercrafting.common.blocks.CrateBlock;
import com.ball.wondercrafting.core.init.BlockInit;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CrateFeature extends Feature<ProbabilityConfig> {
    private static final BlockState CRATE = BlockInit.CRATE.get().getDefaultState();
    private static final BlockState CRATE_CHAIN = BlockInit.CRATE.get().getDefaultState().with(CrateBlock.HANGING, true);
    private static final BlockState CHAIN = Blocks.CHAIN.getDefaultState();

    public CrateFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ProbabilityConfig config) {
        int i = 0;
        BlockPos.Mutable blockpos$mutable = pos.toMutable();
        BlockPos.Mutable blockpos$mutable1 = pos.toMutable();
        BlockPos chainChange = pos;
        if (reader.isAirBlock(blockpos$mutable)) {
            if (reader.getBlockState(blockpos$mutable.down())==Blocks.GRASS_BLOCK.getDefaultState()||reader.getBlockState(blockpos$mutable.down())==Blocks.DIRT.getDefaultState()
                    ||reader.getBlockState(blockpos$mutable.down())==Blocks.MYCELIUM.getDefaultState()||reader.getBlockState(blockpos$mutable.down())==Blocks.PODZOL.getDefaultState()
                    ||reader.getBlockState(blockpos$mutable.down())==Blocks.SAND.getDefaultState()||reader.getBlockState(blockpos$mutable.down())==Blocks.TERRACOTTA.getDefaultState()
                    ||reader.getBlockState(blockpos$mutable.down())==Blocks.RED_SAND.getDefaultState()||reader.getBlockState(blockpos$mutable.down())==Blocks.GRAVEL.getDefaultState()
                    ||reader.getBlockState(blockpos$mutable.down())==Blocks.COARSE_DIRT.getDefaultState()||reader.getBlockState(blockpos$mutable.down())==Blocks.STONE.getDefaultState()
                    ||reader.getBlockState(blockpos$mutable.down())==Blocks.ANDESITE.getDefaultState()||reader.getBlockState(blockpos$mutable.down())==Blocks.DIORITE.getDefaultState()
                    ||reader.getBlockState(blockpos$mutable.down())==Blocks.GRANITE.getDefaultState()
                    ||(reader.getBlockState(blockpos$mutable.down())==Blocks.CAVE_AIR.getDefaultState()&&reader.getBlockState(blockpos$mutable.up())==Blocks.CAVE_AIR.getDefaultState())) {
                int j = rand.nextInt(12) + 5;
                if (rand.nextFloat() < config.probability) {
                    int k = rand.nextInt(4) + 1;
                }

                for(int l1 = 0; l1 < j && reader.isAirBlock(blockpos$mutable); ++l1) {
                    if(reader.getBlockState(blockpos$mutable.up())==Blocks.CAVE_AIR.getDefaultState()&&(reader.getBlockState(blockpos$mutable.down())==Blocks.CAVE_AIR.getDefaultState())&&!reader.canBlockSeeSky(blockpos$mutable)) {
                        reader.setBlockState(blockpos$mutable, CRATE_CHAIN, 2);
                    }
                    else if (!(reader.getBlockState(blockpos$mutable.down())==Blocks.CAVE_AIR.getDefaultState()&&reader.getWorld().canSeeSky(blockpos$mutable.up()))) {
                        reader.setBlockState(blockpos$mutable, CRATE, 2);
                    }
                    while(reader.getBlockState(chainChange.up())==Blocks.CAVE_AIR.getDefaultState()&&reader.getBlockState(blockpos$mutable.down())==Blocks.CAVE_AIR.getDefaultState()&&!reader.canBlockSeeSky(blockpos$mutable)) {
                        reader.setBlockState(chainChange.up(), CHAIN, 2);
                        chainChange = chainChange.up();
                    }
                }
            }

            ++i;
        }

        return i > 0;
    }
}
