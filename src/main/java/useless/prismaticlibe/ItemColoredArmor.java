package useless.prismaticlibe;

import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;

import java.awt.*;

public class ItemColoredArmor extends ItemArmor implements IColored {
    public ItemColoredArmor(String name, int id, ArmorMaterial material, int armorPiece) {
        super(name, id, material, armorPiece);
    }

    @Override
    public int baseColor() {
        float hue = (System.currentTimeMillis()%10000)/10000f;
        return Color.HSBtoRGB(hue, 1, 1);
    }

    @Override
    public int overlayColor() {
        return 0xFF0000;
    }

    @Override
    public int[] baseTexture() {
        return new int[]{1,1};
    }

    @Override
    public int[] overlayTexture() {
        return new int[]{1,1};
    }
}
