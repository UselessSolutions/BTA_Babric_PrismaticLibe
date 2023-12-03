package useless.prismaticlibe;

import java.awt.*;

public class ColoredArmorTexture {
    protected String  armorTexture;
    protected Color color;

    public ColoredArmorTexture(String armorTexture, Color color){
        this.armorTexture = armorTexture;
        this.color = color;
    }
    public String getArmorTexture(){
        return armorTexture;
    }
    public Color getColor(){
        return color;
    }
}
