package useless.prismaticlibe.helper;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import useless.prismaticlibe.PrismaticLibe;


public class ModCheckHelper {
    public static boolean checkForMod(String modID, String version){
        PrismaticLibe.LOGGER.debug("Searching for mod: " + modID + " with version: " + version);
        boolean[] state = new boolean[3]; // Equals; Less than; Greater than;
        if (version.contains(">")){
            state[2] = true;
        }
        if (version.contains("<")){
            state[1] = true;
        }
        if (version.contains("=")){
            state[0] = true;
        }

        ModContainer desiredMod = null;
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()){
            if (mod.getMetadata().getId().equals(modID)){
                PrismaticLibe.LOGGER.info("Found Mod: " + mod);
                desiredMod = mod;
            }
        }
        if (desiredMod == null) {return false;}

        Version modVersion = desiredMod.getMetadata().getVersion();

        try {
            int compareResult = modVersion.compareTo(Version.parse(version.replaceAll("[<>=]", "")));
            if (state[0] && compareResult == 0){
                return true;
            }
            if (state[1] && compareResult < 0){
                return true;
            }
            if (state[2] && compareResult > 0){
                return true;
            }
        }
        catch (VersionParsingException e){
            PrismaticLibe.LOGGER.warn(e.getMessage());
            return false;
        }
        return false;
    }
}
