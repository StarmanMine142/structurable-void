package net.vg.structurevoidable.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.structurevoidable.Constants;

public class StructureVoidBlockEntity extends BlockEntity {

    public StructureVoidBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_VOID_BLOCK_ENTITY.get(), pos, state);
        Constants.LOGGER.debug("Creating StructureVoidBlockEntity at position: {}", pos);
    }
}