package net.vg.structurablevoid;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.vg.structurablevoid.block.entity.ModBlockEntities;
import net.vg.structurablevoid.render.StructureVoidBlockEntityRenderer;

public class StructurableVoidClient {

    public static void initializeClient() {
        Constants.LOGGER.info("Initializing client-side components for Structurable Void...");

        Constants.LOGGER.info("Registering Structure Void Block Entity Renderer...");
        BlockEntityRendererRegistry.register(
                ModBlockEntities.STRUCTURE_VOID_BLOCK_ENTITY.get(),
                StructureVoidBlockEntityRenderer::new
        );
        Constants.LOGGER.info("Client-side components initialized for Structurable Void");
    }
}