package useless.prismaticlibe;

import net.minecraft.core.item.ItemStack;

// Items that implement IColored will use the colored item renderer
public interface IColored {
    ColoredTexture[] getTextures(ItemStack stack);
}
