package useless.prismaticlibe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import useless.prismaticlibe.debug.DebugMain;


public class PrismaticLibe implements ModInitializer {
    public static final String MOD_ID = "prismaticlibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static int playerArmorRenderOffset = 0;

    public static boolean isDev;
    static {
        String version = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();
        isDev = version.equals("${version}") || version.contains("dev");
    }
    @Override
    public void onInitialize() {
        if (isDev){
            DebugMain.init();
        }
        LOGGER.info("PrismaticLibe initialized.");
    }
}
