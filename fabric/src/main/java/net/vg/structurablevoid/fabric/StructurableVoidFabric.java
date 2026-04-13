package net.vg.structurablevoid.fabric;

import net.vg.structurablevoid.StructurableVoid;
import net.fabricmc.api.ModInitializer;

public final class StructurableVoidFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        StructurableVoid.init();
    }
}V