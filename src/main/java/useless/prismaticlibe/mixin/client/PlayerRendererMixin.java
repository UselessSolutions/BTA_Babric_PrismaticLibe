package useless.prismaticlibe.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.prismaticlibe.IColored;

@Mixin(value = PlayerRenderer.class, remap = false)
public abstract class PlayerRendererMixin extends LivingRenderer<EntityPlayer> {
    public PlayerRendererMixin(ModelBase modelbase, float shadowSize) {
        super(modelbase, shadowSize);
    }
    @Shadow
    private ModelBiped modelArmor;


    @Shadow protected abstract boolean setArmorModel(EntityPlayer entity, int i, float f);

    @Inject(method = "setArmorModel(Lnet/minecraft/core/entity/player/EntityPlayer;IF)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerRenderer;setRenderPassModel(Lnet/minecraft/client/render/model/ModelBase;)V"))
    private void injected(EntityPlayer entity, int i, float f, CallbackInfoReturnable<Boolean> cir){
        ItemStack itemstack = entity.inventory.armorItemInSlot(3 - i);
        if (itemstack != null) {
            Item item = itemstack.getItem();
            if (item instanceof IColored){
                float brightness = entity.getBrightness(1f);
                if (Minecraft.getMinecraft(this).fullbright){
                    brightness = 1;
                }
                int color = ((IColored) item).baseColor();
                float[] baseColorRGB = new float[] {(float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f};
                GL11.glColor3f(baseColorRGB[0] * brightness, baseColorRGB[1] * brightness, baseColorRGB[2] * brightness);
            }
        }
    }
    @Inject(method = "drawFirstPersonHand(Lnet/minecraft/core/entity/player/EntityPlayer;)V", at = @At("TAIL"))
    private void renderArmArmor(EntityPlayer player, CallbackInfo ci){
        if (this.renderDispatcher.renderEngine != null) {
            this.setArmorModel(player, 1, 0);
            renderPassModel.onGround = 0.0f;
            renderPassModel.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0725f);
            ((ModelBiped) renderPassModel).bipedRightArm.render(0.0650f);
        }
    }
}
