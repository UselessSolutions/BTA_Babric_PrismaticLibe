package useless.prismaticlibe.debug;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import useless.prismaticlibe.ColoredTexture;
import useless.prismaticlibe.IColored;

import java.awt.*;

public class ItemColored extends Item implements IColored {
    private final ColoredTexture[] textures = new ColoredTexture[]{
            new ColoredTexture(new int[]{11,1}, new Color(255,255,0, 255)),
            new ColoredTexture(new int[]{12,1}, new Color(0,255,255, 128)),
            new ColoredTexture(new int[]{5,9}, new Color(255,0,0, 32))};
    public ItemColored(int id) {
        super(id);
    }
    @Override
    public ColoredTexture[] getTextures(ItemStack stack) {
        return textures;
    }
}
