package com.ball.wondercrafting.common.blocks;

import com.ball.wondercrafting.core.init.BlockInit;
import com.ball.wondercrafting.core.init.PotionInit;
import com.ball.wondercrafting.core.init.TileEntityInit;
import com.ball.wondercrafting.particles.CorSmoke;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class Corhive extends Block {
    public Corhive(AbstractBlock.Properties properties) {
        super(properties.tickRandomly());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.CORHIVE.get().create();
    }


    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        int toxin = (int) ((Math.random() * 100));
        if (entityIn instanceof LivingEntity) {
            if(toxin<50) {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(PotionInit.COR_TOXIN.get(),900,4));
            }
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(PotionInit.COR_TOXIN.get(),400,9));
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, ISelectionContext.dummy());
        Vector3d vector3d = voxelshape.getBoundingBox().getCenter();
        double d0 = (double)pos.getX() + vector3d.x;
        double d1 = (double)pos.getZ() + vector3d.z;

        for(int i = 0; i < 60; ++i) {
            if (true) {
                worldIn.addParticle(new CorSmoke.CorSmokeData(), d0, (double)pos.getY() + (1.5D - rand.nextDouble()), d1, 0.0D, 0.0D, 0.0D);
            }
        }

    }
}
