package com.ball.wondercrafting.common.tileentity;

import com.ball.wondercrafting.core.init.BlockInit;
import com.ball.wondercrafting.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class EpiphanyBloomTileEntity extends TileEntity implements ITickableTileEntity {

    public int x, y, z, tick;
    boolean initialized = false;

    BlockState blockstate1 = BlockInit.CORDIRT.get().getDefaultState();
    BlockState blockstate2 = BlockInit.CORGROWTH.get().getDefaultState();
    BlockState blockstate2fert = BlockInit.CORGROWTH_FERTILE.get().getDefaultState();
    BlockState blockstate3 = BlockInit.ROTWOOD_LOG.get().getDefaultState();
    BlockState blockstate4 = BlockInit.CORSAND.get().getDefaultState();
    BlockState blockstate5 = BlockInit.GOREGRASS.get().getDefaultState();
    BlockState blockstate6 = BlockInit.DOUBLE_GOREGRASS.get().getDefaultState();
    BlockState blockstate7 = BlockInit.PEARLFLOWER.get().getDefaultState();
    BlockState blockstate8 = BlockInit.CORHIVE_BUD.get().getDefaultState();

    public EpiphanyBloomTileEntity(final TileEntityType<?> tileEntityTypeIn) { super(tileEntityTypeIn); }

    public EpiphanyBloomTileEntity() {
        this(TileEntityInit.EPIPHANY_BLOOM.get());
    }

    @Override
    public void tick() {
        if (!initialized)
            init();
        tick++;
        if (tick % 2 == 0) {
            execute(this.getWorld(), this.getWorld().getRandom());
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
            BlockPos blockpos = pos.add(random.nextInt(17) - 8, random.nextInt(10) - 5, random.nextInt(17) - 8); //remember to copy to corgrowth and rotated file
            if (worldIn.getBlockState(blockpos) == blockstate1) {
                worldIn.setBlockState(blockpos, Blocks.DIRT.getDefaultState());
            } else if (worldIn.getBlockState(blockpos) == blockstate2||worldIn.getBlockState(blockpos) == blockstate2fert) {
                worldIn.setBlockState(blockpos, Blocks.GRASS_BLOCK.getDefaultState());
            } else if (worldIn.getBlockState(blockpos) == blockstate3) {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState());
            } else if (worldIn.getBlockState(blockpos) == blockstate4) {
                worldIn.setBlockState(blockpos, Blocks.SAND.getDefaultState());
            }
            else if (worldIn.getBlockState(blockpos) == blockstate5||worldIn.getBlockState(blockpos) == blockstate6||worldIn.getBlockState(blockpos) == blockstate7||worldIn.getBlockState(blockpos) == blockstate8) {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState());
            }

        }
    }
}
