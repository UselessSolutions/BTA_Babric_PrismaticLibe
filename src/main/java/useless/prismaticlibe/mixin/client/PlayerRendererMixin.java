package useless.prismaticlibe.mixin.client;

import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.prismaticlibe.IColored;

@Mixin(value = PlayerRenderer.class, remap = false)
public class PlayerRendererMixin extends LivingRenderer<EntityPlayer> {
    public PlayerRendererMixin(ModelBase modelbase, float shadowSize) {
        super(modelbase, shadowSize);
    }

    @Inject(method = "setArmorModel(Lnet/minecraft/core/entity/player/EntityPlayer;IF)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerRenderer;setRenderPassModel(Lnet/minecraft/client/render/model/ModelBase;)V"))
    private void injected(EntityPlayer entity, int i, float f, CallbackInfoReturnable<Boolean> cir){
        ItemStack itemstack = entity.inventory.armorItemInSlot(3 - i);
        if (itemstack != null) {
            Item item = itemstack.getItem();
            if (item instanceof IColored){
                int color = ((IColored) item).baseColor();
                float[] baseColorRGB = new float[] {(float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f};
                GL11.glColor3f(baseColorRGB[0], baseColorRGB[1], baseColorRGB[2]);
            }
        }
    }
}
