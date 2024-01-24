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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.prismaticlibe.ColoredArmorTexture;
import useless.prismaticlibe.IColoredArmor;
import useless.prismaticlibe.PrismaticLibe;
import useless.prismaticlibe.mixin.accessor.EntityRendererAccessor;

import java.awt.*;

@Mixin(value = PlayerRenderer.class, remap = false)
public class PlayerRendererMixin {
    @Unique
    private final Minecraft mc = Minecraft.getMinecraft(this);
    @Unique
    private ColoredArmorTexture[] armorTextures;
    @Inject(method = "setArmorModel(Lnet/minecraft/core/entity/player/EntityPlayer;IF)Z", at = @At("HEAD"))
    private void colorArmor(EntityPlayer entity, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
        float brightness = mc.fullbright ? 1f : entity.getBrightness(0);
        GL11.glColor4f(brightness,brightness,brightness,brightness);
        ItemStack itemstack = entity.inventory.armorItemInSlot(3 - renderPass);
        armorTextures = null;
        if (itemstack != null && itemstack.getItem() instanceof IColoredArmor){
            armorTextures =((IColoredArmor) itemstack.getItem()).getArmorTextures(itemstack);
            if (PrismaticLibe.playerArmorRenderOffset > armorTextures.length) return;
            Color color = armorTextures[PrismaticLibe.playerArmorRenderOffset].getColor();
            GL11.glColor4f((color.getRed()/255f) * brightness, (color.getGreen()/255f) * brightness, (color.getBlue()/255f) * brightness,color.getAlpha()/255f);
        }
    }
    @Inject(method = "setArmorModel(Lnet/minecraft/core/entity/player/EntityPlayer;IF)Z", at = @At("TAIL"))
    private void colorArmorOff(EntityPlayer entity, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
        float brightness = mc.fullbright ? 1f : entity.getBrightness(0);
        GL11.glColor4f(brightness,brightness,brightness,1f);
    }
    @Redirect(method = "setArmorModel(Lnet/minecraft/core/entity/player/EntityPlayer;IF)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerRenderer;loadTexture(Ljava/lang/String;)V", ordinal = 3))
    private void customArmorTexture(PlayerRenderer instance, String string){
        if (armorTextures != null){
            if (PrismaticLibe.playerArmorRenderOffset > armorTextures.length) return;
            String tmp = string.replace(".png", "");
            int renderPass = Integer.decode(String.valueOf(tmp.charAt(tmp.length()-1)));
            ((EntityRendererAccessor)instance).invokeLoadTexture("/armor/" + armorTextures[PrismaticLibe.playerArmorRenderOffset].getArmorTexture() + "_" + (renderPass != 2 ? 1 : 2) + ".png");
        } else {
            ((EntityRendererAccessor)instance).invokeLoadTexture(string);
        }
    }
}
