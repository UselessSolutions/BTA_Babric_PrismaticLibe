package useless.prismaticlibe.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiRenderItem;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.prismaticlibe.gui.slot.IAlpha;
import useless.prismaticlibe.gui.slot.IHighlighting;
import useless.prismaticlibe.gui.slot.IResizable;

@Mixin(value = GuiRenderItem.class, remap = false, priority = 0)
public class GuiRenderItemMixin extends Gui {
    @Unique
    private float renderScale;
    @Unique
    private boolean isDiscovered;
    @Unique
    private int slotSize;
    @Unique
    private boolean drawBackground;
    @Unique
    private float itemAlpha;

    @Inject(method = "render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V", at = @At(value = "HEAD"))
    public void renderInjectHead(ItemStack itemStack, int x, int y, boolean isSelected, Slot slot, CallbackInfo cbi){
        drawBackground = isSelected;

        if (slot != null) {
            isDiscovered = slot.discovered;
        } else {
            isDiscovered = true;
        }


        if (slot instanceof IResizable){
            slotSize = ((IResizable) slot).getWidth();
            renderScale = (slotSize)/18f;
        } else {
            slotSize = 18;
            renderScale = 1f;
        }
        
        if (slot instanceof IAlpha){
            itemAlpha = ((IAlpha) slot).getStackAlpha();
        } else {
            itemAlpha = 1f;
        }

        if(slot instanceof IHighlighting){
            drawBackground = ((IHighlighting) slot).drawStandardHighlight();
        }
    }

    @Redirect(method = "render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/ItemEntityRenderer;renderItemIntoGUI(Lnet/minecraft/client/render/FontRenderer;Lnet/minecraft/client/render/RenderEngine;Lnet/minecraft/core/item/ItemStack;IIFF)V"))
    private void drawItemRedirect(ItemEntityRenderer itemRenderer, FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int i, int j, float brightness, float alpha){
        int newX = (int)(i * (1/renderScale));
        int newY = (int)(j * (1/renderScale));
        GL11.glScaled(renderScale, renderScale, renderScale);
        itemRenderer.renderItemIntoGUI(fontrenderer, renderengine, itemstack, newX, newY, brightness, alpha);
    }

    @Redirect(method = "render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/ItemEntityRenderer;renderItemOverlayIntoGUI(Lnet/minecraft/client/render/FontRenderer;Lnet/minecraft/client/render/RenderEngine;Lnet/minecraft/core/item/ItemStack;IILjava/lang/String;F)V"))
    private void drawTextRedirect(ItemEntityRenderer instance, FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int x, int y, String override, float alpha){
        int newX = (int)(x * (1/renderScale));
        int newY = (int)(y * (1/renderScale));
        instance.renderItemOverlayIntoGUI(fontrenderer, renderengine, itemstack, newX, newY,  isDiscovered ? null : "?", itemAlpha);
        GL11.glScaled(1/renderScale, 1/renderScale, 1/renderScale);
    }

    @Redirect(method = "render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiRenderItem;drawRect(IIIII)V"))
    private void drawRectRedirect(GuiRenderItem guiRenderItem,int minX, int minY, int maxX, int maxY, int argb){
        if (drawBackground){
            this.drawRect(minX, minY,minX + slotSize-2,minY + slotSize-2, -2130706433);
        }

    }
    @Inject(method = "render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glDisable(I)V", ordinal = 4, shift = At.Shift.BEFORE))
    private void drawHighlight(ItemStack itemStack, int x, int y, boolean isSelected, Slot slot, CallbackInfo ci){
        if(slot instanceof IHighlighting && ((IHighlighting)slot).isHighlighted()){
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            this.drawRect(x, y, x + slotSize-2, y + slotSize-2, 0x80000000 + ((IHighlighting)slot).getHighlightColor());
            GL11.glEnable(2896);
            GL11.glEnable(2929);
        }
    }
}
