package useless.prismaticlibe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextureFX;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import useless.prismaticlibe.mixin.accessor.ItemEntityRendererAccessor;

import java.util.Random;

public class ColoredItemRenderer {
    private static final Minecraft mc = Minecraft.getMinecraft(Minecraft.class);

    public static void drawItemIntoGui(ItemEntityRenderer itemEntityRenderer, FontRenderer fontrenderer, RenderEngine renderengine, ItemStack stack, int x, int y, float brightness) {
        Item item = stack.getItem();
        IColored iColored = (IColored) item;
        ColoredTexture[] iColoredTextures = iColored.getTextures(stack);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2884);
        GL11.glDisable(2896);
        renderengine.bindTexture(renderengine.getTexture("/gui/items.png"));
        int tileWidth = TextureFX.tileWidthItems;
        for (ColoredTexture colorLayer: iColoredTextures) {
            float alpha = colorLayer.getColor().getAlpha() / 255f;
            float r = colorLayer.getColor().getRed() / 255.0F;
            float g = colorLayer.getColor().getGreen() / 255.0F;
            float b = colorLayer.getColor().getBlue() / 255.0F;
            GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
            int[] texture = indexToCoordinate(colorLayer.getTextureIndex(), tileWidth);
            itemEntityRenderer.renderTexturedQuad(x, y, texture[0], texture[1], tileWidth, tileWidth);
        }

        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
    }

    public static int[] indexToCoordinate(int index, int tileWidth){
        return new int[]{index % 32 * tileWidth, index / 32 * tileWidth};
    }
    public static void doRenderItem(ItemEntityRenderer itemEntityRenderer, EntityItem entity, double x, double y, double z, float yaw, float partialTick) {
        ItemEntityRendererAccessor rendererAccessor = (ItemEntityRendererAccessor)itemEntityRenderer;
        Random random = rendererAccessor.getRandom();
        ItemStack itemstack = entity.item;
        if (itemstack != null) {
            Item item = itemstack.getItem();
            IColored iColored = (IColored) item;
            if (item != null) {
                for (ColoredTexture coloredTexture: iColored.getTextures(itemstack)) {
                    random.setSeed(187L);
                    GL11.glPushMatrix();
                    float bobbingOffset = MathHelper.sin(((float)entity.age + partialTick) / 10.0F + entity.field_804_d) * 0.1F + 0.1F;
                    float angle = (((float)entity.age + partialTick) / 20.0F + entity.field_804_d) * ((float) (180.0 / Math.PI));

                    int stackSize = entity.item.stackSize;
                    byte renderCount;
                    if (stackSize > 20){
                        renderCount = 4;
                    } else if (stackSize > 5) {
                        renderCount = 3;
                    } else if (stackSize > 1) {
                        renderCount = 2;
                    } else {
                        renderCount = 1;
                    }


                    GL11.glTranslatef((float)x, (float)y + bobbingOffset, (float)z);
                    GL11.glEnable(32826);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(770, 771);
                    GL11.glScalef(0.5F, 0.5F, 0.5F);

                    int texIndex = coloredTexture.getTextureIndex();

                    rendererAccessor.invokeLoadTexture("/gui/items.png");
                    int tileWidth = TextureFX.tileWidthItems;
                    float atlasWidth = tileWidth * Global.TEXTURE_ATLAS_WIDTH_TILES;
                    int[] texture = indexToCoordinate(texIndex, tileWidth);

                    Tessellator tessellator = Tessellator.instance;
                    float ul = (float)(texture[0]) / atlasWidth;
                    float ur = (float)(texture[0] + tileWidth) / atlasWidth;
                    float vt = (float)(texture[1]) / atlasWidth;
                    float vb = (float)(texture[1] + tileWidth) / atlasWidth;

                    float alpha = coloredTexture.getColor().getAlpha() / 255f;
                    float r = coloredTexture.getColor().getRed() / 255.0F;
                    float g = coloredTexture.getColor().getGreen() / 255.0F;
                    float b = coloredTexture.getColor().getBlue() / 255.0F;

                    float brightness = entity.getBrightness(partialTick);

                    if (mc.fullbright || entity.item.getItem().hasTag(ItemTags.renderFullbright)) {
                        brightness = 1.0F;
                    }

                    GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);

                    if (mc.gameSettings.items3D.value) {
                        GL11.glPushMatrix();
                        GL11.glScaled(1.0, 1.0, 1.0);
                        GL11.glRotated(angle, 0.0, 1.0, 0.0);
                        GL11.glTranslated(-0.5, 0.0, -0.05 * (double)(renderCount - 1));

                        for(int j = 0; j < renderCount; ++j) {
                            GL11.glPushMatrix();
                            GL11.glTranslated(0.0, 0.0, 0.1 * (double)j);
                            renderItem3dSingleLayer(entity, coloredTexture, false);
                            GL11.glPopMatrix();
                        }

                        GL11.glPopMatrix();
                    } else {
                        for(int i = 0; i < renderCount; ++i) {
                            GL11.glPushMatrix();
                            if (i > 0) {
                                float f16 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                                float f18 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                                float f20 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                                GL11.glTranslatef(f16, f18, f20);
                            }

                            GL11.glRotatef(180.0F - rendererAccessor.getRenderDispatcher().viewLerpYaw, 0.0F, 1.0F, 0.0F);
                            tessellator.startDrawingQuads();
                            tessellator.setNormal(0.0F, 1.0F, 0.0F);
                            tessellator.addVertexWithUV(-0.5, -0.25, 0.0, ul, vb);
                            tessellator.addVertexWithUV(0.5, -0.25, 0.0, ur, vb);
                            tessellator.addVertexWithUV(0.5, 0.75, 0.0, ur, vt);
                            tessellator.addVertexWithUV(-0.5, 0.75, 0.0, ul, vt);
                            tessellator.draw();
                            GL11.glPopMatrix();
                        }
                    }

                    GL11.glDisable(32826);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        }
    }
    public static void renderItem3dSingleLayer(Entity entity, ColoredTexture coloredTexture, boolean handheldTransform){
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        float alpha = coloredTexture.getColor().getAlpha() / 255f;
        float r = coloredTexture.getColor().getRed() / 255.0F;
        float g = coloredTexture.getColor().getGreen() / 255.0F;
        float b = coloredTexture.getColor().getBlue() / 255.0F;

        float brightness = entity.getBrightness(0);

        if (mc.fullbright) {
            brightness = 1.0F;
        } else if (entity instanceof EntityItem && ((EntityItem) entity).item.getItem().hasTag(ItemTags.renderFullbright)){
            brightness = 1.0f;
        }

        GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
        GL11.glBindTexture(3553, mc.renderEngine.getTexture("/gui/items.png"));
        int tileWidth = TextureFX.tileWidthItems;

        Tessellator tessellator = Tessellator.instance;
//        int i = itemstack.getItem().getIconIndex(itemstack);
//        if (entity instanceof EntityLiving) { // presumably was for dynamic textures like the compass? or maybe something like the fishing rod?
//            i = ((EntityLiving)entity).getItemIcon(itemstack);
//        }

        int texIndex = coloredTexture.getTextureIndex();
        int[] texture = indexToCoordinate(texIndex, tileWidth);
        float atlasWidth = 32 * tileWidth;
        float ul = (float)(texture[0]) / atlasWidth;
        float ur = (float)(texture[0] + tileWidth) / atlasWidth;
        float vt = (float)(texture[1]) / atlasWidth;
        float vb = (float)(texture[1] + tileWidth) / atlasWidth;
        float f4 = 1.0F;
        float f5 = 0.0F;
        float f6 = 0.3F;
        float foon = 0.5F / (float)tileWidth / 32.0F;
        float goon = 0.0625F * (16.0F / (float)tileWidth);
        GL11.glEnable(32826);
        if (handheldTransform) {
            GL11.glTranslatef(-f5, -f6, 0.0F);
            float f7 = 1.5F;
            GL11.glScalef(f7, f7, f7);
            GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        }

        float thickness = 0.0625F;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, ur, vb);
        tessellator.addVertexWithUV(f4, 0.0, 0.0, ul, vb);
        tessellator.addVertexWithUV(f4, 1.0, 0.0, ul, vt);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, ur, vt);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0F - thickness, ur, vt);
        tessellator.addVertexWithUV(f4, 1.0, 0.0F - thickness, ul, vt);
        tessellator.addVertexWithUV(f4, 0.0, 0.0F - thickness, ul, vb);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0F - thickness, ur, vb);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);

        for(int j = 0; j < tileWidth; ++j) {
            float f9 = (float)j / (float)tileWidth;
            float f13 = ur + (ul - ur) * f9 - foon;
            float f17 = f4 * f9;
            tessellator.addVertexWithUV(f17, 0.0, 0.0F - thickness, f13, vb);
            tessellator.addVertexWithUV(f17, 0.0, 0.0, f13, vb);
            tessellator.addVertexWithUV(f17, 1.0, 0.0, f13, vt);
            tessellator.addVertexWithUV(f17, 1.0, 0.0F - thickness, f13, vt);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);

        for(int k = 0; k < tileWidth; ++k) {
            float f10 = (float)k / (float)tileWidth;
            float f14 = ur + (ul - ur) * f10 - foon;
            float f18 = f4 * f10 + goon;
            tessellator.addVertexWithUV(f18, 1.0, 0.0F - thickness, f14, vt);
            tessellator.addVertexWithUV(f18, 1.0, 0.0, f14, vt);
            tessellator.addVertexWithUV(f18, 0.0, 0.0, f14, vb);
            tessellator.addVertexWithUV(f18, 0.0, 0.0F - thickness, f14, vb);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for(int l = 0; l < tileWidth; ++l) {
            float f11 = (float)l / (float)tileWidth;
            float f15 = vb + (vt - vb) * f11 - foon;
            float f19 = f4 * f11 + goon;
            tessellator.addVertexWithUV(0.0, f19, 0.0, ur, f15);
            tessellator.addVertexWithUV(f4, f19, 0.0, ul, f15);
            tessellator.addVertexWithUV(f4, f19, 0.0F - thickness, ul, f15);
            tessellator.addVertexWithUV(0.0, f19, 0.0F - thickness, ur, f15);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for(int i1 = 0; i1 < tileWidth; ++i1) {
            float f12 = (float)i1 / (float)tileWidth;
            float f16 = vb + (vt - vb) * f12 - foon;
            float f20 = f4 * f12;
            tessellator.addVertexWithUV(f4, f20, 0.0, ul, f16);
            tessellator.addVertexWithUV(0.0, f20, 0.0, ur, f16);
            tessellator.addVertexWithUV(0.0, f20, 0.0F - thickness, ur, f16);
            tessellator.addVertexWithUV(f4, f20, 0.0F - thickness, ul, f16);
        }

        tessellator.draw();
        GL11.glDisable(32826);

        GL11.glPopMatrix();
    }
    public static void renderItem3dColored(Entity entity, ItemStack itemstack, boolean handheldTransform){
        IColored iColored = (IColored) itemstack.getItem();
        for (ColoredTexture coloredTex: iColored.getTextures(itemstack)) {
            renderItem3dSingleLayer(entity, coloredTex, handheldTransform);
        }
    }
}
