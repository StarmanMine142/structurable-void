package net.vg.structurevoidable;

import net.vg.structurevoidable.block.entity.ModBlockEntities;
import net.vg.structurevoidable.config.ModConfigs;
import net.vg.structurevoidable.util.ModKeyMaps;

public final class Structurevoidable {
    public static final String MOD_ID = "structurevoidable";

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
