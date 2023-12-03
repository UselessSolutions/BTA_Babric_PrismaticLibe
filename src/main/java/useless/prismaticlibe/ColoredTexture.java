package useless.prismaticlibe;

import net.minecraft.core.block.Block;

import java.awt.*;

public class ColoredTexture {
    protected int[] textureCoordinates;
    protected Color color;
    public ColoredTexture(int[] textureCoordinates, Color color){
        this.textureCoordinates = textureCoordinates;
        this.color = color;
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
