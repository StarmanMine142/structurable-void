package net.vg.structurevoidable;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.vg.structurevoidable.block.entity.ModBlockEntities;
import net.vg.structurevoidable.render.StructureVoidBlockEntityRenderer;

public class StructureVoidableClient {

    public static void initializeClient() {
        Constants.LOGGER.info("Initializing client-side components for Structure Voidable...");

        Constants.LOGGER.info("Registering Structure Void Block Entity Renderer...");
        BlockEntityRendererRegistry.register(
                ModBlockEntities.STRUCTURE_VOID_BLOCK_ENTITY.get(),
                StructureVoidBlockEntityRenderer::new
        );
        Constants.LOGGER.info("Client-side components initialized for Structure Voidable");
    }
}