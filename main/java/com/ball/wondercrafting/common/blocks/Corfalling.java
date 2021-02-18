package com.ball.wondercrafting.common.blocks;

import com.ball.wondercrafting.core.init.BlockInit;
import com.ball.wondercrafting.core.init.PotionInit;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Corfalling extends FallingBlock {

    private final int dustColor;

    public Corfalling(int dustColorIn, AbstractBlock.Properties properties) {
        super(properties.tickRandomly());
        this.dustColor = dustColorIn;
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
            return this.dustColor;
        }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 3)) return;
        BlockState blockstate1 = BlockInit.CORDIRT.get().getDefaultState();
        BlockState blockstate2 = BlockInit.CORGROWTH.get().getDefaultState();
        BlockState blockstate2fert = BlockInit.CORGROWTH_FERTILE.get().getDefaultState();
        BlockState blockstate3 = BlockInit.ROTWOOD_LOG.get().getDefaultState();
        BlockState blockstate4 = BlockInit.CORSAND.get().getDefaultState();
        BlockState blockstate5 = BlockInit.CORHIVE.get().getDefaultState();

        for(int i = 0; i < 4; ++i) {
            BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1); //remember to copy to corgrowth and rotated file
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

    }
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        int toxin = (int) ((Math.random() * 100));
        if (entityIn instanceof LivingEntity) {
            if(toxin==99) {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(PotionInit.COR_TOXIN.get(),900,0));
            }
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
