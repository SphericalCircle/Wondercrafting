package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.common.blocks.*;
import com.ball.wondercrafting.world.feature.CorhiveFormation;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.OakTree;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Wondercrafting.MOD_ID);

    //Vitality Tree
    public static final RegistryObject<Block> VITALITY_LOG = BLOCKS.register("vitality_log",
            () -> new VitalityLog(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> STRIPPED_VITALITY_LOG = BLOCKS.register("stripped_vitality_log",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> VITALITY_PLANKS = BLOCKS.register("vitality_planks",
            () -> new Block(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> VITALITY_SLAB = BLOCKS.register("vitality_slab",
            () -> new SlabBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> VITALITY_STAIRS = BLOCKS.register("vitality_stairs",
            () -> new StairsBlock(VITALITY_PLANKS.get().getDefaultState(), AbstractBlock.Properties.from(VITALITY_PLANKS.get())));
    public static final RegistryObject<Block> VITALITY_FENCE = BLOCKS.register("vitality_fence",
            () -> new FenceBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> VITALITY_FENCE_GATE = BLOCKS.register("vitality_fence_gate",
            () -> new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> VITALITY_TRAPDOOR = BLOCKS.register("vitality_trapdoor",
            () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> VITALITY_DOOR = BLOCKS.register("vitality_door",
            () -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> VITALITY_WOOD = BLOCKS.register("vitality_wood",
            () -> new VitalityWood(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> STRIPPED_VITALITY_WOOD = BLOCKS.register("stripped_vitality_wood",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.5f).harvestTool(ToolType.AXE).harvestLevel(2)
                    .sound(SoundType.WOOD).setRequiresTool()));
    public static final RegistryObject<Block> VITALITY_PRESSURE_PLATE = BLOCKS.register("vitality_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD).doesNotBlockMovement()));
    public static final RegistryObject<Block> VITALITY_BUTTON = BLOCKS.register("vitality_button",
            () -> new WoodButtonBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(0.5f).harvestTool(ToolType.AXE)
                    .sound(SoundType.WOOD).doesNotBlockMovement()));

    //Epiphany Bloom
    public static final RegistryObject<Block> EPIPHANY_BLOOM = BLOCKS.register("epiphany_bloom",
            () -> new EpiphanyBloom(AbstractBlock.Properties.create(Material.PLANTS)
                    .doesNotBlockMovement().zeroHardnessAndResistance().harvestLevel(0)
                    .sound(SoundType.VINE).setLightLevel((state) -> {
                        return 5; })));

    //Wonder
    public static final RegistryObject<Block> WONDER = BLOCKS.register("wonder",
            () -> new Wonder(AbstractBlock.Properties.create(Material.MISCELLANEOUS)
                    .hardnessAndResistance(1.0f).sound(SoundType.LODESTONE).doesNotBlockMovement().setLightLevel((state) -> {
                        return 7; })));

    //Loot
    public static final RegistryObject<Block> CRATE = BLOCKS.register("crate",
            () -> new CrateBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)
                    .harvestLevel(0).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> NETHER_CRATE = BLOCKS.register("nether_crate",
            () -> new CrateBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)
                    .harvestLevel(0).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> WARPED_CRATE = BLOCKS.register("warped_crate",
            () -> new CrateBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)
                    .harvestLevel(0).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> URN = BLOCKS.register("urn",
            () -> new UrnBlock(AbstractBlock.Properties.create(Material.ROCK)
                    .hardnessAndResistance(2.0f).harvestTool(ToolType.PICKAXE)
                    .harvestLevel(0).sound(SoundType.GILDED_BLACKSTONE)));

    //Cor Growth
    public static final RegistryObject<Block> CORDIRT = BLOCKS.register("cordirt",
            () -> new CorSpreadBlock(AbstractBlock.Properties.create(Material.EARTH)
                    .hardnessAndResistance(0.5f).harvestTool(ToolType.SHOVEL)
                    .harvestLevel(0).sound(SoundType.HONEY)));
    public static final RegistryObject<Block> CORGROWTH = BLOCKS.register("corgrowth",
            () -> new Corgrowth(AbstractBlock.Properties.create(Material.EARTH)
                    .hardnessAndResistance(0.5f).harvestTool(ToolType.SHOVEL)
                    .harvestLevel(0).sound(SoundType.HONEY)));
    public static final RegistryObject<Block> CORGROWTH_FERTILE = BLOCKS.register("corgrowth_fertile",
            () -> new CorgrowthFertile(AbstractBlock.Properties.create(Material.EARTH)
                    .hardnessAndResistance(0.5f).harvestTool(ToolType.SHOVEL)
                    .harvestLevel(0).sound(SoundType.HONEY)));
    public static final RegistryObject<Block> CORSAND = BLOCKS.register("corsand",
            () -> new Corfalling(3539458, AbstractBlock.Properties.create(Material.SAND)
                    .hardnessAndResistance(0.5f).harvestTool(ToolType.SHOVEL)
                    .harvestLevel(0).sound(SoundType.HONEY)));
    public static final RegistryObject<Block> ROTWOOD_LOG = BLOCKS.register("rotwood_log",
            () -> new CorRotatedBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).harvestLevel(0)
                    .sound(SoundType.HYPHAE)));
    public static final RegistryObject<Block> GOREGRASS = BLOCKS.register("goregrass",
            () -> new Corgrass(AbstractBlock.Properties.create(Material.PLANTS)
                    .doesNotBlockMovement().zeroHardnessAndResistance().harvestLevel(0)
                    .sound(SoundType.ROOT)));
    public static final RegistryObject<Block> DOUBLE_GOREGRASS = BLOCKS.register("double_goregrass",
            () -> new DoubleCorgrass(AbstractBlock.Properties.create(Material.PLANTS)
                    .doesNotBlockMovement().zeroHardnessAndResistance().harvestLevel(0)
                    .sound(SoundType.ROOT)));
    public static final RegistryObject<Block> PEARLFLOWER = BLOCKS.register("pearlflower",
            () -> new Corgrass(AbstractBlock.Properties.create(Material.PLANTS)
                    .doesNotBlockMovement().zeroHardnessAndResistance().harvestLevel(0)
                    .sound(SoundType.ROOT).setLightLevel((state) -> {
                        return 5; })));
    public static final RegistryObject<Block> CORHIVE_BUD = BLOCKS.register("corhive_bud",
            () -> new CorhiveBud(new CorhiveFormation(), Block.Properties.create(Material.PLANTS)
                    .doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().harvestLevel(0)
                    .sound(SoundType.ROOT)));

    //Ores
    public static final RegistryObject<Block> CORPEARL_ORE = BLOCKS.register("corpearl_ore",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2)
                    .sound(SoundType.STONE).setRequiresTool()));

    //Corhive
    public static final RegistryObject<Block> CORHIVE = BLOCKS.register("corhive",
            () -> new Corhive(AbstractBlock.Properties.create(Material.ORGANIC)
                    .hardnessAndResistance(10.0f).notSolid()
                    .sound(SoundType.HONEY).setLightLevel((state) -> {
                        return 7; })));
}
