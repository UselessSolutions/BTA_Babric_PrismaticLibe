package useless.prismaticlibe.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.prismaticlibe.IColoredArmor;

import java.awt.*;

@Mixin(value = PlayerRenderer.class, remap = false)
public class PlayerRendererMixin {
    @Unique
    private final Minecraft mc = Minecraft.getMinecraft(this);
    @Inject(method = "setArmorModel(Lnet/minecraft/core/entity/player/EntityPlayer;IF)Z", at = @At("HEAD"))
    private void colorArmor(EntityPlayer entity, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
        float brightness = mc.fullbright ? 1f : entity.getBrightness(0);
        GL11.glColor4f(brightness,brightness,brightness,brightness);
        ItemStack itemstack = entity.inventory.armorItemInSlot(3 - renderPass);
        if (itemstack != null && itemstack.getItem() instanceof IColoredArmor){
            Color color = ((IColoredArmor) itemstack.getItem()).getArmorColor(itemstack);
            GL11.glColor4f((color.getRed()/255f) * brightness, (color.getGreen()/255f) * brightness, (color.getBlue()/255f) * brightness,color.getAlpha()/255f);
        }
    }
    @Inject(method = "setArmorModel(Lnet/minecraft/core/entity/player/EntityPlayer;IF)Z", at = @At("TAIL"))
    private void colorArmorOff(EntityPlayer entity, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
        float brightness = mc.fullbright ? 1f : entity.getBrightness(0);
        GL11.glColor4f(brightness,brightness,brightness,brightness);
    }
}
