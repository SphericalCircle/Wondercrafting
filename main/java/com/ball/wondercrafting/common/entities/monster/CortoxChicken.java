package com.ball.wondercrafting.common.entities.monster;

import com.ball.wondercrafting.core.init.EntityInit;
import com.ball.wondercrafting.core.init.PotionInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@SuppressWarnings("unchecked")
public class CortoxChicken extends CreatureEntity {
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;
    public int timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
    public CortoxChicken(EntityType<? extends CortoxChicken> type, World worldIn){

        super(type, worldIn);
        this.experienceValue = 2;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new SwimGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 1.0d));
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PigEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, CowEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SheepEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WolfEntity.class, false));
        this.goalSelector.addGoal(1, new CortoxChicken.AttackGoal(this));
        this.goalSelector.addGoal(2,new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute func_234215_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 4.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    public void livingTick() {
        super.livingTick();
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
        if (!this.onGround && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
        Vector3d vector3d = this.getMotion();
        if (!this.onGround && vector3d.y < 0.0D) {
            this.setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
        }

        this.wingRotation += this.wingRotDelta * 2.0F;
        if (!this.world.isRemote && this.isAlive() && --this.timeUntilNextEgg <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.entityDropItem(Items.EGG);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }

    }

    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(CortoxChicken chicken) {
            super(chicken, 1.4D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(2.0F + attackTarget.getWidth());
        }
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("EggLayTime")) {
            this.timeUntilNextEgg = compound.getInt("EggLayTime");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("EggLayTime", this.timeUntilNextEgg);
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    public boolean isEntityCor() {
        return true;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof LivingEntity) {
                int i = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 6;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 10;
                } else if (this.world.getDifficulty() == Difficulty.EASY) {
                    i = 3;
                }

                if (i > 0) {
                    ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(PotionInit.COR_TOXIN.get(), i * 100, 0));
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public void func_241847_a(ServerWorld p_241847_1_, LivingEntity p_241847_2_) {
        super.func_241847_a(p_241847_1_, p_241847_2_);
        if ((p_241847_1_.getDifficulty() == Difficulty.NORMAL || p_241847_1_.getDifficulty() == Difficulty.HARD || p_241847_1_.getDifficulty() == Difficulty.EASY)
                && p_241847_2_ instanceof PigEntity && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(p_241847_2_, EntityInit.CORTOX_PIG.get(), (timer) -> {})) {
            if (p_241847_1_.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean()) {
                return;
            }

            PigEntity pigentity = (PigEntity)p_241847_2_;
            CortoxPig cortoxpig = pigentity.func_233656_b_(EntityInit.CORTOX_PIG.get(), false);
            cortoxpig.onInitialSpawn(p_241847_1_, p_241847_1_.getDifficultyForLocation(cortoxpig.getPosition()), SpawnReason.CONVERSION, new ZombieEntity.GroupData(false, true), (CompoundNBT)null);
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(p_241847_2_, cortoxpig);
        }
        if ((p_241847_1_.getDifficulty() == Difficulty.NORMAL || p_241847_1_.getDifficulty() == Difficulty.HARD || p_241847_1_.getDifficulty() == Difficulty.EASY)
                && p_241847_2_ instanceof CowEntity && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(p_241847_2_, EntityInit.CORTOX_PIG.get(), (timer) -> {})) {
            if (p_241847_1_.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean()) {
                return;
            }

            CowEntity cowentity = (CowEntity)p_241847_2_;
            CortoxCow cortoxcow = cowentity.func_233656_b_(EntityInit.CORTOX_COW.get(), false);
            cortoxcow.onInitialSpawn(p_241847_1_, p_241847_1_.getDifficultyForLocation(cortoxcow.getPosition()), SpawnReason.CONVERSION, new ZombieEntity.GroupData(false, true), (CompoundNBT)null);
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(p_241847_2_, cortoxcow);
        }
        if ((p_241847_1_.getDifficulty() == Difficulty.NORMAL || p_241847_1_.getDifficulty() == Difficulty.HARD || p_241847_1_.getDifficulty() == Difficulty.EASY)
                && p_241847_2_ instanceof ChickenEntity && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(p_241847_2_, EntityInit.CORTOX_PIG.get(), (timer) -> {})) {
            if (p_241847_1_.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean()) {
                return;
            }

            ChickenEntity chickenentity = (ChickenEntity)p_241847_2_;
            CortoxChicken cortoxchicken = chickenentity.func_233656_b_(EntityInit.CORTOX_CHICKEN.get(), false);
            cortoxchicken.onInitialSpawn(p_241847_1_, p_241847_1_.getDifficultyForLocation(cortoxchicken.getPosition()), SpawnReason.CONVERSION, new ZombieEntity.GroupData(false, true), (CompoundNBT)null);
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(p_241847_2_, cortoxchicken);
        }
        if ((p_241847_1_.getDifficulty() == Difficulty.NORMAL || p_241847_1_.getDifficulty() == Difficulty.HARD || p_241847_1_.getDifficulty() == Difficulty.EASY)
                && p_241847_2_ instanceof SheepEntity && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(p_241847_2_, EntityInit.CORTOX_PIG.get(), (timer) -> {})) {
            if (p_241847_1_.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean()) {
                return;
            }

            SheepEntity sheepentity = (SheepEntity)p_241847_2_;
            CortoxSheep cortoxsheep = sheepentity.func_233656_b_(EntityInit.CORTOX_SHEEP.get(), false);
            cortoxsheep.setSheared(sheepentity.getSheared());
            cortoxsheep.setFleeceColor(sheepentity.getFleeceColor());
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(p_241847_2_, cortoxsheep);
        }
        if ((p_241847_1_.getDifficulty() == Difficulty.NORMAL || p_241847_1_.getDifficulty() == Difficulty.HARD || p_241847_1_.getDifficulty() == Difficulty.EASY)
                && p_241847_2_ instanceof WolfEntity && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(p_241847_2_, EntityInit.CORTOX_PIG.get(), (timer) -> {})) {
            if (p_241847_1_.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean()) {
                return;
            }

            WolfEntity wolfentity = (WolfEntity)p_241847_2_;
            CortoxWolf cortoxwolf = wolfentity.func_233656_b_(EntityInit.CORTOX_WOLF.get(), false);
            cortoxwolf.onInitialSpawn(p_241847_1_, p_241847_1_.getDifficultyForLocation(cortoxwolf.getPosition()), SpawnReason.CONVERSION, new ZombieEntity.GroupData(false, true), (CompoundNBT)null);
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(p_241847_2_, cortoxwolf);
        }

    }
}
