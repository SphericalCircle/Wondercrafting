package com.ball.wondercrafting.common.items;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.common.tileentity.WonderTileEntity;
import com.ball.wondercrafting.core.init.BlockInit;
import com.ball.wondercrafting.core.interfaces.IWandDisplay;
import com.ball.wondercrafting.core.interfaces.IWandEssence;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class Wand extends Item implements IWandDisplay, IWandEssence {

    int random = 0;
    boolean hasMys, hasCor, hasCho;
    Random rand = new Random();

    public Wand(Item.Properties builder) { super(builder); }

    public static void setEssence(ItemStack stack, int mysIn, int corIn, int choIn, int totalIn) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putInt("Mys", mysIn);
        compoundnbt.putInt("Cor", corIn);
        compoundnbt.putInt("Cho", choIn);
        compoundnbt.putInt("Total", totalIn);
    }

    public int getMys(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        return compoundnbt.getInt("Mys");
    }

    public int getCor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        return compoundnbt.getInt("Cor");
    }

    public int getCho(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        return compoundnbt.getInt("Cho");
    }

    public int getTotal(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        return compoundnbt.getInt("Total");
    }

    public int getScale() {
        return 1;
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        setEssence(stack, 25, 0, 0, 25);
    }

    public static boolean point(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        return compoundnbt.getInt("pointing")==1;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = world.getBlockState(blockpos);
        if (!blockstate.isIn(BlockInit.WONDER.get())||getTotal(context.getItem())>=25||world.isRemote()) {
            return ActionResultType.FAIL;
        } else {
            runExtract(context.getItem(), world, blockpos);

            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putInt("pointing", 0);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }


    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putInt("pointing", 1);
        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void runExtract(ItemStack stack, World worldIn, BlockPos pos) {
        if(worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentMys")>1) {
            hasMys=true;
        }
        if(worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentCor")>1) {
            hasCor=true;
        }
        if(worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentCho")>1) {
            hasCho=true;
        }
        random = rand.nextInt(3);
        if(random==0) {
            if(hasMys) {
                setEssence(stack, getMys(stack)+1, getCor(stack), getCho(stack), getTotal(stack)+1);
                ((WonderTileEntity)worldIn.getTileEntity(pos)).currentMys = worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentMys")-1;
            }
            else if(hasCor) {
                setEssence(stack, getMys(stack), getCor(stack)+1, getCho(stack), getTotal(stack)+1);
                worldIn.getTileEntity(pos).serializeNBT().putInt("CurrentCor", worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentCor")-1);
            }
            else if(hasCho) {
                setEssence(stack, getMys(stack), getCor(stack), getCho(stack)+1, getTotal(stack)+1);
                worldIn.getTileEntity(pos).serializeNBT().putInt("CurrentCho", worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentCho")-1);
            }
        }
        else if(random==1) {
            if(hasCor) {
                setEssence(stack, getMys(stack), getCor(stack)+1, getCho(stack), getTotal(stack)+1);
                worldIn.getTileEntity(pos).serializeNBT().putInt("CurrentCor", worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentCor")-1);
            }
            else if(hasCho) {
                setEssence(stack, getMys(stack), getCor(stack), getCho(stack)+1, getTotal(stack)+1);
                worldIn.getTileEntity(pos).serializeNBT().putInt("CurrentCho", worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentCho")-1);
            }
            else if(hasMys) {
                setEssence(stack, getMys(stack)+1, getCor(stack), getCho(stack), getTotal(stack)+1);
                ((WonderTileEntity)worldIn.getTileEntity(pos)).currentMys = worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentMys")-1;
            }
        }
        else if(random==2) {
            if(hasCho) {
                setEssence(stack, getMys(stack), getCor(stack), getCho(stack)+1, getTotal(stack)+1);
                worldIn.getTileEntity(pos).serializeNBT().putInt("CurrentCho", worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentCho")-1);
            }
            else if(hasMys) {
                setEssence(stack, getMys(stack)+1, getCor(stack), getCho(stack), getTotal(stack)+1);
                ((WonderTileEntity)worldIn.getTileEntity(pos)).currentMys = worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentMys")-1;
            }
            else if(hasCor) {
                setEssence(stack, getMys(stack), getCor(stack)+1, getCho(stack), getTotal(stack)+1);
                worldIn.getTileEntity(pos).serializeNBT().putInt("CurrentCor", worldIn.getTileEntity(pos).serializeNBT().getInt("CurrentCor")-1);
            }
        }
        this.hasMys=false;
        this.hasCor=false;
        this.hasCho=false;
    }
}
