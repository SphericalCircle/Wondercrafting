package com.ball.wondercrafting.common.entities.monster;

import com.ball.wondercrafting.common.entities.ai.goals.SpreadCorSheepGoal;
import com.ball.wondercrafting.core.init.EntityInit;
import com.ball.wondercrafting.core.init.PotionInit;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class CortoxSheep extends AnimalEntity implements IShearable, net.minecraftforge.common.IForgeShearable {
    private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.createKey(com.ball.wondercrafting.common.entities.monster.CortoxSheep.class, DataSerializers.BYTE);
    private static final Map<DyeColor, IItemProvider> WOOL_BY_COLOR = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
        p_203402_0_.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        p_203402_0_.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        p_203402_0_.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        p_203402_0_.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        p_203402_0_.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        p_203402_0_.put(DyeColor.LIME, Blocks.LIME_WOOL);
        p_203402_0_.put(DyeColor.PINK, Blocks.PINK_WOOL);
        p_203402_0_.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        p_203402_0_.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        p_203402_0_.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        p_203402_0_.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        p_203402_0_.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        p_203402_0_.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        p_203402_0_.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        p_203402_0_.put(DyeColor.RED, Blocks.RED_WOOL);
        p_203402_0_.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });
    /** Map from EnumDyeColor to RGB values for passage to GlStateManager.color() */
    private static final Map<DyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((DyeColor p_200204_0_) -> {
        return p_200204_0_;
    }, com.ball.wondercrafting.common.entities.monster.CortoxSheep::createSheepColor)));
    private int sheepTimer;
    private SpreadCorSheepGoal eatGrassGoal;

    private static float[] createSheepColor(DyeColor dyeColorIn) {
        if (dyeColorIn == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] afloat = dyeColorIn.getColorComponentValues();
            float f = 0.75F;
            return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static float[] getDyeRgb(DyeColor dyeColor) {
        return DYE_TO_RGB.get(dyeColor);
    }

    public CortoxSheep(EntityType<? extends com.ball.wondercrafting.common.entities.monster.CortoxSheep> type, World worldIn) {
        super(type, worldIn);
        this.experienceValue = 2;
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new SpreadCorSheepGoal(this);
        this.goalSelector.addGoal(0,new SwimGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 1.0d));
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PigEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, CowEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SheepEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WolfEntity.class, false));
        this.goalSelector.addGoal(1, new CortoxSheep.AttackGoal(this));
        this.goalSelector.addGoal(3, this.eatGrassGoal);
        this.goalSelector.addGoal(3,new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
    }

    protected void updateAITasks() {
        this.sheepTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    public void livingTick() {
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.livingTick();
    }

    public static AttributeModifierMap.MutableAttribute func_234215_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(CortoxSheep sheep) {
            super(sheep, 1.25D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(2.0F + attackTarget.getWidth());
        }
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(DYE_COLOR, (byte)0);
    }

    public ResourceLocation getLootTable() {
        if (this.getSheared()) {
            return this.getType().getLootTable();
        } else {
            switch(this.getFleeceColor()) {
                case WHITE:
                default:
                    return LootTables.ENTITIES_SHEEP_WHITE;
                case ORANGE:
                    return LootTables.ENTITIES_SHEEP_ORANGE;
                case MAGENTA:
                    return LootTables.ENTITIES_SHEEP_MAGENTA;
                case LIGHT_BLUE:
                    return LootTables.ENTITIES_SHEEP_LIGHT_BLUE;
                case YELLOW:
                    return LootTables.ENTITIES_SHEEP_YELLOW;
                case LIME:
                    return LootTables.ENTITIES_SHEEP_LIME;
                case PINK:
                    return LootTables.ENTITIES_SHEEP_PINK;
                case GRAY:
                    return LootTables.ENTITIES_SHEEP_GRAY;
                case LIGHT_GRAY:
                    return LootTables.ENTITIES_SHEEP_LIGHT_GRAY;
                case CYAN:
                    return LootTables.ENTITIES_SHEEP_CYAN;
                case PURPLE:
                    return LootTables.ENTITIES_SHEEP_PURPLE;
                case BLUE:
                    return LootTables.ENTITIES_SHEEP_BLUE;
                case BROWN:
                    return LootTables.ENTITIES_SHEEP_BROWN;
                case GREEN:
                    return LootTables.ENTITIES_SHEEP_GREEN;
                case RED:
                    return LootTables.ENTITIES_SHEEP_RED;
                case BLACK:
                    return LootTables.ENTITIES_SHEEP_BLACK;
            }
        }
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.sheepTimer <= 0) {
            return 0.0F;
        } else if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
            return 1.0F;
        } else {
            return this.sheepTimer < 4 ? ((float)this.sheepTimer - p_70894_1_) / 4.0F : -((float)(this.sheepTimer - 40) - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * ((float)Math.PI / 180F);
        }
    }

    public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
        if (false && itemstack.getItem() == Items.SHEARS) { //Forge: Moved to onSheared
            if (!this.world.isRemote && this.isShearable()) {
                this.shear(SoundCategory.PLAYERS);
                itemstack.damageItem(1, p_230254_1_, (p_213613_1_) -> {
                    p_213613_1_.sendBreakAnimation(p_230254_2_);
                });
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.CONSUME;
            }
        } else {
            return super.func_230254_b_(p_230254_1_, p_230254_2_);
        }
    }

    public void shear(SoundCategory category) {
        this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_SHEEP_SHEAR, category, 1.0F, 1.0F);
        this.setSheared(true);
        int i = 1 + this.rand.nextInt(3);

        for(int j = 0; j < i; ++j) {
            ItemEntity itementity = this.entityDropItem(WOOL_BY_COLOR.get(this.getFleeceColor()), 1);
            if (itementity != null) {
                itementity.setMotion(itementity.getMotion().add((double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double)(this.rand.nextFloat() * 0.05F), (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
            }
        }

    }

    public boolean isShearable() {
        return false;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Sheared", this.getSheared());
        compound.putByte("Color", (byte)this.getFleeceColor().getId());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setFleeceColor(DyeColor.byId(compound.getByte("Color")));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    /**
     * Gets the wool color of this sheep.
     */
    public DyeColor getFleeceColor() {
        return DyeColor.byId(this.dataManager.get(DYE_COLOR) & 15);
    }

    /**
     * Sets the wool color of this sheep
     */
    public void setFleeceColor(DyeColor color) {
        byte b0 = this.dataManager.get(DYE_COLOR);
        this.dataManager.set(DYE_COLOR, (byte)(b0 & 240 | color.getId() & 15));
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean getSheared() {
        return (this.dataManager.get(DYE_COLOR) & 16) != 0;
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean sheared) {
        byte b0 = this.dataManager.get(DYE_COLOR);
        if (sheared) {
            this.dataManager.set(DYE_COLOR, (byte)(b0 | 16));
        } else {
            this.dataManager.set(DYE_COLOR, (byte)(b0 & -17));
        }

    }

    /**
     * Chooses a "vanilla" sheep color based on the provided random.
     */
    public static DyeColor getRandomSheepColor(Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return DyeColor.BLACK;
        } else if (i < 10) {
            return DyeColor.GRAY;
        } else if (i < 15) {
            return DyeColor.LIGHT_GRAY;
        } else if (i < 18) {
            return DyeColor.BROWN;
        } else {
            return random.nextInt(500) == 0 ? DyeColor.PINK : DyeColor.WHITE;
        }
    }

    public void eatGrassBonus() {
        this.setSheared(false);

    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setFleeceColor(getRandomSheepColor(worldIn.getRandom()));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean isShearable(@javax.annotation.Nonnull ItemStack item, World world, BlockPos pos) {
        return isShearable();
    }

    @javax.annotation.Nonnull
    @Override
    public java.util.List<ItemStack> onSheared(@Nullable PlayerEntity player, @javax.annotation.Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isRemote) {
            this.setSheared(true);
            int i = 1 + this.rand.nextInt(3);

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            for (int j = 0; j < i; ++j) {
                items.add(new ItemStack(WOOL_BY_COLOR.get(this.getFleeceColor())));
            }
            return items;
        }
        return java.util.Collections.emptyList();
    }

    private static CraftingInventory createDyeColorCraftingInventory(DyeColor color, DyeColor color1) {
        CraftingInventory craftinginventory = new CraftingInventory(new Container((ContainerType)null, -1) {
            /**
             * Determines whether supplied player can use this container
             */
            public boolean canInteractWith(PlayerEntity playerIn) {
                return false;
            }
        }, 2, 1);
        craftinginventory.setInventorySlotContents(0, new ItemStack(DyeItem.getItem(color)));
        craftinginventory.setInventorySlotContents(1, new ItemStack(DyeItem.getItem(color1)));
        return craftinginventory;
    }

    private DyeColor getDyeColorMixFromParents(AnimalEntity father, AnimalEntity mother) {
        DyeColor dyecolor = ((SheepEntity)father).getFleeceColor();
        DyeColor dyecolor1 = ((SheepEntity)mother).getFleeceColor();
        CraftingInventory craftinginventory = createDyeColorCraftingInventory(dyecolor, dyecolor1);
        return this.world.getRecipeManager().getRecipe(IRecipeType.CRAFTING, craftinginventory, this.world).map((p_213614_1_) -> {
            return p_213614_1_.getCraftingResult(craftinginventory);
        }).map(ItemStack::getItem).filter(DyeItem.class::isInstance).map(DyeItem.class::cast).map(DyeItem::getDyeColor).orElseGet(() -> {
            return this.world.rand.nextBoolean() ? dyecolor : dyecolor1;
        });
    }

    public SheepEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        SheepEntity sheepentity = (SheepEntity)p_241840_2_;
        SheepEntity sheepentity1 = EntityType.SHEEP.create(p_241840_1_);
        sheepentity1.setFleeceColor(this.getDyeColorMixFromParents(this, sheepentity));
        return sheepentity1;
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
