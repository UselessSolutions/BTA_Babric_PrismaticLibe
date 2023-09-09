package useless.prismaticlibe.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.*;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.prismaticlibe.IColored;

@Mixin(value = ItemRenderer.class, remap = false)
public class ItemRendererMixin {
    @Inject(method = "renderItem(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/item/ItemStack;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/Item;getIconIndex(Lnet/minecraft/core/item/ItemStack;)I", shift = At.Shift.BEFORE), cancellable = true)
    private void shiftToColored(Entity entity, ItemStack itemstack, boolean handheldTransform, CallbackInfo ci){
        if (itemstack.getItem() instanceof IColored){
            int tileWidth;
            if (itemstack.itemID < Block.blocksList.length) {
                tileWidth = TextureFX.tileWidthTerrain;
            } else {
                tileWidth = TextureFX.tileWidthItems;
            }
            renderColoredItemHand(entity, itemstack, handheldTransform, tileWidth);
            GL11.glPopMatrix();
            ci.cancel();
        }
    }
    @Unique
    public void renderColoredItemHand(Entity entity, ItemStack itemstack, boolean handheldTransform, int tileWidth){
        Tessellator tessellator = Tessellator.instance;
        IColored coloredItem = (IColored) itemstack.getItem();

        int baseTextureIndex = Item.iconCoordToIndex(coloredItem.baseTexture()[0], coloredItem.baseTexture()[1]); // Specified texture
        int baseColor = coloredItem.baseColor();
        int overlayTextureIndex = Item.iconCoordToIndex(coloredItem.overlayTexture()[0], coloredItem.overlayTexture()[1]); // Specified texture
        int overlayColor = coloredItem.overlayColor();

        float brightness = entity.getBrightness(1f);
        if (Minecraft.getMinecraft(this).fullbright) {
            brightness = 1.0f;
        }

        // UV coordinates
        renderModel(tessellator, tileWidth, baseTextureIndex, baseColor, brightness, handheldTransform);
        renderModel(tessellator, tileWidth, overlayTextureIndex, overlayColor, brightness, false);
        GL11.glDisable(32826);
    }

    @Unique
    public void renderModel(Tessellator tessellator, int tileWidth, int textureIndex, int color, float brightness, boolean handheldTransform){
        float red = ((float)(color >> 16 & 0xFF) / 255.0f) * brightness;
        float green = ((float)(color >> 8 & 0xFF) / 255.0f) * brightness;
        float blue = ((float)(color & 0xFF) / 255.0f) * brightness;

        float f = ((float)(textureIndex % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) + 0.0f) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f1 = ((float)(textureIndex % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) + ((float)tileWidth - 0.01f)) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f2 = ((float)(textureIndex / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) + 0.0f) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f3 = ((float)(textureIndex / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) + ((float)tileWidth - 0.01f)) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);

        float f4 = 1.0f;
        float f5 = 0.0f;
        float f6 = 0.3f;
        float foon = 0.5f / (float)tileWidth / (float)Global.TEXTURE_ATLAS_WIDTH_TILES;
        float goon = 0.0625f * (16.0f / (float)tileWidth);
        GL11.glEnable(32826);
        if (handheldTransform) { // Moves item to appropriate hand position
            GL11.glTranslatef(-f5, -f6, 0.0f);
            float f7 = 1.5f;
            GL11.glScalef(f7, f7, f7);
            GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
        }
        float thickness = 0.0625f;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(red,green,blue);
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, f1, f3);
        tessellator.addVertexWithUV(f4, 0.0, 0.0, f, f3);
        tessellator.addVertexWithUV(f4, 1.0, 0.0, f, f2);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, f1, f2);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(red,green,blue);
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0f - thickness, f1, f2);
        tessellator.addVertexWithUV(f4, 1.0, 0.0f - thickness, f, f2);
        tessellator.addVertexWithUV(f4, 0.0, 0.0f - thickness, f, f3);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0f - thickness, f1, f3);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(red,green,blue);
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        for (int j = 0; j < tileWidth; ++j) {
            float f9 = (float)j / (float)tileWidth;
            float f13 = f1 + (f - f1) * f9 - foon;
            float f17 = f4 * f9;
            tessellator.addVertexWithUV(f17, 0.0, 0.0f - thickness, f13, f3);
            tessellator.addVertexWithUV(f17, 0.0, 0.0, f13, f3);
            tessellator.addVertexWithUV(f17, 1.0, 0.0, f13, f2);
            tessellator.addVertexWithUV(f17, 1.0, 0.0f - thickness, f13, f2);
        }
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(red,green,blue);
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        for (int k = 0; k < tileWidth; ++k) {
            float f10 = (float)k / (float)tileWidth;
            float f14 = f1 + (f - f1) * f10 - foon;
            float f18 = f4 * f10 + goon;
            tessellator.addVertexWithUV(f18, 1.0, 0.0f - thickness, f14, f2);
            tessellator.addVertexWithUV(f18, 1.0, 0.0, f14, f2);
            tessellator.addVertexWithUV(f18, 0.0, 0.0, f14, f3);
            tessellator.addVertexWithUV(f18, 0.0, 0.0f - thickness, f14, f3);
        }
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(red,green,blue);
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        for (int l = 0; l < tileWidth; ++l) {
            float f11 = (float)l / (float)tileWidth;
            float f15 = f3 + (f2 - f3) * f11 - foon;
            float f19 = f4 * f11 + goon;
            tessellator.addVertexWithUV(0.0, f19, 0.0, f1, f15);
            tessellator.addVertexWithUV(f4, f19, 0.0, f, f15);
            tessellator.addVertexWithUV(f4, f19, 0.0f - thickness, f, f15);
            tessellator.addVertexWithUV(0.0, f19, 0.0f - thickness, f1, f15);
        }
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(red,green,blue);
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        for (int i1 = 0; i1 < tileWidth; ++i1) {
            float f12 = (float)i1 / (float)tileWidth;
            float f16 = f3 + (f2 - f3) * f12 - foon;
            float f20 = f4 * f12;
            tessellator.addVertexWithUV(f4, f20, 0.0, f, f16);
            tessellator.addVertexWithUV(0.0, f20, 0.0, f1, f16);
            tessellator.addVertexWithUV(0.0, f20, 0.0f - thickness, f1, f16);
            tessellator.addVertexWithUV(f4, f20, 0.0f - thickness, f, f16);
        }
        tessellator.draw();
    }
}
