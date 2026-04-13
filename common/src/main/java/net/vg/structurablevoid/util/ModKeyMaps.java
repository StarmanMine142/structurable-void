package net.vg.structurablevoid.util;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.vg.structurablevoid.Constants;
import net.vg.structurablevoid.StructurableVoid;
import net.vg.structurablevoid.config.ModConfigs;

import java.util.Arrays;
import java.util.List;

public class ModKeyMaps {

    static KeyMapping.Category STRUCTURABLEVOID_CATEGORY = new KeyMapping.Category(
            Identifier.fromNamespaceAndPath(StructurableVoid.MOD_ID, "structurablevoid")
    );

    public static final KeyMapping CUSTOM_KEYMAPPING = new KeyMapping(
            "key.toggle_outline_visible",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_INSERT,
            STRUCTURABLEVOID_CATEGORY
    );


    public static final KeyMapping CYCLE_OUTLINE_SIZE_KEYMAPPING = new KeyMapping(
            "key.cycle_outline_size",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_I,
            STRUCTURABLEVOID_CATEGORY
    );

    public static final KeyMapping CYCLE_BLOCK_TYPE_KEYMAPPING = new KeyMapping(
            "key.cycle_block_type",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_O,
            STRUCTURABLEVOID_CATEGORY
    );

    private static final List<String> OUTLINE_SIZES = Arrays.asList("none", "small", "medium", "large");
    private static final List<String> BLOCK_TYPES = Arrays.asList("none", "default", "stone", "deepslate", "dirt", "netherrack", "endstone");

    public static void register() {
        KeyMappingRegistry.register(CUSTOM_KEYMAPPING);
        KeyMappingRegistry.register(CYCLE_OUTLINE_SIZE_KEYMAPPING);
        KeyMappingRegistry.register(CYCLE_BLOCK_TYPE_KEYMAPPING);

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (CUSTOM_KEYMAPPING.consumeClick()) {
                ModConfigs.OUTLINE_VISIBLE = !ModConfigs.OUTLINE_VISIBLE;
                ModConfigs.saveConfigs();
            }

            while (CYCLE_OUTLINE_SIZE_KEYMAPPING.consumeClick()) {
                cycleOutlineSize();
                ModConfigs.saveConfigs();
            }

            while (CYCLE_BLOCK_TYPE_KEYMAPPING.consumeClick()) {
                cycleBlockType();
                ModConfigs.saveConfigs();
            }
        });
    }

    private static void cycleOutlineSize() {
        int currentIndex = OUTLINE_SIZES.indexOf(ModConfigs.OUTLINE_SIZE);
        int nextIndex = (currentIndex + 1) % OUTLINE_SIZES.size();
        ModConfigs.OUTLINE_SIZE = OUTLINE_SIZES.get(nextIndex);

        Constants.LOGGER.info("Outline size set to: {}", ModConfigs.OUTLINE_SIZE);
    }

    private static void cycleBlockType() {
        int currentIndex = BLOCK_TYPES.indexOf(ModConfigs.BLOCK_TYPE);
        int nextIndex = (currentIndex + 1) % BLOCK_TYPES.size();

        switch (nextIndex) {
            case 0 -> {
                ModConfigs.OUTLINE_VISIBLE = false;
                ModConfigs.DISPLAY_BLOCK = false;
                ModConfigs.BLOCK_TYPE = "none";
            }
            case 1 -> {
                ModConfigs.OUTLINE_VISIBLE = true;
                ModConfigs.DISPLAY_BLOCK = false;
                ModConfigs.BLOCK_TYPE = "default";
            }
            default -> {
                ModConfigs.OUTLINE_VISIBLE = true;
                ModConfigs.DISPLAY_BLOCK = true;
                ModConfigs.BLOCK_TYPE = BLOCK_TYPES.get(nextIndex);
            }
        }

        Constants.LOGGER.info("Block display set to: {} (Display block: {}, Outline visible: {})",
                ModConfigs.BLOCK_TYPE, ModConfigs.DISPLAY_BLOCK, ModConfigs.OUTLINE_VISIBLE);
    }
}