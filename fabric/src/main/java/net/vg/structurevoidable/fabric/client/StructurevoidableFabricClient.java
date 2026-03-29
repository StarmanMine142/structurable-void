package net.vg.structurevoidable.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.vg.structurevoidable.StructureVoidableClient;

public final class StructurevoidableFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        StructureVoidableClient.initializeClient();

    }
}
