package com.ball.wondercrafting.common.blocks;

import com.ball.wondercrafting.core.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NetherVegetationFeature;
import net.minecraft.world.gen.feature.TwistingVineFeature;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Corgrowth extends CorSpreadBlock implements IGrowable {
    public Corgrowth(AbstractBlock.Properties properties) {
        super(properties);
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return worldIn.getBlockState(pos.up()).isAir();
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        BlockState blockstate = worldIn.getBlockState(pos);
        BlockPos blockpos = pos.up();
            NetherVegetationFeature.func_236325_a_(worldIn, rand, blockpos, Features.Configs.CRIMSON_FOREST_VEGETATION_CONFIG, 3, 1);

    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockState blockstate1 = BlockInit.CORDIRT.get().getDefaultState();
        BlockState blockstate2 = BlockInit.CORGROWTH.get().getDefaultState();
        BlockState blockstate2fert = BlockInit.CORGROWTH_FERTILE.get().getDefaultState();
        BlockState blockstate3 = BlockInit.ROTWOOD_LOG.get().getDefaultState();
        BlockState blockstate4 = BlockInit.CORSAND.get().getDefaultState();
        BlockState blockstate5 = BlockInit.CORHIVE.get().getDefaultState();

        for(int i = 0; i < 4; ++i) {
            BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1); //remember to copy to corgrowth file
            if (worldIn.getBlockState(blockpos).isIn(Blocks.DIRT)||worldIn.getBlockState(blockpos).isIn(Blocks.COARSE_DIRT)||worldIn.getBlockState(blockpos).isIn(Blocks.FARMLAND)) {
                worldIn.setBlockState(blockpos, blockstate1);
            }
            else if (worldIn.getBlockState(blockpos).isIn(Blocks.GRASS_BLOCK)||worldIn.getBlockState(blockpos).isIn(Blocks.MYCELIUM)||worldIn.getBlockState(blockpos).isIn(Blocks.PODZOL)) {
                int grass = random.nextInt(3)+1;
                if(grass==1){
                    worldIn.setBlockState(blockpos, blockstate2);
                }
                if(grass==2){
                    worldIn.setBlockState(blockpos, blockstate2);
                }
                if(grass==3){
                    worldIn.setBlockState(blockpos, blockstate2fert);
                }
            }
            else if (worldIn.getBlockState(blockpos).isIn(BlockTags.LOGS_THAT_BURN)) {
                worldIn.setBlockState(blockpos, blockstate3);
            }
            else if (worldIn.getBlockState(blockpos).isIn(Blocks.SAND)||worldIn.getBlockState(blockpos).isIn(Blocks.RED_SAND)) {
                worldIn.setBlockState(blockpos, blockstate4);
            } else if (worldIn.getBlockState(blockpos).isIn(Blocks.BEEHIVE)||worldIn.getBlockState(blockpos).isIn(Blocks.BEE_NEST)||worldIn.getBlockState(blockpos).isIn(Blocks.PUMPKIN)||worldIn.getBlockState(blockpos).isIn(Blocks.MELON)) {
                worldIn.setBlockState(blockpos, blockstate5);
            }
        }
        if (worldIn.getLight(pos.up()) >= 9) {
                BlockState blockstate = this.getDefaultState();

            for (int i = 0; i < 4; ++i) {
                BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (worldIn.getBlockState(blockpos).getBlockState()==BlockInit.CORDIRT.get().getDefaultState()&&worldIn.getLight(blockpos.up()) >= 9) {
                    worldIn.setBlockState(blockpos, blockstate);
                }
            }
        }
        if (worldIn.getLight(pos.up()) < 2) {
            worldIn.setBlockState(pos, BlockInit.CORDIRT.get().getDefaultState());
        }
    }
}
