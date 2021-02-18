package com.ball.wondercrafting.common.entities.monster;

import com.ball.wondercrafting.common.blocks.EpiphanyBloom;
import com.ball.wondercrafting.common.entities.ai.goals.BlightMeleeAttackGoal;
import com.ball.wondercrafting.core.init.BlockInit;
import com.ball.wondercrafting.core.init.EntityInit;
import com.ball.wondercrafting.core.init.PotionInit;
import com.ball.wondercrafting.core.init.SoundInit;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("unchecked")
public class BlightWeta extends CreatureEntity {
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    private int carrotTicks;

    public BlightWeta(EntityType<? extends BlightWeta> type, World worldIn) {
        super(type, worldIn);
        this.experienceValue = 7;
        this.jumpController = new BlightWeta.JumpHelperController(this);
        this.moveController = new BlightWeta.MoveHelperController(this);
        this.setMovementSpeed(0.0D);
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(5, new BlightWeta.RaidFarmGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(1, new BlightWeta.EvilAttackGoal(this));
        this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PigEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, CowEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SheepEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WolfEntity.class, false));
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    protected float getJumpUpwardsMotion() {
        if (!this.collidedHorizontally && (!this.moveController.isUpdating() || !(this.moveController.getY() > this.getPosY() + 0.5D))) {
            Path path = this.navigator.getPath();
            if (path != null && !path.isFinished()) {
                Vector3d vector3d = path.getPosition(this);
                if (vector3d.y > this.getPosY() + 0.5D) {
                    return 0.6F;
                }
            }

            return this.moveController.getSpeed() <= 0.6D ? 0.3F : 0.6F;
        } else {
            return 0.6F;
        }
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump() {
        super.jump();
        double d0 = this.moveController.getSpeed();
        if (d0 > 0.0D) {
            double d1 = horizontalMag(this.getMotion());
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vector3d(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float p_175521_1_) {
        return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + p_175521_1_) / (float)this.jumpDuration;
    }

    public void setMovementSpeed(double newSpeed) {
        this.getNavigator().setSpeed(newSpeed);
        this.moveController.setMoveTo(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ(), newSpeed);
    }

    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    protected void registerData() {
        super.registerData();
    }

    public void updateAITasks() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }

        if (this.carrotTicks > 0) {
            this.carrotTicks -= this.rand.nextInt(3);
            if (this.carrotTicks < 0) {
                this.carrotTicks = 0;
            }
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            if (this.currentMoveTypeDuration == 0) {
                LivingEntity livingentity = this.getAttackTarget();
                if (livingentity != null && this.getDistanceSq(livingentity) < 16.0D) {
                    this.calculateRotationYaw(livingentity.getPosX(), livingentity.getPosZ());
                    this.moveController.setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), this.moveController.getSpeed());
                    this.startJumping();
                    this.wasOnGround = true;
                }
            }

