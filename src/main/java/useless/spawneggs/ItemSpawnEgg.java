package useless.spawneggs;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityEgg;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.CommandError;
import net.minecraft.core.net.command.LocationTarget;
import net.minecraft.core.net.command.commands.SummonCommand;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import turniplabs.halplibe.helper.TextureHelper;

import java.util.Set;

public class ItemSpawnEgg extends Item implements IColored{
    private int colorBase;
    private int colorOverlay;

    String entityName;
    public ItemSpawnEgg(String name, int id, String entityName, int colorBase, int colorOverlay) {
        super(name, id);
        this.entityName = entityName;
        this.colorBase = colorBase;
        this.colorOverlay = colorOverlay;
    }
    public ItemSpawnEgg(String name, int id, String entityName) {
        super(name, id);
        this.entityName = entityName;
        this.colorBase = 0XFFFFFF;
        this.colorOverlay = 0XFFFFFF;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        itemstack.consumeItem(entityplayer);
        if (!world.isClientSide) {
            Set<String> keySet = EntityDispatcher.stringToClassMapping.keySet();
            String entityID = entityName;
            Class<? extends Entity> entityClass = null;
            for (String str : keySet) {
                if (!str.equalsIgnoreCase(entityID)) continue;
                entityID = str;
                entityClass = EntityDispatcher.stringToClassMapping.get(entityID);
            }
            if (entityClass == null) {
                throw new CommandError("Could not find entity \"" + entityID + "\"");
            }
            Entity entity = createEntity(entityClass, world);
            entity.moveTo(blockX + 0.5F, blockY + 1, blockZ + 0.5F, entityplayer.yRot, 0.0f);
            world.entityJoinedWorld(entity);
        }
        return true;
    }
    public static Entity createEntity(Class<? extends Entity> entityClass, World world) {
        try {
            return entityClass.getConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            throw new CommandError("Could not create Entity!");
        }
    }

    @Override
    public int baseColor() {
        return colorBase;
    }

    @Override
    public int overlayColor() {
        return colorOverlay;
    }

    @Override
    public int[] baseTexture() {
        return SpawnEggsMod.baseEgg;
    }

    @Override
    public int[] overlayTexture() {
        return SpawnEggsMod.overlayEgg;
    }
}
