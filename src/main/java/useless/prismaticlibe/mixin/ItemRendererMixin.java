package useless.prismaticlibe.mixin;

import net.minecraft.client.render.ItemRenderer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.prismaticlibe.ColoredItemRenderer;
import useless.prismaticlibe.IColored;

@Mixin(value = ItemRenderer.class, remap = false)
public class ItemRendererMixin {
    @Inject(method = "renderItem(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/item/ItemStack;Z)V", at = @At("HEAD"), cancellable = true)
    private void renderColoredItem(Entity entity, ItemStack itemstack, boolean handheldTransform, CallbackInfo ci){
        if (itemstack.getItem() instanceof IColored){
            ColoredItemRenderer.renderItem3dColored(entity, itemstack, handheldTransform);
            ci.cancel();
        }
    }

}
