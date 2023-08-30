package useless.prismaticlibe;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import useless.prismaticlibe.helper.SoundHelper;


public class PrismaticLibe implements ModInitializer {
    public static final String MOD_ID = "prismaticlibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        LOGGER.info("PrismaticLibe initialized.");
    }
}
