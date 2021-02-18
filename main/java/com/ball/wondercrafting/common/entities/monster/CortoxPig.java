package com.ball.wondercrafting.common.entities.monster;

import com.ball.wondercrafting.common.entities.ai.goals.BlightMeleeAttackGoal;
import com.ball.wondercrafting.core.init.EntityInit;
import com.ball.wondercrafting.core.init.PotionInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
public class CortoxPig extends CreatureEntity {
    public CortoxPig(EntityType<? extends CortoxPig> type, World worldIn){

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
        this.goalSelector.addGoal(1, new CortoxPig.AttackGoal(this));
        this.goalSelector.addGoal(2,new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute func_234215_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.5D);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        Direction direction = this.getAdjustedHorizontalFacing();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.func_230268_c_(livingEntity);
        } else {
            int[][] aint = TransportationHelper.func_234632_a_(direction);
            BlockPos blockpos = this.getPosition();
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for(Pose pose : livingEntity.getAvailablePoses()) {
                AxisAlignedBB axisalignedbb = livingEntity.getPoseAABB(pose);

                for(int[] aint1 : aint) {
                    blockpos$mutable.setPos(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
                    double d0 = this.world.func_242403_h(blockpos$mutable);
                    if (TransportationHelper.func_234630_a_(d0)) {
                        Vector3d vector3d = Vector3d.copyCenteredWithVerticalOffset(blockpos$mutable, d0);
                        if (TransportationHelper.func_234631_a_(this.world, livingEntity, axisalignedbb.offset(vector3d))) {
                            livingEntity.setPose(pose);
                            return vector3d;
                        }
                    }
                }
            }

            return super.func_230268_c_(livingEntity);
        }
    }

    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(CortoxPig pig) {
            super(pig, 1.25D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(2.0F + attackTarget.getWidth());
        }
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    public boolean isEntityCor() {
        return true;
    }

    public void func_241841_a(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
        if (p_241841_1_.getDifficulty() != Difficulty.PEACEFUL && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, EntityType.ZOMBIFIED_PIGLIN, (timer) -> {})) {
            ZombifiedPiglinEntity zombifiedpiglinentity = EntityType.ZOMBIFIED_PIGLIN.create(p_241841_1_);
            zombifiedpiglinentity.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            zombifiedpiglinentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            zombifiedpiglinentity.setNoAI(this.isAIDisabled());
            zombifiedpiglinentity.setChild(this.isChild());
            if (this.hasCustomName()) {
                zombifiedpiglinentity.setCustomName(this.getCustomName());
                zombifiedpiglinentity.setCustomNameVisible(this.isCustomNameVisible());
            }

            zombifiedpiglinentity.enablePersistence();
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, zombifiedpiglinentity);
            p_241841_1_.addEntity(zombifiedpiglinentity);
            this.remove();
        } else {
            super.func_241841_a(p_241841_1_, p_241841_2_);
        }

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
