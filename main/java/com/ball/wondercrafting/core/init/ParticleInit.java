package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.particles.CorSmoke;
import com.ball.wondercrafting.particles.CorSmoke.CorSmokeData;
import com.ball.wondercrafting.particles.WonderSpark;
import com.ball.wondercrafting.particles.WonderSpark.WonderSparkData;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(
            ForgeRegistries.PARTICLE_TYPES, Wondercrafting.MOD_ID);

    public static final RegistryObject<ParticleType<CorSmokeData>> COR_SMOKE = PARTICLES.register(
            "cor_smoke",
            () -> new ParticleType<CorSmokeData>(true, CorSmokeData.DESERIALIZER) {
                @Override
                public Codec<CorSmokeData> func_230522_e_() {
                    return null;
                }
            });

    public static final RegistryObject<ParticleType<WonderSparkData>> WONDER_SPARK = PARTICLES.register(
            "wonder_spark",
            () -> new ParticleType<WonderSparkData>(true, WonderSparkData.DESERIALIZER) {
                @Override
                public Codec<WonderSparkData> func_230522_e_() {
                    return null;
                }
            });

}
