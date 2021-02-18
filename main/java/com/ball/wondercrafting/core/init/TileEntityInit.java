package com.ball.wondercrafting.core.init;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.common.entities.monster.CortoxPig;
import com.ball.wondercrafting.common.tileentity.CorhiveTileEntity;
import com.ball.wondercrafting.common.tileentity.EpiphanyBloomTileEntity;
import com.ball.wondercrafting.common.tileentity.WonderTileEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
        public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Wondercrafting.MOD_ID);

    public static final RegistryObject<TileEntityType<EpiphanyBloomTileEntity>> EPIPHANY_BLOOM = TILE_ENTITIES.register("epiphany_bloom",
            () -> TileEntityType.Builder.create(EpiphanyBloomTileEntity::new, BlockInit.EPIPHANY_BLOOM.get()).build(null));
    public static final RegistryObject<TileEntityType<WonderTileEntity>> WONDER = TILE_ENTITIES.register("wonder",
            () -> TileEntityType.Builder.create(WonderTileEntity::new, BlockInit.WONDER.get()).build(null));
    public static final RegistryObject<TileEntityType<CorhiveTileEntity>> CORHIVE = TILE_ENTITIES.register("corhive",
            () -> TileEntityType.Builder.create(CorhiveTileEntity::new, BlockInit.CORHIVE.get()).build(null));
}
