package useless.prismaticlibe.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.prismaticlibe.IColored;

import java.util.Random;

@Mixin(value = ItemEntityRenderer.class, remap = false)
public abstract class ItemEntityRendererMixin extends EntityRenderer<EntityItem> {
    @Final
    @Shadow
    private final RenderBlocks renderBlocks = new RenderBlocks();
    @Final
    @Shadow
    private final Random random = new Random();
    @Shadow
    public boolean field_27004_a;

    @Inject(method = "doRenderItem(Lnet/minecraft/core/entity/EntityItem;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;getIconIndex()I"), cancellable = true)
    private void doRenderColoredItem(EntityItem entity, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
        ItemStack itemstack = entity.item;
        if (itemstack == null) {
            return;
        }
        if (itemstack.getItem() instanceof IColored){
            Item item = itemstack.getItem();
            if (item == null) {
                return;
            }
            float f2 = MathHelper.sin(((float)entity.age + f1) / 10.0f + entity.field_804_d) * 0.1f + 0.1f;
            float f3 = (((float)entity.age + f1) / 20.0f + entity.field_804_d) * 57.29578f;
            int renderCount = 1;
            if (entity.item.stackSize > 1) {
                renderCount = 2;
            }
            if (entity.item.stackSize > 5) {
                renderCount = 3;
            }
            if (entity.item.stackSize > 20) {
                renderCount = 4;
            }
            int tileWidth;
            if (itemstack.itemID < Block.blocksList.length) {
                this.loadTexture("/terrain.png");
                tileWidth = TextureFX.tileWidthTerrain;
            } else {
                this.loadTexture("/gui/items.png"); //
                tileWidth = TextureFX.tileWidthItems;
            }
            Tessellator tessellator = Tessellator.instance;
            if (this.field_27004_a) {
                int k = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getMetadata());
                float f15 = (float)(k >> 16 & 0xFF) / 255.0f;
                float f17 = (float)(k >> 8 & 0xFF) / 255.0f;
                float f19 = (float)(k & 0xFF) / 255.0f;
                float f21 = entity.getBrightness(f1);
                if (Minecraft.getMinecraft(this).fullbright || entity.item.getItem().hasTag(ItemTags.renderFullbright)) {
                    f21 = 1.0f;
                }
                GL11.glColor4f(f15 * f21, f17 * f21, f19 * f21, 1.0f);
            }
            if (Minecraft.getMinecraft(this).gameSettings.items3D.value) {
                GL11.glPushMatrix();
                GL11.glScaled(1.0, 1.0, 1.0);
                GL11.glRotated(f3, 0.0, 1.0, 0.0);
                GL11.glTranslated(-0.5, 0.0, -0.05 * (double)(renderCount - 1));
                for (int j = 0; j < renderCount; ++j) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0, 0.0, 0.1 * (double)j);
                    EntityRenderDispatcher.instance.itemRenderer.renderItem(entity, itemstack, false);
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();
            } else {
                IColored coloredItem = (IColored)item;
                int baseTextureIndex = Item.iconCoordToIndex(coloredItem.baseTexture()[0],(coloredItem.baseTexture()[1]));
                int baseColor = coloredItem.baseColor();

                int overlayTextureIndex = Item.iconCoordToIndex(coloredItem.overlayTexture()[0],(coloredItem.overlayTexture()[1]));
                int overlayColor = coloredItem.overlayColor();

                float brightness = 1.0f;
                if (this.field_27004_a) {
                    int k = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getMetadata());
                    float f15 = (float)(k >> 16 & 0xFF) / 255.0f;
                    float f17 = (float)(k >> 8 & 0xFF) / 255.0f;
                    float f19 = (float)(k & 0xFF) / 255.0f;
                    brightness = entity.getBrightness(f1);
                    if (Minecraft.getMinecraft(this).fullbright || entity.item.getItem().hasTag(ItemTags.renderFullbright)) {
                        brightness = 1.0f;
                    }
                    GL11.glColor4f(f15 * brightness, f17 * brightness, f19 * brightness, 1.0f);
                }
                for (int l = 0; l < renderCount; ++l) {

                    GL11.glPushMatrix();
                    if (l > 0) {
                        float f16 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        float f18 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        float f20 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        GL11.glTranslatef(f16, f18, f20);
                    }
                    GL11.glRotatef(180.0f - this.renderDispatcher.viewLerpYaw, 0.0f, 1.0f, 0.0f);

                    renderColorQuadSpace(tessellator, tileWidth, baseColor, brightness, baseTextureIndex);
                    renderColorQuadSpace(tessellator, tileWidth, overlayColor, brightness, overlayTextureIndex);

                    GL11.glPopMatrix();
                    GL11.glDisable(32826);
                    GL11.glPopMatrix();
                    ci.cancel();
                }
            }
        }
    }

    @Unique
    public void renderColorQuadSpace(Tessellator tessellator, int tileWidth, int color, float brightness, int textureIndex){

        float b1 = (float)(textureIndex % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float b2 = (float)(textureIndex % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth + tileWidth) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float b3 = (float)(textureIndex / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float b4 = (float)(textureIndex / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth + tileWidth) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float[] baseColorRGB = new float[] {(float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f};

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.setColorOpaque_F(baseColorRGB[0] * brightness, baseColorRGB[1] * brightness, baseColorRGB[2] * brightness);
        tessellator.addVertexWithUV(-0.5, -0.25, 0.0, b1, b4);
        tessellator.setColorOpaque_F(baseColorRGB[0] * brightness, baseColorRGB[1] * brightness, baseColorRGB[2] * brightness);
        tessellator.addVertexWithUV(0.5, -0.25, 0.0, b2, b4);
        tessellator.setColorOpaque_F(baseColorRGB[0] * brightness, baseColorRGB[1] * brightness, baseColorRGB[2] * brightness);
        tessellator.addVertexWithUV(0.5, 0.75, 0.0, b2, b3);
        tessellator.setColorOpaque_F(baseColorRGB[0] * brightness, baseColorRGB[1] * brightness, baseColorRGB[2] * brightness);
        tessellator.addVertexWithUV(-0.5, 0.75, 0.0, b1, b3);
        tessellator.draw();
    }

    // Just hotbar
    @Redirect(method = "renderItemIntoGUI(Lnet/minecraft/client/render/FontRenderer;Lnet/minecraft/client/render/RenderEngine;Lnet/minecraft/core/item/ItemStack;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ItemEntityRenderer;drawItemIntoGui(Lnet/minecraft/client/render/FontRenderer;Lnet/minecraft/client/render/RenderEngine;IIIIIFF)V"))
    private void renderColoredItemIntoGUI(ItemEntityRenderer instance, FontRenderer fontrenderer, RenderEngine renderengine, int item, int itemMeta, int iconIndex, int tileWidth, int tileHeight, float brightness, float alpha) {
        if (Item.itemsList[item] instanceof IColored){
            this.drawColoredItemIntoGui(fontrenderer, renderengine, item, itemMeta, iconIndex, tileWidth, tileHeight, brightness, alpha);
        }
        else {
            instance.drawItemIntoGui(fontrenderer, renderengine, item, itemMeta, iconIndex, tileWidth, tileHeight, brightness, alpha);
        }
    }
    @Redirect(method = "renderItemIntoGUI(Lnet/minecraft/client/render/FontRenderer;Lnet/minecraft/client/render/RenderEngine;Lnet/minecraft/core/item/ItemStack;IIFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ItemEntityRenderer;drawItemIntoGui(Lnet/minecraft/client/render/FontRenderer;Lnet/minecraft/client/render/RenderEngine;IIIIIFF)V"))
    private void renderColoredItemIntoGUIOther(ItemEntityRenderer instance, FontRenderer fontrenderer, RenderEngine renderengine, int item, int itemMeta, int iconIndex, int tileWidth, int tileHeight, float brightness, float alpha) {
        if (Item.itemsList[item] instanceof IColored){
            this.drawColoredItemIntoGui(fontrenderer, renderengine, item, itemMeta, iconIndex, tileWidth, tileHeight, brightness, alpha);
        }
        else {
            instance.drawItemIntoGui(fontrenderer, renderengine, item, itemMeta, iconIndex, tileWidth, tileHeight, brightness, alpha);
        }
    }

    @Unique
    public void drawColoredItemIntoGui(FontRenderer fontrenderer, RenderEngine renderengine, int itemId, int itemMeta, int j, int l, int i1, float brightness, float alpha) {
        IColored coloredItem = (IColored) Item.itemsList[itemId];
        int baseTextureIndex = Item.iconCoordToIndex(coloredItem.baseTexture()[0], coloredItem.baseTexture()[1]);
        int overlayTextureIndex = Item.iconCoordToIndex(coloredItem.overlayTexture()[0], coloredItem.overlayTexture()[1]);

        if (itemId < Block.blocksList.length && BlockModelDispatcher.getInstance().getDispatch(Block.blocksList[itemId]).shouldItemRender3d()) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
            Block block = Block.blocksList[itemId];
            GL11.glPushMatrix();
            GL11.glTranslatef(l - 2, i1 + 3, -3.0f);
            GL11.glScalef(10.0f, 10.0f, 10.0f);
            GL11.glTranslatef(1.0f, 0.5f, 1.0f);
            GL11.glScalef(1.0f, 1.0f, -1.0f);
            GL11.glRotatef(210.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            int l1 = Item.itemsList[itemId].getColorFromDamage(j);
            float f2 = (float)(l1 >> 16 & 0xFF) / 255.0f;
            float f4 = (float)(l1 >> 8 & 0xFF) / 255.0f;
            float f5 = (float)(l1 & 0xFF) / 255.0f;
            if (this.field_27004_a) {
                GL11.glColor4f(f2 * brightness, f4 * brightness, f5 * brightness, alpha);
            } else {
                GL11.glColor4f(brightness, brightness, brightness, alpha);
            }
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            this.renderBlocks.useInventoryTint = this.field_27004_a;
            this.renderBlocks.renderBlockOnInventory(block, j, brightness);
            this.renderBlocks.useInventoryTint = true;
            GL11.glPopMatrix();
            GL11.glDisable(3042);
        } else if (baseTextureIndex >= 0) {
            int tileWidth;

            GL11.glDisable(2896);
            if (itemId < Block.blocksList.length) {
                renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
                tileWidth = TextureFX.tileWidthTerrain;
            } else {
                renderengine.bindTexture(renderengine.getTexture("/gui/items.png"));
                tileWidth = TextureFX.tileWidthItems;
            }
            int k1 = Item.itemsList[itemId].getColorFromDamage(j);
            float f = (float)(k1 >> 16 & 0xFF) / 255.0f;
            float f1 = (float)(k1 >> 8 & 0xFF) / 255.0f;
            float f3 = (float)(k1 & 0xFF) / 255.0f;
            if (this.field_27004_a) {
                GL11.glColor4f(f * brightness, f1 * brightness, f3 * brightness, alpha);
            } else {
                GL11.glColor4f(brightness, brightness, brightness, alpha);
            }

            renderColoredQuadPlane(l, i1, baseTextureIndex, tileWidth, coloredItem.baseColor());
            renderColoredQuadPlane(l, i1, overlayTextureIndex, tileWidth, coloredItem.overlayColor());
            GL11.glEnable(2896);
        }
        GL11.glEnable(2884);
    }
    @Unique
    public void renderColoredQuadPlane(int x, int y, int textureIndex, int tileWidth, int color) {
        int tileX = textureIndex % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;
        int tileY = textureIndex / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;

        float f1 = 1.0f / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f2 = 1.0f / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float[] baseColorRGB = new float[] {(float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f};

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(baseColorRGB[0] , baseColorRGB[1] , baseColorRGB[2] );
        tessellator.addVertexWithUV(x, y + 16, 0.0, (float)(tileX) * f1, (float)(tileY + tileWidth) * f2);
        tessellator.setColorOpaque_F(baseColorRGB[0] , baseColorRGB[1] , baseColorRGB[2] );
        tessellator.addVertexWithUV(x + 16, y + 16, 0.0, (float)(tileX + tileWidth) * f1, (float)(tileY + tileWidth) * f2);
        tessellator.setColorOpaque_F(baseColorRGB[0] , baseColorRGB[1] , baseColorRGB[2] );
        tessellator.addVertexWithUV(x + 16, y, 0.0, (float)(tileX + tileWidth) * f1, (float)(tileY) * f2);
        tessellator.setColorOpaque_F(baseColorRGB[0] , baseColorRGB[1] , baseColorRGB[2] );
        tessellator.addVertexWithUV(x, y, 0.0, (float)(tileX) * f1, (float)(tileY) * f2);
        tessellator.draw();
    }
}
