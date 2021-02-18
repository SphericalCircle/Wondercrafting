package com.ball.wondercrafting.common.blocks;

import com.ball.wondercrafting.core.init.TileEntityInit;
import com.ball.wondercrafting.particles.CorSmoke;
import com.ball.wondercrafting.particles.WonderSpark;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class Wonder extends Block {

    Random rand = new Random();
    float red, blue, green, alpha;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public Wonder(AbstractBlock.Properties properties) {
        super(properties.tickRandomly());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.WONDER.get().create();

    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (worldIn.getTileEntity(pos).serializeNBT().getInt("Cho") >= 50 && worldIn.getTileEntity(pos).serializeNBT().getInt("Cho") < 100 && !worldIn.isRemote()) {
            if (rand.nextBoolean()) {
                worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1.0f, Explosion.Mode.BREAK);
            }
        }
        if (worldIn.getTileEntity(pos).serializeNBT().getInt("Cho") >= 100 && !worldIn.isRemote()) {
            worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.0f, Explosion.Mode.BREAK);
        }
    }
}
