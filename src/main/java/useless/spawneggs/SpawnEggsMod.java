package useless.spawneggs;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import turniplabs.halplibe.helper.ItemHelper;


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

        spawnEggChicken = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggCow = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggCreeper = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggGhast = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggPig = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggSheep = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggSkeleton = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggSlime = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggSpider = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggSquid = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggWolf = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggZombie = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggScorpion = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggSnowman = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggPigZombie = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggArmouredZombie = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos].toLowerCase(), "spawnEgg" + entities[arrayPos++] + ".png");
        spawnEggMonster = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggGiant = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");
        spawnEggFireflyCluster = ItemHelper.createItem(MOD_ID, new ItemSpawnEgg("spawn.egg." + entities[arrayPos].toLowerCase(), num++, entities[arrayPos]), "item.spawn.egg." + entities[arrayPos++].toLowerCase(), "spawnEggDefault.png");


        LOGGER.info("SpawnEggsMod initialized.");
    }
}
