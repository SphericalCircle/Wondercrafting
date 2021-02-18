package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.common.entities.monster.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Wondercrafting.MOD_ID);

    public static final RegistryObject<EntityType<CortoxPig>> CORTOX_PIG = ENTITIES.register("cortox_pig",
            () -> EntityType.Builder.create(CortoxPig::new, EntityClassification.MONSTER)
            .build(new ResourceLocation(Wondercrafting.MOD_ID, "cortox_pig").toString()));
    public static final RegistryObject<EntityType<CortoxCow>> CORTOX_COW = ENTITIES.register("cortox_cow",
            () -> EntityType.Builder.create(CortoxCow::new, EntityClassification.MONSTER)
                    .build(new ResourceLocation(Wondercrafting.MOD_ID, "cortox_cow").toString()));
    public static final RegistryObject<EntityType<CortoxChicken>> CORTOX_CHICKEN = ENTITIES.register("cortox_chicken",
            () -> EntityType.Builder.create(CortoxChicken::new, EntityClassification.MONSTER)
                    .build(new ResourceLocation(Wondercrafting.MOD_ID, "cortox_chicken").toString()));
    public static final RegistryObject<EntityType<CortoxSheep>> CORTOX_SHEEP = ENTITIES.register("cortox_sheep",
            () -> EntityType.Builder.create(CortoxSheep::new, EntityClassification.MONSTER)
                    .build(new ResourceLocation(Wondercrafting.MOD_ID, "cortox_sheep").toString()));
    public static final RegistryObject<EntityType<CortoxWolf>> CORTOX_WOLF = ENTITIES.register("cortox_wolf",
            () -> EntityType.Builder.create(CortoxWolf::new, EntityClassification.MONSTER)
                    .build(new ResourceLocation(Wondercrafting.MOD_ID, "cortox_wolf").toString()));
    public static final RegistryObject<EntityType<BlightWeta>> BLIGHT_WETA = ENTITIES.register("blight_weta",
            () -> EntityType.Builder.create(BlightWeta::new, EntityClassification.MONSTER)
                    .build(new ResourceLocation(Wondercrafting.MOD_ID, "blight_weta").toString()));

}
