package useless.prismaticlibe;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.ItemHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PrismaticLibe implements ModInitializer {
    public static final String MOD_ID = "prismaticlibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static int itemIdTacker = Block.blocksList.length + 5000;
    //armor
    public static final ArmorMaterial armorspacesuit = ArmorHelper.createArmorMaterial("space",256,25.0f,25.0f,30.0f,50.0f);

    public static final Item armorHelmetSpace = ItemHelper.createItem(MOD_ID, new ItemColoredArmor("Space Helmet", itemIdTacker++, armorspacesuit, 0), "armor.helmet.space", "space_helmet.png");
    public static final Item armorChestplateSpace = ItemHelper.createItem(MOD_ID, new ItemColoredArmor("Space Suit", itemIdTacker++, armorspacesuit, 1), "armor.chestplate.space", "space_suit.png");
    public static final Item armorLeggingsSpace = ItemHelper.createItem(MOD_ID, new ItemColoredArmor("Space Leggings", itemIdTacker++, armorspacesuit, 2), "armor.leggings.space", "space_pants.png");
    public static final Item armorBootsSpace = ItemHelper.createItem(MOD_ID, new ItemColoredArmor("Space Boots", itemIdTacker++, armorspacesuit, 3), "armor.boots.space", "space_boots.png");

    public static final ArmorMaterial armorclothing = ArmorHelper.createArmorMaterial("clothes",128,10.0f,0.0f,0.0f,10.0f);
    public static final Item armorHelmetCloth = ItemHelper.createItem(MOD_ID, new ItemArmor("Cloth Hood", itemIdTacker++, armorclothing, 0), "armor.helmet.clothes", "cloth_hood.png");
    public static final Item armorChestplateCloth = ItemHelper.createItem(MOD_ID, new ItemArmor("Cloth Shirt", itemIdTacker++, armorclothing, 1), "armor.chestplate.clothes", "cloth_shirt.png");
    public static final Item armorLeggingsCloth = ItemHelper.createItem(MOD_ID, new ItemArmor("Cloth Pants", itemIdTacker++, armorclothing, 2), "armor.leggings.clothes", "cloth_pants.png");
    public static final Item armorBootsCloth = ItemHelper.createItem(MOD_ID, new ItemArmor("Shoes", itemIdTacker++, armorclothing, 3), "armor.boots.clothes", "cloth_boots.png");

    @Override
    public void onInitialize() {
        LOGGER.info("PrismaticLibe initialized.");
    }
}
