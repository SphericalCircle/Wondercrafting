package com.ball.wondercrafting.client.render.entity;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.client.render.entity.layers.CortoxSheepWoolLayer;
import com.ball.wondercrafting.client.render.model.CortoxCowModel;
import com.ball.wondercrafting.client.render.model.CortoxSheepModel;
import com.ball.wondercrafting.common.entities.monster.CortoxCow;
import com.ball.wondercrafting.common.entities.monster.CortoxSheep;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class CortoxSheepRender extends LivingRenderer<CortoxSheep, CortoxSheepModel<CortoxSheep>> {
    public CortoxSheepRender(EntityRendererManager manager) {
        super(manager, new CortoxSheepModel(), 0.7f);
        this.addLayer(new CortoxSheepWoolLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(CortoxSheep entity) {
        return new ResourceLocation(Wondercrafting.MOD_ID, "/textures/entities/cortoxsheep.png");
    }

    public static class RenderFactory implements IRenderFactory<CortoxSheep> {
        @Override
        public EntityRenderer<? super CortoxSheep> createRenderFor(EntityRendererManager manager) {
            return new CortoxSheepRender(manager);
        }
    }

    @Override
    protected boolean canRenderName(CortoxSheep entity) {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }
}

