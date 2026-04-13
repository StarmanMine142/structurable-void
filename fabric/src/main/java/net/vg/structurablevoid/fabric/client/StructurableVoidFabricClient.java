package net.vg.structurablevoid.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.vg.structurablevoid.StructurableVoidClient;

public final class StructurableVoidFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        StructurableVoidClient.initializeClient();
    }
}
