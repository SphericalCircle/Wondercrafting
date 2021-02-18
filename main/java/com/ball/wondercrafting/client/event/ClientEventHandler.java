package com.ball.wondercrafting.client.event;

import com.ball.wondercrafting.Wondercrafting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Wondercrafting.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    public static final ResourceLocation LOC = new ResourceLocation(Wondercrafting.MOD_ID , "particle/wonder_anim");


    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onStitchEvent(TextureStitchEvent.Pre event)
    {
        ResourceLocation stitching = event.getMap().getTextureLocation();
        if(!stitching.equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE))
        {
            return;
        }


        boolean added = event.addSprite(LOC);
        Wondercrafting.LOGGER.debug(added+ " atlas wonder");

    }

}

