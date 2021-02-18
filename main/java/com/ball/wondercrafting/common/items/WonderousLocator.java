package com.ball.wondercrafting.common.items;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.common.tileentity.WonderTileEntity;
import com.ball.wondercrafting.core.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WonderousLocator extends Item {

    public int tick;
    public BlockPos pos, tilePos;

    public WonderousLocator(Item.Properties builder) {
        super(builder);
    }

    public static boolean isBlinking(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("Blinking");
    }

    public static void setBlinking(ItemStack stack, boolean blinkIn) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putBoolean("Blinking", blinkIn);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        setBlinking(stack, false);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(isSelected) {
            tick++;
            if(tick>=getBeepInterval(worldIn, entityIn)&&checkForWonder(worldIn, entityIn)){
                setBlinking(stack, true);
                SoundCategory soundcategory = entityIn instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
                worldIn.playSound((PlayerEntity)null, entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), SoundInit.LOCATOR_BEEP.get(), soundcategory, 1.0F, 1.0F);
                tick = 0;
            }
            if(tick>2&&tick<10) {
                setBlinking(stack, false);
            }
            if(tick>2&&tick<4&&checkForWonder(worldIn, entityIn)) {
                SoundCategory soundcategory = entityIn instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
                worldIn.playSound((PlayerEntity)null, entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), SoundInit.LOCATOR_BEEP.get(), soundcategory, 1.0F, 1.0F);
            }
        }
        else {
            setBlinking(stack, false);
        }

    }

    public void blink(ItemStack stack, World worldIn, Entity entityIn){
        worldIn.playSound((PlayerEntity)null, entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), SoundInit.LOCATOR_BEEP.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
        setBlinking(stack,true);
    }

    public int getBeepInterval(World worldIn, Entity entityIn) {
        if(checkForWonderCloser(worldIn, entityIn)) {return 20;}
        else if (checkForWonderClose(worldIn, entityIn)) {return 40;}
        else return 80;
    }

    public boolean checkForWonder(World worldIn, Entity entityIn) {
        for(int i = -16;i<=16;i++) {
            for(int j = -16;j<=16;j++) {
                for(int k = 0;k<256;k++) {
                    pos = new BlockPos(entityIn.getPosX()+i,k,entityIn.getPosZ()+j);
                    if(worldIn.getTileEntity(pos) instanceof WonderTileEntity) {
                        tilePos = pos;
                    }
                }
            }
        }
        if(tilePos!=null) {
            tilePos=null;
            pos=null;
            return true;
        }
        else {
            pos=null;
            return false;
        }
    }

    public boolean checkForWonderClose(World worldIn, Entity entityIn) {
        for(int i = -8;i<=8;i++) {
            for(int j = -8;j<=8;j++) {
                for(int k = 0;k<256;k++) {
                    pos = new BlockPos(entityIn.getPosX()+i,k,entityIn.getPosZ()+j);
                    if(worldIn.getTileEntity(pos) instanceof WonderTileEntity) {
                        tilePos = pos;
                    }
                }
            }
        }
        if(tilePos!=null) {
            tilePos=null;
            pos=null;
            return true;
        }
        else {
            pos=null;
            return false;
        }
    }

    public boolean checkForWonderCloser(World worldIn, Entity entityIn) {
        for(int i = -4;i<=4;i++) {
            for(int j = -4;j<=4;j++) {
                for(int k = 0;k<256;k++) {
                    pos = new BlockPos(entityIn.getPosX()+i,k,entityIn.getPosZ()+j);
                    if(worldIn.getTileEntity(pos) instanceof WonderTileEntity) {
                        tilePos = pos;
                    }
                }
            }
        }
        if(tilePos!=null) {
            tilePos=null;
            pos=null;
            return true;
        }
        else {
            pos=null;
            return false;
        }
    }

    public boolean checkForWonderClosest(World worldIn, Entity entityIn) {
        for(int i = -2;i<=2;i++) {
            for(int j = -2;j<=2;j++) {
                for(int k = 0;k<256;k++) {
                    pos = new BlockPos(entityIn.getPosX()+i,k,entityIn.getPosZ()+j);
                    if(worldIn.getTileEntity(pos) instanceof WonderTileEntity) {
                        tilePos = pos;
                    }
                }
            }
        }
        if(tilePos!=null) {
            tilePos=null;
            pos=null;
            return true;
        }
        else {
            pos=null;
            return false;
        }
    }
}
