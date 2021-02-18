package com.ball.wondercrafting.client.guis;

import com.ball.wondercrafting.Wondercrafting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Wondercrafting.MOD_ID)
public class HUDEventHandler {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final GUIWandHUD wandHUD = new GUIWandHUD();

    @SubscribeEvent
    public static void renderWandHUD(final RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        final PlayerEntity player = minecraft.player;

        wandHUD.drawHUD(event.getMatrixStack(), event.getPartialTicks());

    }
}