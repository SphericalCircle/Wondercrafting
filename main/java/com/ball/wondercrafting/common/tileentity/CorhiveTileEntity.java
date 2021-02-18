package com.ball.wondercrafting.common.tileentity;

import com.ball.wondercrafting.core.init.BlockInit;
import com.ball.wondercrafting.core.init.PotionInit;
import com.ball.wondercrafting.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class CorhiveTileEntity extends TileEntity implements ITickableTileEntity {

    public int x, y, z, tick;
    boolean initialized = false;

    public CorhiveTileEntity(final TileEntityType<?> tileEntityTypeIn) { super(tileEntityTypeIn); }

    public CorhiveTileEntity() {
        this(TileEntityInit.CORHIVE.get());
    }

    BlockState blockstate1 = BlockInit.CORDIRT.get().getDefaultState();
    BlockState blockstate2 = BlockInit.CORGROWTH.get().getDefaultState();
    BlockState blockstate2fert = BlockInit.CORGROWTH_FERTILE.get().getDefaultState();
    BlockState blockstate3 = BlockInit.ROTWOOD_LOG.get().getDefaultState();
    BlockState blockstate4 = BlockInit.CORSAND.get().getDefaultState();
    BlockState blockstate5 = BlockInit.CORHIVE.get().getDefaultState();

    @Override
    public void tick() {
        if (!initialized)
            init();
        tick++;
        if (tick % 10 == 0) {
            execute(this.getWorld(), this.getWorld().getRandom());
        }
        if (tick == 100) {
            toxic(this.getWorld());
            tick = 0;
        }
    }

    private void toxic(World worldIn) {
        worldIn = this.getWorld();
        List<Entity> poisonList = worldIn.getEntitiesWithinAABB(LivingEntity.class, (new AxisAlignedBB((double)this.x+10, (double)this.y+5, (double)this.z+10,(double)this.x-10, (double)this.y-1, (double)this.z-10)));
        for (int i = 0; i < poisonList.size(); i++) {
            Entity entityIn = poisonList.get(i);
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(PotionInit.COR_TOXIN.get(),900,2));
        }

    }

    private void init() {
        initialized = true;
        x = this.pos.getX();
        y = this.pos.getY();
        z = this.pos.getZ();
        tick = 0;
    }

    private void execute(World worldIn, Random random) {
        for (int i = 0; i < 4; ++i) {
            BlockPos blockpos = pos.add(random.nextInt(21) - 10, random.nextInt(10) - 5, random.nextInt(21) - 10); //remember to copy to corgrowth and rotated file
            if (worldIn.getBlockState(blockpos).isIn(Blocks.DIRT) || worldIn.getBlockState(blockpos).isIn(Blocks.COARSE_DIRT)||worldIn.getBlockState(blockpos).isIn(Blocks.FARMLAND)) {
                worldIn.setBlockState(blockpos, blockstate1);
            } else if (worldIn.getBlockState(blockpos).isIn(Blocks.GRASS_BLOCK) || worldIn.getBlockState(blockpos).isIn(Blocks.MYCELIUM) || worldIn.getBlockState(blockpos).isIn(Blocks.PODZOL)) {
                int grass = random.nextInt(3) + 1;
                if (grass == 1) {
                    worldIn.setBlockState(blockpos, blockstate2);
                }
                if (grass == 2) {
                    worldIn.setBlockState(blockpos, blockstate2);
                }
                if (grass == 3) {
                    worldIn.setBlockState(blockpos, blockstate2fert);
                }
            } else if (worldIn.getBlockState(blockpos).isIn(BlockTags.LOGS_THAT_BURN)) {
                worldIn.setBlockState(blockpos, blockstate3);
            } else if (worldIn.getBlockState(blockpos).isIn(Blocks.SAND) || worldIn.getBlockState(blockpos).isIn(Blocks.RED_SAND)) {
                worldIn.setBlockState(blockpos, blockstate4);
            } else if (worldIn.getBlockState(blockpos).isIn(Blocks.BEEHIVE)||worldIn.getBlockState(blockpos).isIn(Blocks.BEE_NEST)
                    ||worldIn.getBlockState(blockpos).isIn(Blocks.PUMPKIN)||worldIn.getBlockState(blockpos).isIn(Blocks.MELON)) {
                worldIn.setBlockState(blockpos, blockstate5);
            }

        }
    }

}
