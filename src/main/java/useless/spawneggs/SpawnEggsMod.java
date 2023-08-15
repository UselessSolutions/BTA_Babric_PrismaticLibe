package useless.spawneggs;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.TextureHelper;


public class SpawnEggsMod implements ModInitializer {
    static {
        //this is here to possibly fix some class loading issues, do not delete
        try {
            Class.forName("net.minecraft.core.block.Block");
            Class.forName("net.minecraft.core.item.Item");
        } catch (ClassNotFoundException ignored) {}
    }
    public static final String MOD_ID = "spawneggs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final int[] baseEgg = TextureHelper.registerItemTexture(SpawnEggsMod.MOD_ID, "baseEgg.png");
    public static final int[] overlayEgg = TextureHelper.registerItemTexture(SpawnEggsMod.MOD_ID, "overlayEgg.png");
    public static Item spawnEggChicken;
    public static Item spawnEggCow;
    public static Item spawnEggCreeper;
    public static Item spawnEggGhast;
    public static Item spawnEggPig;
    public static Item spawnEggSheep;
    public static Item spawnEggSkeleton;
    public static Item spawnEggSlime;
    public static Item spawnEggSpider;
    public static Item spawnEggSquid;
    public static Item spawnEggWolf;
    public static Item spawnEggZombie;
    public static Item spawnEggScorpion;
    public static Item spawnEggSnowman;
    public static Item spawnEggPigZombie;
    public static Item spawnEggArmouredZombie;
    public static Item spawnEggMonster;
    public static Item spawnEggGiant;
    public static Item spawnEggFireflyCluster;


    @Override
    public void onInitialize() {
        int num = Block.blocksList.length + 1000;
        int arrayPos = 0;
        String[] entities = new String[] {"Chicken", "Cow", "Creeper", "Ghast", "Pig", "Sheep", "Skeleton", "Slime", "Spider", "Squid", "Wolf", "Zombie", "Scorpion", "Snowman", "PigZombie", "ArmouredZombie", "Monster", "Giant", "FireflyCluster"};

        spawnEggChicken = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0XA1A1A1, 0XFF0000),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggCow = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0X443626, 0XA1A1A1),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggCreeper = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0X0DA70B, 0X000000),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggGhast = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0xF9F9F9, 0xBCBCBC),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggPig = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0xF0A5A2, 0xDB635F),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggSheep = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0xE7E7E7,0xFFB5B5),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggSkeleton = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0xC1C1C1, 0x494949),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggSlime = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x51A03E, 0x7EBF6E),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggSpider = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x342D27, 0xA80E0E),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggSquid = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x223B4D, 0x708899),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggWolf = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0xD7D3D3, 0xCEAF96),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggZombie = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x00AFAF, 0x799C65),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggScorpion = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x888362, 0xE0D989),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggSnowman = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x0f6496, 0xd3d3d3),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggPigZombie = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0xEA9393, 0x4C7129),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggArmouredZombie = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x735300, 0x799C65),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggMonster = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x4639a4, 0x00AFAF),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggGiant = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos],0x009F9F, 0x496C35),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggFireflyCluster = ItemHelper.createItem(MOD_ID,
                new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos], 0x000000, 0xFFFF00),
                "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");


        LOGGER.info("SpawnEggsMod initialized.");
    }
}
