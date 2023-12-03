package useless.prismaticlibe.mixin;

import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.prismaticlibe.ColoredItemRenderer;
import useless.prismaticlibe.IColored;

@Mixin(value = ItemEntityRenderer.class, remap = false)
public abstract class ItemEntityRendererMixin extends EntityRenderer<EntityItem> {
    @Inject(method = "doRenderItem(Lnet/minecraft/core/entity/EntityItem;DDDFF)V", at = @At("HEAD"), cancellable = true)
    private void renderItem(EntityItem entity, double d, double d1, double d2, float f, float f1, CallbackInfo ci){
        if (entity.item.getItem() instanceof IColored){
            ColoredItemRenderer.doRenderItem((ItemEntityRenderer)(Object) this, entity, d, d1, d2, f, f1);
            ci.cancel();
        }
    }
    @Inject(method = "renderItemIntoGUI(Lnet/minecraft/client/render/FontRenderer;Lnet/minecraft/client/render/RenderEngine;Lnet/minecraft/core/item/ItemStack;IIF)V", at = @At("HEAD"), cancellable = true)
    private void coloredItemRendering0(FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int x, int y, float alpha, CallbackInfo ci){
        if (itemstack != null && itemstack.getItem() instanceof IColored){
            ColoredItemRenderer.drawItemIntoGui((ItemEntityRenderer)(Object) this, fontrenderer, renderengine, itemstack, x, y, 1.0f);
            ci.cancel();
        }
    }
    @Inject(method = "renderItemIntoGUI(Lnet/minecraft/client/render/FontRenderer;Lnet/minecraft/client/render/RenderEngine;Lnet/minecraft/core/item/ItemStack;IIFF)V", at = @At("HEAD"), cancellable = true)
    private void coloredItemRendering1(FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int x, int y, float brightness, float alpha, CallbackInfo ci){
        if (itemstack != null && itemstack.getItem() instanceof IColored){
            ColoredItemRenderer.drawItemIntoGui((ItemEntityRenderer)(Object) this, fontrenderer, renderengine, itemstack, x, y, brightness);
            ci.cancel();
        }
    }

}
