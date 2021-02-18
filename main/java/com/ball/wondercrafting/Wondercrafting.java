package com.ball.wondercrafting;

import com.ball.wondercrafting.client.render.entity.*;
import com.ball.wondercrafting.client.render.tileentity.WonderRenderer;
import com.ball.wondercrafting.common.entities.monster.*;
import com.ball.wondercrafting.common.items.Wand;
import com.ball.wondercrafting.common.items.WonderousLocator;
import com.ball.wondercrafting.core.init.*;
import com.ball.wondercrafting.core.util.Networking;
import com.ball.wondercrafting.core.util.packets.PacketSendHungry;
import com.ball.wondercrafting.particles.CorSmoke;
import com.ball.wondercrafting.particles.WonderSpark;
import com.ball.wondercrafting.world.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Wondercrafting.MOD_ID)
public class Wondercrafting
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "wondercrafting";
    public static final ItemGroup WONDERCRAFTING_GROUP = new WondercraftingGroup("wondercraftingtab");
    public Wondercrafting() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);

        ParticleInit.PARTICLES.register(bus);
        PotionInit.EFFECTS.register(bus);
        PotionInit.POTIONS.register(bus);
        ItemInit.ITEMS.register(bus);
        SoundInit.SOUNDS.register(bus);
        BlockInit.BLOCKS.register(bus);
        TileEntityInit.TILE_ENTITIES.register(bus);
        EntityInit.ENTITIES.register(bus);
        FeatureInit.FEATURES.register(bus);
        BiomeInit.BIOMES.register(bus);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WonderGeneration::generateWonders);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, CrateGeneration::generateCrates);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, UrnGeneration::generateUrns);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, NetherCrateGeneration::generateCrates);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WarpedCrateGeneration::generateCrates);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, Wondercrafting::onBiomesLoad);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        int packetID=0;

        //Particles
        Minecraft.getInstance().particles.registerFactory(ParticleInit.COR_SMOKE.get(), CorSmoke.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ParticleInit.WONDER_SPARK.get(), WonderSpark.Factory::new);

        //Packets
        Networking.INSTANCE.registerMessage(packetID++,
                PacketSendHungry.class,
                PacketSendHungry::write,
                PacketSendHungry::read,
                PacketSendHungry::handle
        );

        //Block Rendering
        RenderTypeLookup.setRenderLayer(BlockInit.GOREGRASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.DOUBLE_GOREGRASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PEARLFLOWER.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CORHIVE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CORHIVE_BUD.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.EPIPHANY_BLOOM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.WONDER.get(), RenderType.getCutout());

        //Item Models
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(ItemInit.WONDEROUS_LOCATOR.get(), new ResourceLocation("blink"), (itemStackIn, p_239425_1_, entityIn) -> {
            if(WonderousLocator.isBlinking(itemStackIn)) {
                return 1.0f;
            }
            else {
                return 0.0f;
            }
        }));

        event.enqueueWork(() -> ItemModelsProperties.registerProperty(ItemInit.BASIC_IRON_WAND.get(), new ResourceLocation("pointing"), (itemStackIn, p_239425_1_, entityIn) -> {
            if(Wand.point(itemStackIn)) {
                return 1.0f;
            }
            else {
                return 0.0f;
            }
        }));

        //Entity Rendering
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CORTOX_PIG.get(), CortoxPigRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CORTOX_COW.get(), CortoxCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CORTOX_CHICKEN.get(), CortoxChickenRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CORTOX_SHEEP.get(), CortoxSheepRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CORTOX_WOLF.get(), CortoxWolfRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BLIGHT_WETA.get(), BlightWetaRender::new);

        //Entity Attributes
        event.enqueueWork(() -> GlobalEntityTypeAttributes.put(EntityInit.CORTOX_PIG.get(), CortoxPig.func_234215_eI_().create()));
        event.enqueueWork(() -> GlobalEntityTypeAttributes.put(EntityInit.CORTOX_COW.get(), CortoxCow.func_234215_eI_().create()));
        event.enqueueWork(() -> GlobalEntityTypeAttributes.put(EntityInit.CORTOX_CHICKEN.get(), CortoxChicken.func_234215_eI_().create()));
        event.enqueueWork(() -> GlobalEntityTypeAttributes.put(EntityInit.CORTOX_SHEEP.get(), CortoxSheep.func_234215_eI_().create()));
        event.enqueueWork(() -> GlobalEntityTypeAttributes.put(EntityInit.CORTOX_WOLF.get(), CortoxWolf.func_234215_eI_().create()));
        event.enqueueWork(() -> GlobalEntityTypeAttributes.put(EntityInit.BLIGHT_WETA.get(), BlightWeta.func_234224_eJ_().create()));

        //Tile Entity Rendering
        ClientRegistry.bindTileEntityRenderer(TileEntityInit.WONDER.get(), WonderRenderer::new);

        //Entity Spawning
        EntitySpawnPlacementRegistry.register(EntityInit.BLIGHT_WETA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlightWeta::canMonsterSpawn);


    }

    public static class WondercraftingGroup extends ItemGroup {
        public WondercraftingGroup(String label) {
            super(label);
        }
        @Override
        public ItemStack createIcon() {
            return ItemInit.MYSTEEL_CAP.get().getDefaultInstance();
        }
    }

    public static void onBiomesLoad(final BiomeLoadingEvent event) {
        Biome.Category cat = event.getCategory();
        event.getSpawns().withSpawner(
                EntityClassification.MONSTER,
                new MobSpawnInfo.Spawners(EntityInit.BLIGHT_WETA.get(), 400, 2, 6)
        );
    }


}
