package useless.prismaticlibe.mixin.accessor;

import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(value = ItemEntityRenderer.class, remap = false)
public interface ItemEntityRendererAccessor extends EntityRendererAccessor {
    @Accessor
    RenderBlocks getRenderBlocks();
    @Accessor
    Random getRandom();
}
