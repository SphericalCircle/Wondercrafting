package com.ball.wondercrafting.particles;

import com.ball.wondercrafting.core.init.ParticleInit;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CorSmoke extends SpriteTexturedParticle {
    public CorSmoke(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn,
                    CorSmokeData data, IAnimatedSprite sprite) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

        this.motionX = xSpeedIn;
        this.motionY = ySpeedIn;
        this.motionZ = zSpeedIn;
        this.posX = xCoordIn;
        this.posY = yCoordIn;
        this.posZ = zCoordIn;
        this.particleScale = 0.1f * (this.rand.nextFloat() * 0.2f + 1.7f);
        float f = (float) Math.random() * 0.4f + 0.6f;
        this.maxAge = (int) (Math.random() *10.0d) + 40;
    }

    @Override
    public void tick() {
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        }
        else {
            float f = (float) this.age / (float) this.maxAge;
            float f1 = (float) Math.random();
            float f2 = (float) Math.random() * -1;
            float f3 = f1 + f2;
            float f4 = (float) Math.random();
            float f5 = (float) Math.random() * -1;
            float f6 = f4 + f5;
            this.posY = this.posY + 0.2F * f;
            this.posX = this.posX + 0.1F * f3;
            this.posZ = this.posZ + 0.1F * f6;
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<CorSmokeData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteIn) {
            this.spriteSet = spriteIn;
        }

        public Particle makeParticle(CorSmokeData typeIn, ClientWorld worldIn, double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed) {
            CorSmoke particle = new CorSmoke(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
            particle.selectSpriteRandomly(spriteSet);
            return particle;
        }
    }


    public static class CorSmokeData implements IParticleData {
        public static final IParticleData.IDeserializer<CorSmokeData> DESERIALIZER = new IParticleData.IDeserializer<CorSmokeData>() {
            public CorSmokeData deserialize(ParticleType<CorSmokeData> particleTypeIn,
                                                    StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                return new CorSmokeData();
            }

            public CorSmokeData read(ParticleType<CorSmokeData> particleTypeIn, PacketBuffer buffer) {
                return new CorSmokeData();
            }
        };

        @Override
        public ParticleType<CorSmokeData> getType() {
            return ParticleInit.COR_SMOKE.get();
        }

        @Override
        public void write(PacketBuffer buffer) {

        }

        @Override
        public String getParameters() {
            return null;
        }
    }
}
