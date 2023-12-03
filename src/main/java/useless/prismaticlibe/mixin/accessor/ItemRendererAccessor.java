package useless.prismaticlibe.mixin.accessor;

import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.RenderBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ItemRenderer.class, remap = false)
public interface ItemRendererAccessor {
    @Accessor
    RenderBlocks getRenderBlocksInstance();
}
