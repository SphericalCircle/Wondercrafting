package com.ball.wondercrafting.common.effects;

import com.ball.wondercrafting.common.entities.monster.*;
import com.ball.wondercrafting.core.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Difficulty;

public class CorToxin extends Effect {

    public CorToxin(){
        super(EffectType.HARMFUL,5047814);
    }


    public void performEffect(LivingEntity entityIn, int amplifier) {
        if (entityIn.getType() != EntityInit.CORTOX_PIG.get()&&entityIn.getType() != EntityInit.CORTOX_COW.get()&&entityIn.getType() != EntityInit.CORTOX_CHICKEN.get()
                &&entityIn.getType() != EntityInit.CORTOX_SHEEP.get()&&entityIn.getType() != EntityInit.CORTOX_WOLF.get()&&entityIn.getType() != EntityInit.BLIGHT_WETA.get()) {
            if (entityIn.getHealth()>1) {
                entityIn.attackEntityFrom(DamageSource.MAGIC,1.0f);
            }
            if (entityIn.getHealth()==1) {
                if (entityIn.getType() == EntityType.PIG) {
                    PigEntity pigentity = (PigEntity)entityIn;
                    CortoxPig cortoxpig = pigentity.func_233656_b_(EntityInit.CORTOX_PIG.get(), false);
                    net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entityIn, cortoxpig);
                }
                else if (entityIn.getType() == EntityType.COW) {
                    CowEntity cowentity = (CowEntity)entityIn;
                    CortoxCow cortoxcow = cowentity.func_233656_b_(EntityInit.CORTOX_COW.get(), false);
                    net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entityIn, cortoxcow);
                }
                else if (entityIn.getType() == EntityType.CHICKEN) {
                    ChickenEntity chickenentity = (ChickenEntity)entityIn;
                    CortoxChicken cortoxchicken = chickenentity.func_233656_b_(EntityInit.CORTOX_CHICKEN.get(), false);
                    net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entityIn, cortoxchicken);
                }
                else if (entityIn.getType() == EntityType.SHEEP) {
                    SheepEntity sheepentity = (SheepEntity)entityIn;
                    CortoxSheep cortoxsheep = sheepentity.func_233656_b_(EntityInit.CORTOX_SHEEP.get(), false);
                    cortoxsheep.setSheared(sheepentity.getSheared());
                    cortoxsheep.setFleeceColor(sheepentity.getFleeceColor());
                    net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entityIn, cortoxsheep);
                }
                else if (entityIn.getType() == EntityType.WOLF) {
                    WolfEntity wolfentity = (WolfEntity) entityIn;
                    CortoxWolf cortoxwolf = wolfentity.func_233656_b_(EntityInit.CORTOX_WOLF.get(), false);
                    net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entityIn, cortoxwolf);
                }
                else {
                    entityIn.attackEntityFrom(DamageSource.MAGIC,1.0f);
                }
            }
        }
        else {
            entityIn.heal(3.0f);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int j = 1;
        if (amplifier>0) {
            j = 200/amplifier;
        }
        else {
            j = 200;
        }
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }
}
