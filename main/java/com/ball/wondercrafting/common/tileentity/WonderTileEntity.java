package com.ball.wondercrafting.common.tileentity;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.core.init.BlockInit;
import com.ball.wondercrafting.core.init.PotionInit;
import com.ball.wondercrafting.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WonderTileEntity extends TileEntity implements ITickableTileEntity {

    public int tick, mys, cor, cho, randomMys, randomCor, randomCho, currentMys, currentCor, currentCho, netEssence, totalEssence, starvingTick;
    public int starvingRadius = 2;
    public boolean initialized, hasMys, hasCor, hasCho;
    public String name = "Wonder";
    Random randombreak = new Random();
    public float rotation, rotationTwo;

    public WonderTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public WonderTileEntity() {
        this(TileEntityInit.WONDER.get());
    }

    @Override
    public void tick() {
        if (this.world.isBlockLoaded(this.getPos())) {
            if (!initialized && !this.world.isRemote()) {
                this.Init(this.getWorld().getRandom());
            }
            tick++;
            if (!this.world.isRemote()) {
                this.markDirty();
                this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 2);
                if (tick % 200 == 0 && totalEssence > 20) {
                    if (currentMys < mys) {
                        if (this.getWorld().getRandom().nextBoolean()) {
                            currentMys++;
                        }
                    }
                    if (currentCor < cor) {
                        if (this.getWorld().getRandom().nextBoolean()) {
                            currentCor++;
                        }
                    }
                    if (currentCho < cho) {
                        if (this.getWorld().getRandom().nextBoolean()) {
                            currentCho++;
                        }
                    }
                }
                if (tick % 250 == 0 && totalEssence >= 500) {
                    if (this.getWorld().getRandom().nextBoolean()) {
                        mys++;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean()) {
                        cor++;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean() && mys > 0) {
                        mys = mys - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean() && cor > 0) {
                        cor = cor - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean()) {
                        if (this.getWorld().getRandom().nextBoolean()) {
                            this.instability();
                            this.getEssenceLevel();
                            this.getName();
                        }
                    }
                }
                if (tick % 250 == 0 && cho >= 190 && cho < 200) {
                    if (this.getWorld().getRandom().nextBoolean()) {
                        cho++;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean() && cho > 0) {
                        cho = cho - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                }
                if (tick % 100 == 0 && cho >= 200) {
                    if (this.getWorld().getRandom().nextBoolean() && mys > 0) {
                        mys = mys - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean() && cor > 0) {
                        cor = cor - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean() && cho > 0) {
                        cho = cho - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean()) {
                        this.instability();
                        this.getEssenceLevel();
                        this.getName();
                    }
                }
                if (tick % 20 == 0 && netEssence >= 150) {
                    if (this.getWorld().getRandom().nextBoolean() && cor > 0) {
                        cor = cor - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                }
                if (tick % 20 == 0 && netEssence >= 200) {
                    if (this.getWorld().getRandom().nextBoolean() && cor > 0) {
                        cor = cor - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean() && cho > 0) {
                        cho = cho - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                }
                if (tick % 20 == 0 && netEssence <= -150) {
                    if (this.getWorld().getRandom().nextBoolean() && mys > 0) {
                        mys = mys - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                }
                if (tick % 20 == 0 && netEssence <= -200) {
                    if (this.getWorld().getRandom().nextBoolean() && mys > 0) {
                        mys = mys - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                    if (this.getWorld().getRandom().nextBoolean() && cho > 0) {
                        cho = cho - 1;
                        this.getEssenceLevel();
                        this.getName();
                    }
                }
                if (tick % 2 == 0 && netEssence >= 150) {
                    executeEpiphany(this.getWorld(), this.getWorld().getRandom());
                }
                if (tick % 100 == 0 && netEssence <= -150 && netEssence > -200) {
                    executeBlight(this.getWorld(), this.getWorld().getRandom());
                }
                if (tick % 10 == 0 && netEssence <= -200) {
                    executeBlight(this.getWorld(), this.getWorld().getRandom());
                }
                if (tick % 100 == 0 && netEssence >= 75) {
                    healing(this.getWorld());
                }
                if (tick % 100 == 0 && netEssence <= -75) {
                    toxic(this.getWorld());
                }
                if (tick % 2 == 0 && cho >= 200) {
                    eatBlocks(starvingRadius, this.getWorld(), this.pos, randombreak);
                    starvingTick++;
                    eatEntities(this.getWorld());
                    if (starvingTick % 400 == 0 && starvingRadius < 8) {
                        starvingRadius++;
                    }
                }
            }
            if (cho >= 200) {
                pull(this.getWorld());
            }
            PlayerEntity playerentity = this.world.getClosestPlayer((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D, 100.0D, false);
            if (playerentity!=null) {
                double d0 = playerentity.getPosX() - ((double) this.pos.getX() + 0.5D);
                double d1 = playerentity.getPosZ() - ((double) this.pos.getZ() + 0.5D);
                double d2 = playerentity.getPosY() - ((double) this.pos.getY() - 1.0D);
                this.rotation = (float)-(MathHelper.atan2(d1, d0) * (180F / Math.PI) + 90.0F);
                double horizontal = (double) MathHelper.sqrt(d0 * d0 + d1 * d1);
                this.rotationTwo = (float)((MathHelper.atan2(d2, horizontal) * (180D / Math.PI)));
            }
            if (tick % 200 == 0) {
                Wondercrafting.LOGGER.debug(name + ": Mys = " + mys + ", Cor = " + cor + ", Cho = " + cho + ", Net Essence Power = " + netEssence + ", Total Essence = " + totalEssence);
            }
            if (tick % 20 == 0) {
                Wondercrafting.LOGGER.debug("Current Mys = " + currentMys + ", Current Cor = " + currentCor + ", Current Cho = " + currentCho);
            }
        }
    }

    private void Init(Random rand) {
        initialized = true;
        int random = (int) (Math.random() * 100) + 1;
        if (random <= 10) {
            randomMys = (int) (Math.random() * 20) + 1;
            randomCor = (int) (Math.random() * 20) + 1;
            randomCho = (int) (Math.random() * 20) + 1;
        }
        if (random > 10 && random <= 50) {
            randomMys = (int) (Math.random() * 75) + 1;
            randomCor = (int) (Math.random() * 75) + 1;
            randomCho = (int) (Math.random() * 75) + 1;
        }
        if (random > 50 && random <= 75) {
            randomMys = (int) (Math.random() * 150) + 1;
            randomCor = (int) (Math.random() * 150) + 1;
            randomCho = (int) (Math.random() * 150) + 1;
        }
        if (random > 75 && random <= 99) {
            randomMys = (int) (Math.random() * 300) + 1;
            randomCor = (int) (Math.random() * 300) + 1;
            randomCho = (int) (Math.random() * 300) + 1;
        }
        if (random == 100) {
            randomMys = (int) (Math.random() * 500) + 1;
            randomCor = (int) (Math.random() * 500) + 1;
            randomCho = (int) (Math.random() * 500) + 1;
        }
        hasMys = rand.nextBoolean();
        hasCor = rand.nextBoolean();
        if (rand.nextBoolean()) hasCho = rand.nextBoolean();
        if (hasMys == hasCor && hasMys == hasCho && !hasMys) hasMys = true;
            if (random <= 10) {
                if (hasMys) mys = randomMys;
                if (hasCor) cor = randomCor;
                if (hasCho) cho = randomCho;
            }
            if (random > 10 && random <= 50) {
                if (hasMys) mys = randomMys;
                if (hasCor) cor = randomCor;
                if (hasCho) cho = randomCho;
            }
            if (random > 50 && random <= 75) {
                if (hasMys) mys = randomMys;
                if (hasCor) cor = randomCor;
                if (hasCho) cho = randomCho;
            }
            if (random > 75 && random <= 99) {
                if (hasMys) mys = randomMys;
                if (hasCor) cor = randomCor;
                if (hasCho) cho = randomCho;
            }
            if (random == 100) {
                if (hasMys) mys = randomMys;
                if (hasCor) cor = randomCor;
                if (hasCho) cho = randomCho;
            }
            this.getEssenceLevel();
            this.getName();
            currentMys = mys;
            currentCor = cor;
            currentCho = cho;
            this.markDirty();
        Wondercrafting.LOGGER.debug(name+": Mys = "+mys+", Cor = "+cor+", Cho = "+cho+", Net Essence Power = "+netEssence+", Total Essence = "+totalEssence);
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        compound.putBoolean("Initialized", initialized);
        compound.putInt("Mys", this.mys);
        compound.putInt("Cor", this.cor);
        compound.putInt("Cho", this.cho);
        compound.putInt("NetEssence", this.netEssence);
        compound.putInt("TotalEssence", this.totalEssence);
        compound.putInt("CurrentMys", this.currentMys);
        compound.putInt("CurrentCor", this.currentCor);
        compound.putInt("CurrentCho", this.currentCho);
        compound.putInt("StarvingRadius", this.starvingRadius);
        compound.putString("Name", name);

        return compound;
    }

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.initialized = nbt.getBoolean("Initialized");
        this.mys = nbt.getInt("Mys");
        this.cor = nbt.getInt("Cor");
        this.cho = nbt.getInt("Cho");
        this.netEssence = nbt.getInt("NetEssence");
        this.totalEssence = nbt.getInt("TotalEssence");
        this.currentMys = nbt.getInt("CurrentMys");
        this.currentCor = nbt.getInt("CurrentCor");
        this.currentCho = nbt.getInt("CurrentCho");
        this.starvingRadius = nbt.getInt("StarvingRadius");
        this.name = nbt.getString("Name");

    }

    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        this.read(world.getBlockState(packet.getPos()), packet.getNbtCompound());
    }


    private void getEssenceLevel() {
        netEssence = mys-cor;
        totalEssence = mys+cor+cho;
    }

    private void getName() {
        if (totalEssence<=20) {
            name = "Dead Wonder";
        }
        if (cho>=50&&cho<100) {
            name = "Combustive Wonder";
        } else if (cho>=100&&cho<200) {
            name = "Volatile Wonder";
        }
        if (netEssence>=150&&netEssence<200) {
            name = "Pure Wonder";
            if(cho>=50&&cho<100) {
                name = "Combustive Pure Wonder";
            } else if(cho>=100&&cho<200) {
                name = "Volatile Pure Wonder";
            }
        }
        if (netEssence<=-150&&netEssence>-200) {
            name = "Impure Wonder";
            if(cho>=50&&cho<100) {
                name = "Combustive Impure Wonder";
            } else if(cho>=100&&cho<200) {
                name = "Volatile Impure Wonder";
            }
        }
        if (totalEssence>=200&&totalEssence<500) {
            if (Math.abs(netEssence)<200) {
                name = "Bright Wonder";
                if(cho>=50&&cho<100) {
                    name = "Combustive Bright Wonder";
                    if (netEssence<=-150&&netEssence>-200) {
                        name = "Impure Combustive Bright Wonder";
                    }
                    if (netEssence>=150&&netEssence<200) {
                        name = "Pure Combustive Bright Wonder";

                    }
                } else if(cho>=100&&cho<200) {
                    name = "Volatile Bright Wonder";
                    if (netEssence<=-150&&netEssence>-200) {
                        name = "Impure Volatile Bright Wonder";
                    }
                    if (netEssence>=150&&netEssence<200) {
                        name = "Pure Volatile Bright Wonder";

                    }
                } else if(cho>=200) {
                    name = "Starving Wonder";
                    if (netEssence<=-150&&netEssence>-200) {
                        name = "Impure Starving Wonder";
                    }
                    if (netEssence>=150&&netEssence<200) {
                        name = "Pure Starving Wonder";

                    }
                }
            } else if (netEssence>=200) {
                name = "Epiphany";
                if(cho>=50&&cho<100) {
                    name = "Combustive Epiphany";
                } else if(cho>=100&&cho<200) {
                    name = "Volatile Epiphany";
                } else if(cho>=200) {
                    name = "Starving Epiphany";
                }
            } else if (netEssence<=-200) {
                name = "Blight";
                if(cho>=50&&cho<100) {
                    name = "Combustive Blight";
                } else if(cho>=100&&cho<200) {
                    name = "Volatile Blight";
                } else if(cho>=200) {
                    name = "Starving Blight";
                }
            }
        }
        if (totalEssence>=500) {
            if (Math.abs(netEssence)<200) {
                name = "Power Wonder";
                if(cho>=50&&cho<100) {
                    name = "Combustive Power Wonder";
                    if (netEssence<=-150&&netEssence>-200) {
                        name = "Impure Combustive Power Wonder";
                    }
                    if (netEssence>=150&&netEssence<200) {
                        name = "Pure Combustive Power Wonder";

                    }
                } else if(cho>=100&&cho<200) {
                    name = "Volatile Power Wonder";
                    if (netEssence<=-150&&netEssence>-200) {
                        name = "Impure Volatile Power Wonder";
                    }
                    if (netEssence>=150&&netEssence<200) {
                        name = "Pure Volatile Power Wonder";

                    }
                } else if(cho>=200) {
                    name = "Starving Power Wonder";
                    if (netEssence<=-150&&netEssence>-200) {
                        name = "Impure Starving Power Wonder";
                    }
                    if (netEssence>=150&&netEssence<200) {
                        name = "Pure Starving Power Wonder";

                    }
                }
            } else if (netEssence>=200) {
                name = "Power Epiphany";
                if(cho>=50&&cho<100) {
                    name = "Combustive Power Epiphany";
                } else if(cho>=100&&cho<200) {
                    name = "Volatile Power Epiphany";
                } else if(cho>=200) {
                    name = "Starving Power Epiphany";
                }
            } else if (netEssence<=-200) {
                name = "Power Blight";
                if(cho>=50&&cho<100) {
                    name = "Combustive Power Blight";
                } else if(cho>=100&&cho<200) {
                    name = "Volatile Power Blight";
                } else if(cho>=200) {
                    name = "Starving Power Blight";
                }
            }
        }

    }

    private void instability() {
        if(hasMys&&hasCor) {
            mys = mys-1;
            currentMys = currentMys - 1;
            cor = cor-1;
            currentCor = currentCor - 1;
            cho = cho+1;
            currentCho = currentCho + 1;
        }
    }

    private boolean getInCircle (int radius, BlockPos center, BlockPos point) {
        return (Math.pow(point.getX()-center.getX(),2)+ Math.pow((point.getY()-center.getY()),2)+ Math.pow((point.getZ()-center.getZ()),2) <= Math.pow(radius,2));
    }

    private void eatBlocks (int radius, World worldIn, BlockPos pos, Random random) {
        for (int i = 0; i < 6; ++i) {
            BlockPos blockpos = pos.add(random.nextInt((radius*2)+1) - radius, random.nextInt((radius*2)+1) - radius, random.nextInt((radius*2)+1) - radius);
            if (getInCircle(radius, pos, blockpos)&&blockpos!=pos) {
                worldIn.destroyBlock(blockpos, true);
            }
        }
    }

    private void toxic(World worldIn) {
        List<Entity> poisonList = worldIn.getEntitiesWithinAABB(LivingEntity.class, (new AxisAlignedBB((double)this.getPos().getX()+10, (double)this.getPos().getY()+5, (double)this.getPos().getZ()+10,(double)this.getPos().getX()-10, (double)this.getPos().getY()-1, (double)this.getPos().getZ()-10)));
        for (int i = 0; i < poisonList.size(); i++) {
            Entity entityIn = poisonList.get(i);
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(PotionInit.COR_TOXIN.get(),900,0));
        }

    }

    private void pull(World worldIn) {
        List<Entity> pullList = worldIn.getEntitiesWithinAABB(Entity.class, (new AxisAlignedBB((double)this.getPos().getX()+10, (double)this.getPos().getY()+10, (double)this.getPos().getZ()+10,(double)this.getPos().getX()-10, (double)this.getPos().getY()-10, (double)this.getPos().getZ()-10)));
        for (int i = 0; i < pullList.size(); i++) {
            Entity entityIn = pullList.get(i);
            double velX;
            double velY;
            double velZ;
            if ((entityIn.getPosX()-this.getPos().getX()<-1||entityIn.getPosX()-this.getPos().getX()>1)) {velX = 0.05*(-1/(entityIn.getPosX()-this.getPos().getX()-0.5));}

            else if (entityIn.getPosX()>=this.getPos().getX()+0.5) {velX=-0.3;}

            else {velX = 0.1;}

            if ((entityIn.getPosY()-this.getPos().getY()<-1||entityIn.getPosY()-this.getPos().getY()>1)) {velY = 0.1*(-(entityIn.getPosY()-this.getPos().getY()-0.5));}

            else if (entityIn.getPosY()>=this.getPos().getY()+0.5) {velY=-0.1;}

            else {velY = 0.1;}

            if ((entityIn.getPosZ()-this.getPos().getZ()<-1||entityIn.getPosZ()-this.getPos().getZ()>1)) {velZ = 0.05*(-1/(entityIn.getPosZ()-this.getPos().getZ()-0.5));}

            else if (entityIn.getPosZ()>=this.getPos().getZ()+0.5) {velZ=-0.1;}

            else {velZ = 0.1;}

            entityIn.addVelocity(velX, velY, velZ);
        }

    }


    private void eatEntities(World worldIn) {
        List<Entity> eatList = worldIn.getEntitiesWithinAABB(Entity.class, (new AxisAlignedBB((double)this.getPos().getX()+1, (double)this.getPos().getY()+1, (double)this.getPos().getZ()+1,(double)this.getPos().getX()-1, (double)this.getPos().getY()-1, (double)this.getPos().getZ()-1)));
        for (int i = 0; i < eatList.size(); i++) {
            Entity entityIn = eatList.get(i);
            if(entityIn instanceof ItemEntity&&tick%10==0) {
                cho++;
                currentCho++;
                this.markDirty();
            }
            entityIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
        }

    }

    private void healing(World worldIn) {
        List<Entity> poisonList = worldIn.getEntitiesWithinAABB(LivingEntity.class, (new AxisAlignedBB((double)this.getPos().getX()+10, (double)this.getPos().getY()+5, (double)this.getPos().getZ()+10,(double)this.getPos().getX()-10, (double)this.getPos().getY()-1, (double)this.getPos().getZ()-10)));
        for (int i = 0; i < poisonList.size(); i++) {
            Entity entityIn = poisonList.get(i);
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SATURATION,200,0));
        }

    }

    private void executeBlight(World worldIn, Random random) {
        for (int i = 0; i < 4; ++i) {
            BlockPos blockpos = pos.add(random.nextInt(21) - 10, random.nextInt(10) - 5, random.nextInt(21) - 10); //remember to copy to corgrowth and rotated file
            if (worldIn.getBlockState(blockpos).isIn(Blocks.DIRT) || worldIn.getBlockState(blockpos).isIn(Blocks.COARSE_DIRT)||worldIn.getBlockState(blockpos).isIn(Blocks.FARMLAND)) {
                worldIn.setBlockState(blockpos, BlockInit.CORDIRT.get().getDefaultState());
            } else if (worldIn.getBlockState(blockpos).isIn(Blocks.GRASS_BLOCK) || worldIn.getBlockState(blockpos).isIn(Blocks.MYCELIUM) || worldIn.getBlockState(blockpos).isIn(Blocks.PODZOL)) {
                int grass = random.nextInt(3) + 1;
                if (grass == 1) {
                    worldIn.setBlockState(blockpos, BlockInit.CORGROWTH.get().getDefaultState());
                }
                if (grass == 2) {
                    worldIn.setBlockState(blockpos, BlockInit.CORGROWTH.get().getDefaultState());
                }
                if (grass == 3) {
                    worldIn.setBlockState(blockpos, BlockInit.CORGROWTH_FERTILE.get().getDefaultState());
                }
            } else if (worldIn.getBlockState(blockpos).isIn(BlockTags.LOGS_THAT_BURN)) {
                worldIn.setBlockState(blockpos, BlockInit.ROTWOOD_LOG.get().getDefaultState());
            } else if (worldIn.getBlockState(blockpos).isIn(Blocks.SAND) || worldIn.getBlockState(blockpos).isIn(Blocks.RED_SAND)) {
                worldIn.setBlockState(blockpos, BlockInit.CORSAND.get().getDefaultState());
            } else if (worldIn.getBlockState(blockpos).isIn(Blocks.BEEHIVE)||worldIn.getBlockState(blockpos).isIn(Blocks.BEE_NEST)
                    ||worldIn.getBlockState(blockpos).isIn(Blocks.PUMPKIN)||worldIn.getBlockState(blockpos).isIn(Blocks.MELON)) {
                worldIn.setBlockState(blockpos, BlockInit.CORHIVE.get().getDefaultState());
            }

        }
    }

    private void executeEpiphany(World worldIn, Random random) {
        for (int i = 0; i < 4; ++i) {
            BlockPos blockpos = pos.add(random.nextInt(17) - 8, random.nextInt(10) - 5, random.nextInt(17) - 8); //remember to copy to corgrowth and rotated file
            if (worldIn.getBlockState(blockpos) == BlockInit.CORDIRT.get().getDefaultState()) {
                worldIn.setBlockState(blockpos, Blocks.DIRT.getDefaultState());
            } else if (worldIn.getBlockState(blockpos) == BlockInit.CORGROWTH.get().getDefaultState()||worldIn.getBlockState(blockpos) == BlockInit.CORGROWTH_FERTILE.get().getDefaultState()) {
                worldIn.setBlockState(blockpos, Blocks.GRASS_BLOCK.getDefaultState());
            } else if (worldIn.getBlockState(blockpos) == BlockInit.ROTWOOD_LOG.get().getDefaultState()) {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState());
            } else if (worldIn.getBlockState(blockpos) == BlockInit.CORSAND.get().getDefaultState()) {
                worldIn.setBlockState(blockpos, Blocks.SAND.getDefaultState());
            }
            else if (worldIn.getBlockState(blockpos) == BlockInit.GOREGRASS.get().getDefaultState()||worldIn.getBlockState(blockpos) == BlockInit.DOUBLE_GOREGRASS.get().getDefaultState()||worldIn.getBlockState(blockpos) == BlockInit.PEARLFLOWER.get().getDefaultState()||worldIn.getBlockState(blockpos) == BlockInit.CORHIVE_BUD.get().getDefaultState()) {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState());
            }

        }
    }
}
