package com.ball.wondercrafting.client.render.entity;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.client.render.model.CortoxPigModel;
import com.ball.wondercrafting.common.entities.monster.CortoxPig;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class CortoxPigRender extends LivingRenderer<CortoxPig, CortoxPigModel> {
    public CortoxPigRender(EntityRendererManager manager) {
        super(manager, new CortoxPigModel(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(CortoxPig entity) {
        return new ResourceLocation(Wondercrafting.MOD_ID, "/textures/entities/cortoxpig.png");
    }

    public static class RenderFactory implements IRenderFactory<CortoxPig> {
        @Override
        public EntityRenderer<? super CortoxPig> createRenderFor(EntityRendererManager manager) {
            return new CortoxPigRender(manager);
        }
    }

    @Override
    protected boolean canRenderName(CortoxPig entity) {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }
}
