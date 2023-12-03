package useless.prismaticlibe.mixin.accessor;

import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = EntityRenderer.class, remap = false)
public interface EntityRendererAccessor {
    @Accessor
    EntityRenderDispatcher getRenderDispatcher();
    @Invoker("loadTexture")
    void invokeLoadTexture(String texture);
}
