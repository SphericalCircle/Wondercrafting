package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.common.items.Wand;
import com.ball.wondercrafting.common.items.WonderousLocator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Wondercrafting.MOD_ID);

    //Crystals
    public static final RegistryObject<Item> SKI_CRYSTAL = ITEMS.register("ski_crystal",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> LIQ_CRYSTAL = ITEMS.register("liq_crystal",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> TER_CRYSTAL = ITEMS.register("ter_crystal",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> HET_CRYSTAL = ITEMS.register("het_crystal",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> MYS_CRYSTAL = ITEMS.register("mys_crystal",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> COR_CRYSTAL = ITEMS.register("cor_crystal",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));

    //Wands
    public static final RegistryObject<Item> BASIC_IRON_WAND = ITEMS.register("basic_iron_wand",
            () -> new Wand(new Item.Properties().maxStackSize(1).group(Wondercrafting.WONDERCRAFTING_GROUP)));

    //Wand Materials
    public static final RegistryObject<Item> IRON_CAP = ITEMS.register("iron_cap",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> GOLD_CAP = ITEMS.register("gold_cap",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> MYSTEEL_CAP = ITEMS.register("mysteel_cap",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> CORMETAL_CAP = ITEMS.register("cormetal_cap",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> VITALITY_WAND_STAFF = ITEMS.register("vitality_wand_staff",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> MYS_PEARL = ITEMS.register("mys_pearl",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> COR_PEARL = ITEMS.register("cor_pearl",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));

    //Tools
    public static final RegistryObject<Item> LOCATOR_CORE = ITEMS.register("locator_core",
            () -> new Item(new Item.Properties().maxStackSize(1).group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> WONDEROUS_LOCATOR = ITEMS.register("wonderous_locator",
            () -> new WonderousLocator(new Item.Properties().maxStackSize(1).group(Wondercrafting.WONDERCRAFTING_GROUP)));

    //Cor
    public static final RegistryObject<Item> COR_TENDRIL = ITEMS.register("cor_tendril",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));

    //Loot
    public static final RegistryObject<Item> COIN = ITEMS.register("coin",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<Item> RESEARCH_PIECE = ITEMS.register("research_piece",
            () -> new Item(new Item.Properties().group(Wondercrafting.WONDERCRAFTING_GROUP)));

    // Blocks
    public static final RegistryObject<BlockItem> VITALITY_LOG = ITEMS.register("vitality_log",
            () -> new BlockItem(BlockInit.VITALITY_LOG.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> STRIPPED_VITALITY_LOG = ITEMS.register("stripped_vitality_log",
            () -> new BlockItem(BlockInit.STRIPPED_VITALITY_LOG.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_PLANKS = ITEMS.register("vitality_planks",
            () -> new BlockItem(BlockInit.VITALITY_PLANKS.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_SLAB = ITEMS.register("vitality_slab",
            () -> new BlockItem(BlockInit.VITALITY_SLAB.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_STAIRS = ITEMS.register("vitality_stairs",
            () -> new BlockItem(BlockInit.VITALITY_STAIRS.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_FENCE = ITEMS.register("vitality_fence",
            () -> new BlockItem(BlockInit.VITALITY_FENCE.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_FENCE_GATE = ITEMS.register("vitality_fence_gate",
            () -> new BlockItem(BlockInit.VITALITY_FENCE_GATE.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_TRAPDOOR = ITEMS.register("vitality_trapdoor",
            () -> new BlockItem(BlockInit.VITALITY_TRAPDOOR.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_DOOR = ITEMS.register("vitality_door",
            () -> new BlockItem(BlockInit.VITALITY_DOOR.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_WOOD = ITEMS.register("vitality_wood",
            () -> new BlockItem(BlockInit.VITALITY_WOOD.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> STRIPPED_VITALITY_WOOD = ITEMS.register("stripped_vitality_wood",
            () -> new BlockItem(BlockInit.STRIPPED_VITALITY_WOOD.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_PRESSURE_PLATE = ITEMS.register("vitality_pressure_plate",
            () -> new BlockItem(BlockInit.VITALITY_PRESSURE_PLATE.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> VITALITY_BUTTON = ITEMS.register("vitality_button",
            () -> new BlockItem(BlockInit.VITALITY_BUTTON.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> EPIPHANY_BLOOM = ITEMS.register("epiphany_bloom",
            () -> new BlockItem(BlockInit.EPIPHANY_BLOOM.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> WONDER = ITEMS.register("wonder",
            () -> new BlockItem(BlockInit.WONDER.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> CRATE = ITEMS.register("crate",
            () -> new BlockItem(BlockInit.CRATE.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> NETHER_CRATE = ITEMS.register("nether_crate",
            () -> new BlockItem(BlockInit.NETHER_CRATE.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> WARPED_CRATE = ITEMS.register("warped_crate",
            () -> new BlockItem(BlockInit.WARPED_CRATE.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> URN = ITEMS.register("urn",
            () -> new BlockItem(BlockInit.URN.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> CORDIRT = ITEMS.register("cordirt",
            () -> new BlockItem(BlockInit.CORDIRT.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> CORGROWTH = ITEMS.register("corgrowth",
            () -> new BlockItem(BlockInit.CORGROWTH.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> CORGROWTH_FERTILE = ITEMS.register("corgrowth_fertile",
            () -> new BlockItem(BlockInit.CORGROWTH_FERTILE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CORSAND = ITEMS.register("corsand",
            () -> new BlockItem(BlockInit.CORSAND.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> ROTWOOD_LOG = ITEMS.register("rotwood_log",
            () -> new BlockItem(BlockInit.ROTWOOD_LOG.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> GOREGRASS = ITEMS.register("goregrass",
            () -> new BlockItem(BlockInit.GOREGRASS.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> DOUBLE_GOREGRASS = ITEMS.register("double_goregrass",
            () -> new BlockItem(BlockInit.DOUBLE_GOREGRASS.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> PEARLFLOWER = ITEMS.register("pearlflower",
            () -> new BlockItem(BlockInit.PEARLFLOWER.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> CORHIVE_BUD = ITEMS.register("corhive_bud",
            () -> new BlockItem(BlockInit.CORHIVE_BUD.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> CORPEARL_ORE = ITEMS.register("corpearl_ore",
            () -> new BlockItem(BlockInit.CORPEARL_ORE.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
    public static final RegistryObject<BlockItem> CORHIVE = ITEMS.register("corhive",
            () -> new BlockItem(BlockInit.CORHIVE.get(), new Item.Properties()
                    .group(Wondercrafting.WONDERCRAFTING_GROUP)));
}
