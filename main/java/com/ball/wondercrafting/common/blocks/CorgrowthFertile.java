package com.ball.wondercrafting.common.blocks;

import com.ball.wondercrafting.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NetherVegetationFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class CorgrowthFertile extends CorSpreadBlock implements IGrowable {
    public CorgrowthFertile(Properties properties) {
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

        BlockState blockstatespecial1 = BlockInit.GOREGRASS.get().getDefaultState();
        BlockState blockstatespecial2 = BlockInit.PEARLFLOWER.get().getDefaultState();
        BlockState blockstatespecial3 = BlockInit.DOUBLE_GOREGRASS.get().getDefaultState().with(DoubleCorgrass.HALF, DoubleBlockHalf.UPPER);
        BlockState blockstatespecial4 = BlockInit.DOUBLE_GOREGRASS.get().getDefaultState().with(DoubleCorgrass.HALF, DoubleBlockHalf.LOWER);
        BlockState blockstatespecial5 = BlockInit.CORHIVE_BUD.get().getDefaultState();

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
            } else if (worldIn.getBlockState(blockpos).isIn(Blocks.BEEHIVE)||worldIn.getBlockState(blockpos).isIn(Blocks.BEE_NEST)
                    ||worldIn.getBlockState(blockpos).isIn(Blocks.PUMPKIN)||worldIn.getBlockState(blockpos).isIn(Blocks.MELON)) {
                worldIn.setBlockState(blockpos, blockstate5);
            }
        }
        if (worldIn.getBlockState(pos.up()).isIn(Blocks.AIR)) {
            int plant = random.nextInt(100)+1;
            int hive = random.nextInt(10)+1;
            if(plant<=74){
                worldIn.setBlockState(pos.up(), blockstatespecial1);
            }
            if(plant>74&&plant<95) {
                worldIn.setBlockState(pos.up(), blockstatespecial4);
                worldIn.setBlockState(pos.up().up(), blockstatespecial3);
            }
            if(plant>94&&plant<100) {
                worldIn.setBlockState(pos.up(), blockstatespecial2);
            }
            if(plant==100) {
                if(hive==10) {worldIn.setBlockState(pos.up(), blockstatespecial5);}
                else {worldIn.setBlockState(pos.up(), blockstatespecial1);}
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
