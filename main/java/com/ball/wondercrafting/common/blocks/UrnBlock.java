package com.ball.wondercrafting.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class UrnBlock extends Block {

    protected static final VoxelShape SHAPEONE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D);
    protected static final VoxelShape SHAPETWO = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 10.0D, 15.0D);
    protected static final VoxelShape SHAPETHREE = Block.makeCuboidShape(2.0D, 10.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    protected static final VoxelShape SHAPEFOUR = Block.makeCuboidShape(1.0D, 13.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.or(VoxelShapes.or(SHAPEONE, SHAPETWO),VoxelShapes.or(SHAPETHREE, SHAPEFOUR));

    public UrnBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
