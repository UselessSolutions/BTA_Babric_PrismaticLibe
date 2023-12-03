package useless.prismaticlibe;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import useless.prismaticlibe.debug.DebugMain;


public class PrismaticLibe implements ModInitializer {
    public static final String MOD_ID = "prismaticlibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static int playerArmorRenderOffset = 0;


    @Override
    public void onInitialize() {
        DebugMain.init();
        LOGGER.info("PrismaticLibe initialized.");
    }
}
