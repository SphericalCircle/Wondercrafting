package com.ball.wondercrafting.client.render.entity;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.client.render.model.CortoxCowModel;
import com.ball.wondercrafting.client.render.model.CortoxWolfModel;
import com.ball.wondercrafting.common.entities.monster.CortoxCow;
import com.ball.wondercrafting.common.entities.monster.CortoxWolf;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class CortoxWolfRender extends LivingRenderer<CortoxWolf, CortoxWolfModel<CortoxWolf>> {
    public CortoxWolfRender(EntityRendererManager manager) {
        super(manager, new CortoxWolfModel(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(CortoxWolf entity) {
        return new ResourceLocation(Wondercrafting.MOD_ID, "/textures/entities/cortoxwolf.png");
    }

    public static class RenderFactory implements IRenderFactory<CortoxWolf> {
        @Override
        public EntityRenderer<? super CortoxWolf> createRenderFor(EntityRendererManager manager) {
            return new CortoxWolfRender(manager);
        }
    }

    @Override
    protected boolean canRenderName(CortoxWolf entity) {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }
}
