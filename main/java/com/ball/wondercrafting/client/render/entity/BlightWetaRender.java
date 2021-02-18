package com.ball.wondercrafting.client.render.entity;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.client.render.model.BlightWetaModel;
import com.ball.wondercrafting.client.render.model.CortoxPigModel;
import com.ball.wondercrafting.common.entities.monster.BlightWeta;
import com.ball.wondercrafting.common.entities.monster.CortoxPig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class BlightWetaRender extends LivingRenderer<BlightWeta, BlightWetaModel<BlightWeta>> {
    public BlightWetaRender(EntityRendererManager manager) {
        super(manager, new BlightWetaModel(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(BlightWeta entity) {
        return new ResourceLocation(Wondercrafting.MOD_ID, "/textures/entities/blightweta.png");
    }

    protected void preRenderCallback(BlightWeta entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.4f, 0.4f, 0.4f);
    }

    public static class RenderFactory implements IRenderFactory<BlightWeta> {
        @Override
        public EntityRenderer<? super BlightWeta> createRenderFor(EntityRendererManager manager) {
            return new BlightWetaRender(manager);
        }
    }

    protected boolean canRenderName(BlightWeta entity) {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }
}
