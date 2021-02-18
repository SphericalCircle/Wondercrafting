package com.ball.wondercrafting.common.blocks;

import com.ball.wondercrafting.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class VitalityWood extends RotatedPillarBlock {
    public VitalityWood(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y));
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
                                             Hand handIn, BlockRayTraceResult hit) {
        if(player.getHeldItem(handIn) != null) {
            Item item = player.getHeldItem(handIn).getItem();
            if (item instanceof AxeItem) {
                worldIn.setBlockState(pos, BlockInit.STRIPPED_VITALITY_WOOD.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)));
                worldIn.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 0.7F, 1F);
                player.swingArm(handIn);
                player.getHeldItem(handIn).getStack().damageItem(1, player, Consumer -> player.sendBreakAnimation(handIn));
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
