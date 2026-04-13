package net.vg.structurablevoid;

import net.vg.structurablevoid.block.entity.ModBlockEntities;
import net.vg.structurablevoid.config.ModConfigs;
import net.vg.structurablevoid.util.ModKeyMaps;

public final class StructurableVoid {
    public static final String MOD_ID = "structurablevoid";

    public static void init() {

        Constants.LOGGER.info("Initialized Mod: {} v{}", Constants.MOD_NAME, Constants.MOD_VERSION);

        Constants.LOGGER.info("Registering configurations...");

        ModConfigs.registerConfigs();
        Constants.LOGGER.info("Registering block entities...");
        ModBlockEntities.register();

        Constants.LOGGER.info("Registering keybindings...");
        ModKeyMaps.register();
    }
}
