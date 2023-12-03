package useless.prismaticlibe.mixin;

import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import useless.prismaticlibe.ColoredArmorTexture;
import useless.prismaticlibe.IColoredArmor;
import useless.prismaticlibe.PrismaticLibe;

@Mixin(value = LivingRenderer.class, remap = false)
public abstract class LivingRendererMixin<T extends EntityLiving> {
    @Shadow protected abstract boolean shouldRenderPass(T entity, int renderPass, float partialTick);

    @Shadow protected ModelBase renderPassModel;
    @Unique
    float limbSwing;
    @Unique
    float limbYaw;
    @Unique
    float ticksExisted;
    @Unique
    float headYaw;
    @Unique
    float headYawOffset;
    @Unique
    float headPitch;
    @Unique
    float scale;
    @Inject(method = "render(Lnet/minecraft/core/entity/EntityLiving;DDDFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingRenderer;shouldRenderPass(Lnet/minecraft/core/entity/EntityLiving;IF)Z", shift = At.Shift.BEFORE),
    locals = LocalCapture.CAPTURE_FAILHARD)
    private void captureLocals(T entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci, float headYawOffset, float headYaw, float headPitch, float ticksExisted, float scale, float limbYaw, float limbSwing, int renderPass){
        this.limbSwing = limbSwing;
        this.limbYaw = limbYaw;
        this.ticksExisted = ticksExisted;
        this.headYaw = headYaw;
        this.headYawOffset = headYawOffset;
        this.headPitch = headPitch;
        this.scale = scale;
    }
    @Redirect(method = "render(Lnet/minecraft/core/entity/EntityLiving;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingRenderer;shouldRenderPass(Lnet/minecraft/core/entity/EntityLiving;IF)Z"))
    private boolean hijackRenderPass(LivingRenderer instance, T entity, int renderPass, float partialTick){
        PrismaticLibe.playerArmorRenderOffset = 0;
        if (entity instanceof EntityPlayer){
            ItemStack itemstack = ((EntityPlayer) entity).inventory.armorItemInSlot(3 - renderPass);
            if (itemstack != null && itemstack.getItem() instanceof IColoredArmor){
                // do stuff
                ColoredArmorTexture[] cTex = ((IColoredArmor) itemstack.getItem()).getArmorTextures(itemstack);
                for (ColoredArmorTexture ignored : cTex) {
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(770, 771);
                    shouldRenderPass(entity, renderPass, partialTick);
                    this.renderPassModel.render(limbSwing, limbYaw, ticksExisted, headYaw - headYawOffset, headPitch, scale);
                    GL11.glDisable(3042);
                    GL11.glEnable(3008);
                    PrismaticLibe.playerArmorRenderOffset++;
                }
                PrismaticLibe.playerArmorRenderOffset = 0;
                return false;
            }
        }
        return this.shouldRenderPass(entity, renderPass, partialTick);
    }
}