            BlightWeta.JumpHelperController blightweta$jumphelpercontroller = (BlightWeta.JumpHelperController)this.jumpController;
            if (!blightweta$jumphelpercontroller.getIsJumping()) {
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vector3d vector3d = new Vector3d(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ());
                    if (path != null && !path.isFinished()) {
                        vector3d = path.getPosition(this);
                    }

                    this.calculateRotationYaw(vector3d.x, vector3d.z);
                    this.startJumping();
                }
            } else if (!blightweta$jumphelpercontroller.canJump()) {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    public boolean shouldSpawnRunningEffects() {
        return false;
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float)(MathHelper.atan2(z - this.getPosZ(), x - this.getPosX()) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    private void enableJumpControl() {
        ((BlightWeta.JumpHelperController)this.jumpController).setCanJump(true);
    }

    private void disableJumpControl() {
        ((BlightWeta.JumpHelperController)this.jumpController).setCanJump(false);
    }

    private void updateMoveTypeDuration() {
        if (this.moveController.getSpeed() < 2.2D) {
            this.currentMoveTypeDuration = 10;
        } else {
            this.currentMoveTypeDuration = 1;
        }

    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    public void livingTick() {
        super.livingTick();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }

    }

    public static AttributeModifierMap.MutableAttribute func_234224_eJ_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.4F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
    }

    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn).scale(0.5F);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
    }

    protected boolean isDespawnPeaceful() {
        return true;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundInit.BLIGHTWETA_HIT.get();
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.BLIGHTWETA_HIT.get();
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : super.attackEntityFrom(source, amount);
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    public static boolean canMonsterSpawn(EntityType<BlightWeta> type, IServerWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState blockstate = world.getBlockState(pos.down());
        return blockstate==BlockInit.CORGROWTH.get().getDefaultState() || blockstate==BlockInit.CORGROWTH_FERTILE.get().getDefaultState() || blockstate==BlockInit.CORSAND.get().getDefaultState();
    }


    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            this.handleRunningEffect();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    static class EvilAttackGoal extends BlightMeleeAttackGoal {
        public EvilAttackGoal(BlightWeta weta) {
            super(weta, 2.0D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(2.0F + attackTarget.getWidth());
        }
    }

    public class JumpHelperController extends JumpController {
        private final BlightWeta weta;
        private boolean canJump;

        public JumpHelperController(BlightWeta weta) {
            super(weta);
            this.weta = weta;
        }

        public boolean getIsJumping() {
            return this.isJumping;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        public void tick() {
            if (this.isJumping) {
                this.weta.startJumping();
                this.isJumping = false;
            }

        }
    }

    static class MoveHelperController extends MovementController {
        private final BlightWeta weta;
        private double nextJumpSpeed;

        public MoveHelperController(BlightWeta weta) {
            super(weta);
            this.weta = weta;
        }

        public void tick() {
            if (this.weta.onGround && !this.weta.isJumping && !((BlightWeta.JumpHelperController)this.weta.jumpController).getIsJumping()) {
                this.weta.setMovementSpeed(0.0D);
            } else if (this.isUpdating()) {
                this.weta.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        /**
         * Sets the speed and location to move to
         */
        public void setMoveTo(double x, double y, double z, double speedIn) {
            if (this.weta.isInWater()) {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }

        }
    }

    static class RaidFarmGoal extends MoveToBlockGoal {
        private final BlightWeta weta;
        private boolean wantsToRaid;
        private boolean canRaid;

        public RaidFarmGoal(BlightWeta wetaIn) {
            super(wetaIn, (double)0.7F, 16);
            this.weta = wetaIn;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            if (this.runDelay <= 0) {
                if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.weta.world, this.weta)) {
                    return false;
                }

                this.canRaid = false;
                this.wantsToRaid = true;
            }

            return super.shouldExecute();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.canRaid && super.shouldContinueExecuting();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            super.tick();
            this.weta.getLookController().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, 0.0f);
            if (this.getIsAboveDestination()) {
                World world = this.weta.world;
                BlockPos blockpos = this.destinationBlock.up();
                BlockState blockstate = world.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (this.canRaid && (block instanceof CarrotBlock||block instanceof PotatoBlock||block instanceof BeetrootBlock||block instanceof CropsBlock||block instanceof EpiphanyBloom)) {
                    world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                    world.destroyBlock(blockpos, true, this.weta);

                    this.weta.carrotTicks = 40;
                }

                this.canRaid = false;
                this.runDelay = 10;
            }

        }

        /**
         * Return true to set given position as destination
         */
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            Block block = worldIn.getBlockState(pos).getBlock();
            if (this.wantsToRaid && !this.canRaid) {
                pos = pos.up();
                BlockState blockstate = worldIn.getBlockState(pos);
                block = blockstate.getBlock();
                if ((block instanceof CropsBlock||block instanceof EpiphanyBloom)) {
                    this.canRaid = true;
                    return true;
                }
            }

            return false;
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        this.playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.3F);
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof LivingEntity) {
                int i = 0;
                int j = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 6;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 10;
                    j = 1;
                } else if (this.world.getDifficulty() == Difficulty.EASY) {
                    i = 3;
                }

                if (i > 0) {
                    ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(PotionInit.COR_TOXIN.get(), i * 100, j));
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
