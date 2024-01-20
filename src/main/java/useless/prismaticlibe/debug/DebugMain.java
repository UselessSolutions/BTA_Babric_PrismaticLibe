package useless.prismaticlibe.debug;

import net.minecraft.core.item.material.ArmorMaterial;
import org.jetbrains.annotations.ApiStatus;
import turniplabs.halplibe.helper.ArmorHelper;
@ApiStatus.Internal
public class DebugMain {
    public static void init(){
        int id = 19000;
        new ItemColored(id++);
        ArmorMaterial material = ArmorHelper.createArmorMaterial("cloth", 10, 50,50,50,50);
        new ItemArmorColored("helm", id++, material, 0);
        new ItemArmorColored("ches", id++, material, 1);
        new ItemArmorColored("leg", id++, material, 2);
        new ItemArmorColored("boot", id++, material, 3);
    }
}
