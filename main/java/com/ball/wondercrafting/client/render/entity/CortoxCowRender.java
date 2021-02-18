package com.ball.wondercrafting.client.render.entity;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.client.render.model.CortoxCowModel;
import com.ball.wondercrafting.client.render.model.CortoxPigModel;
import com.ball.wondercrafting.common.entities.monster.CortoxCow;
import com.ball.wondercrafting.common.entities.monster.CortoxPig;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class CortoxCowRender extends LivingRenderer<CortoxCow, CortoxCowModel> {
    public CortoxCowRender(EntityRendererManager manager) {
        super(manager, new CortoxCowModel(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(CortoxCow entity) {
        return new ResourceLocation(Wondercrafting.MOD_ID, "/textures/entities/cortoxcow.png");
    }

    public static class RenderFactory implements IRenderFactory<CortoxCow> {
        @Override
        public EntityRenderer<? super CortoxCow> createRenderFor(EntityRendererManager manager) {
            return new CortoxCowRender(manager);
        }
    }

    @Override
    protected boolean canRenderName(CortoxCow entity) {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }
}
