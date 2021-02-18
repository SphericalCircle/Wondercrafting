package com.ball.wondercrafting.client.guis;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.core.interfaces.IWandDisplay;
import com.ball.wondercrafting.core.interfaces.IWandEssence;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIWandHUD extends AbstractGui {
    private static final Minecraft minecraft = Minecraft.getInstance();
    int mys, cor, cho, scale;
    protected static final ResourceLocation MYS_ESSENCE = new ResourceLocation(Wondercrafting.MOD_ID, "textures/gui/mys_essence.png");
    protected static final ResourceLocation COR_ESSENCE = new ResourceLocation(Wondercrafting.MOD_ID,"textures/gui/cor_essence.png");
    protected static final ResourceLocation CHO_ESSENCE = new ResourceLocation(Wondercrafting.MOD_ID,"textures/gui/cho_essence.png");

    public boolean shouldDisplayBar(){
        ItemStack mainHand = minecraft.player.getHeldItemMainhand();
        ItemStack offHand = minecraft.player.getHeldItemOffhand();
        return (mainHand.getItem() instanceof IWandDisplay && ((IWandDisplay) mainHand.getItem()).shouldDisplay(mainHand))
                || (offHand.getItem() instanceof IWandDisplay && ((IWandDisplay) offHand.getItem()).shouldDisplay(offHand));
    }

    public void drawHUD(MatrixStack ms, float pt) {
        if(!shouldDisplayBar())
            return;

        if(minecraft.player.getHeldItemMainhand().getItem() instanceof IWandEssence) {
            IWandEssence essence = (IWandEssence) minecraft.player.getHeldItemMainhand().getItem();
            mys = essence.getMys(minecraft.player.getHeldItemMainhand());
            cor = essence.getCor(minecraft.player.getHeldItemMainhand());
            cho = essence.getCho(minecraft.player.getHeldItemMainhand());
            scale = essence.getScale();
        }
        else if(minecraft.player.getHeldItemOffhand().getItem() instanceof IWandEssence) {
            IWandEssence essence = (IWandEssence) minecraft.player.getHeldItemOffhand().getItem();
            mys = essence.getMys(minecraft.player.getHeldItemOffhand());
            cor = essence.getCor(minecraft.player.getHeldItemOffhand());
            cho = essence.getCho(minecraft.player.getHeldItemOffhand());
            scale = essence.getScale();
        }
        else {
            return;
        }


        int offsetLeft = 20;

        int height = minecraft.getMainWindow().getScaledHeight() - 5;

        minecraft.getTextureManager().bindTexture(MYS_ESSENCE);
        blit(ms,offsetLeft, height-10-(mys*2), 0, 0, 20, mys*2/scale, 256, 256);

        minecraft.getTextureManager().bindTexture(COR_ESSENCE);
        blit(ms,offsetLeft, height-10-(mys*2)-(cor*2), 0, 0, 20, cor*2/scale, 256, 256);

        minecraft.getTextureManager().bindTexture(CHO_ESSENCE);
        blit(ms,offsetLeft, height-10-(mys*2)-(cor*2)-(cho*2), 0, 0, 20, cho*2/scale, 256, 256);

    }
}
