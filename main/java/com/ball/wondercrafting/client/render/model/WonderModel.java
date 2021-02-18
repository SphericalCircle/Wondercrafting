package com.ball.wondercrafting.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class WonderModel extends Model {
    private final ModelRenderer orb = (new ModelRenderer(16, 16, 0, 0)).addBox(0F, 0F, 0F, 16.0F, 16.0F, 0.005F);

    public WonderModel() {
        super(RenderType::getItemEntityTranslucentCull);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.renderAll(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void renderAll(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        orb.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setWonderState(float xIn, float yIn, float zIn) {
        this.orb.setRotationPoint(xIn, yIn, zIn);
    }
}
