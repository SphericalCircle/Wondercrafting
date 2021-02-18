package com.ball.wondercrafting.client.render.tileentity;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.client.render.model.WonderModel;
import com.ball.wondercrafting.common.tileentity.WonderTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class WonderRenderer extends TileEntityRenderer<WonderTileEntity> {
    public static final ResourceLocation TEXTURE_LOC = new ResourceLocation(Wondercrafting.MOD_ID , "particle/wonder_anim");
    private final RenderMaterial TEXTURE_WONDER = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, TEXTURE_LOC);
    private final WonderModel modelWonder = new WonderModel();

    public WonderRenderer(TileEntityRendererDispatcher p_i226015_1_) {
        super(p_i226015_1_);
    }

    public void render(WonderTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        float f = (float)tileEntityIn.tick + partialTicks;
        float red, blue, green, alpha;
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.translate(0D, (double)(0.1F + MathHelper.sin(f * 0.1F) * 0.01F), 0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(tileEntityIn.rotation));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(tileEntityIn.rotationTwo));
        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);

        if(tileEntityIn.netEssence > 0) {
            if(tileEntityIn.netEssence>=200) {
                green=(tileEntityIn.cho/200f);
                blue=1.0f+(tileEntityIn.cho/200f);
                red=1.0f+(tileEntityIn.cho/200f);
            }
            else {
                green = 1.0f + (tileEntityIn.cho/200f) - (tileEntityIn.netEssence/200f);
                blue=1.0f+(tileEntityIn.cho/200f);
                red=1.0f+(tileEntityIn.cho/200f);
            }
        }
        else {
            if(tileEntityIn.netEssence<=-200) {
                green=(tileEntityIn.cho/200f);
                blue=(tileEntityIn.cho/200f);
                red=1.0f+(tileEntityIn.cho/200f);
            }
            else {
                green = 1.0f + (tileEntityIn.cho/200f) + (tileEntityIn.netEssence/200f);
                blue = 1.0f + (tileEntityIn.cho/200f) + (tileEntityIn.netEssence/200f);
                red=1.0f+(tileEntityIn.cho/200f);
            }
        }
        if(tileEntityIn.totalEssence >= 500) {
            alpha = 0.9f;
        }
        else if (tileEntityIn.totalEssence >= 200) {
            alpha = 0.5f;
        }
        else
            alpha = 0.05f;

        IVertexBuilder ivertexbuilder = TEXTURE_WONDER.getBuffer(bufferIn, RenderType::getItemEntityTranslucentCull);
        this.modelWonder.renderAll(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    public boolean isGlobalRenderer(WonderTileEntity te) {
        return true;
    }
}
