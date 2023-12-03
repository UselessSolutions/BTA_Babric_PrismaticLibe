package useless.prismaticlibe;

import net.minecraft.core.block.Block;
import turniplabs.halplibe.helper.TextureHelper;

import java.awt.*;

public class ColoredTexture {
    protected int[] textureCoordinates;
    protected Color color;
    public ColoredTexture(int[] textureCoordinates, Color color){
        this.textureCoordinates = textureCoordinates;
        this.color = color;
    }
    public ColoredTexture(String modID, String itemTextureSource, Color color){
        this(TextureHelper.getOrCreateItemTexture(modID, itemTextureSource), color);
    }
    public int getTextureIndex(){
        return Block.texCoordToIndex(textureCoordinates[0], textureCoordinates[1]);
    }
    public int[] getTexture(){
        return textureCoordinates;
    }
    public int getColorInt(){
        return color.getRGB();
    }
    public Color getColor(){
        return color;
    }
}
