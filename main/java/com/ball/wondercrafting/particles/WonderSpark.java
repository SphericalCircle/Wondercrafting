package com.ball.wondercrafting.particles;

import com.ball.wondercrafting.core.init.ParticleInit;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

public class WonderSpark extends SpriteTexturedParticle {

    private final IAnimatedSprite spriteSet;

    public WonderSpark(ClientWorld worldIn, double xIn, double yIn, double zIn,
                       double xSpeed, double ySpeed, double zSpeed, WonderSparkData data, IAnimatedSprite spriteSet) {
        super(worldIn, xIn, yIn, zIn, xSpeed, ySpeed, zSpeed);

        this.spriteSet = spriteSet;
        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;
        this.posX = xIn;
        this.posY = yIn;
        this.posZ = zIn;
        this.particleScale = 0.1f * (this.rand.nextFloat()*0.2f+5.0f);
        this.particleRed = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getRed();
        this.particleGreen = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getGreen();
        this.particleBlue = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getBlue();
        this.maxAge = (int) (24.0D / ((double)this.rand.nextFloat() * 0.8D + 0.2D)) + 4;
        this.selectSpriteWithAge(spriteSet);
    }

    private double posX, posY, posZ;
    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.selectSpriteWithAge(this.spriteSet);
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<WonderSparkData> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite spriteIn) {
            this.sprites = spriteIn;
        }

        public Particle makeParticle(WonderSparkData typeIn, ClientWorld worldIn, double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed) {
            WonderSpark particle = new WonderSpark(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn,
                    sprites);
            return particle;
        }
    }

    public static class WonderSparkData implements IParticleData {
        public static final IParticleData.IDeserializer<WonderSparkData> DESERIALIZER = new IParticleData.IDeserializer<WonderSparkData>() {
            public WonderSparkData deserialize(ParticleType<WonderSparkData> particleTypeIn,
                                               StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                float red = (float) reader.readDouble();
                reader.expect(' ');
                float green = (float) reader.readDouble();
                reader.expect(' ');
                float blue = (float) reader.readDouble();
                reader.expect(' ');
                float alpha = (float) reader.readDouble();
                return new WonderSparkData(red, green, blue, alpha);
            }

            public WonderSparkData read(ParticleType<WonderSparkData> particleTypeIn, PacketBuffer buffer) {
                return new WonderSparkData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(),
                        buffer.readFloat());
            }
        };

        private final float red;
        private final float green;
        private final float blue;
        private final float alpha;

        public WonderSparkData(float redIn, float greenIn, float blueIn, float alphaIn) {
            this.red = redIn;
            this.green = greenIn;
            this.blue = blueIn;
            this.alpha = MathHelper.clamp(alphaIn, 0.01f, 4.0f);
        }

        @Override
        public void write(PacketBuffer buffer) {
            buffer.writeFloat(this.red);
            buffer.writeFloat(this.green);
            buffer.writeFloat(this.blue);
            buffer.writeFloat(this.alpha);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String getParameters() {
            return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()),
                    this.red, this.green, this.blue, this.alpha);
        }

        @Override
        public ParticleType<WonderSparkData> getType() {
            return ParticleInit.WONDER_SPARK.get();
        }

        @OnlyIn(Dist.CLIENT)
        public float getRed() {
            return this.red;
        }

        @OnlyIn(Dist.CLIENT)
        public float getGreen() {
            return this.green;
        }

        @OnlyIn(Dist.CLIENT)
        public float getBlue() {
            return this.blue;
        }

        @OnlyIn(Dist.CLIENT)
        public float getAlpha() {
            return this.alpha;
        }
    }

}
