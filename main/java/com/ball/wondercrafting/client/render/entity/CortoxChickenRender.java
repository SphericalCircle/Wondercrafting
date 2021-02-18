package com.ball.wondercrafting.client.render.entity;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.client.render.model.CortoxChickenModel;
import com.ball.wondercrafting.client.render.model.CortoxCowModel;
import com.ball.wondercrafting.common.entities.monster.CortoxChicken;
import com.ball.wondercrafting.common.entities.monster.CortoxCow;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class CortoxChickenRender extends LivingRenderer<CortoxChicken, CortoxChickenModel> {
    public CortoxChickenRender(EntityRendererManager manager) {
        super(manager, new CortoxChickenModel(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(CortoxChicken entity) {
        return new ResourceLocation(Wondercrafting.MOD_ID, "/textures/entities/cortoxchicken.png");
    }

    protected float handleRotationFloat(CortoxChicken livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    public static class RenderFactory implements IRenderFactory<CortoxChicken> {
        @Override
        public EntityRenderer<? super CortoxChicken> createRenderFor(EntityRendererManager manager) {
            return new CortoxChickenRender(manager);
        }
    }

    @Override
    protected boolean canRenderName(CortoxChicken entity) {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }
}
