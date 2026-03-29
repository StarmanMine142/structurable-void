package net.vg.structurevoidable.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.vg.structurevoidable.Constants;
import net.vg.structurevoidable.config.ModConfigs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "getMarkerParticleTarget", at = @At("HEAD"), cancellable = true)
    private void injectStructureVoidParticle(CallbackInfoReturnable<Block> cir) {
        if (ModConfigs.BARRIER_BEHAVIOR) {
            Constants.LOGGER.debug("Injecting into getBlockParticle method");

            assert this.minecraft.gameMode != null;
            if (this.minecraft.gameMode.getPlayerMode() == GameType.CREATIVE) {
                assert this.minecraft.player != null;
                ItemStack itemStack = this.minecraft.player.getMainHandItem();
                Item item = itemStack.getItem();

                if (item instanceof BlockItem) {
                    Block block = ((BlockItem) item).getBlock();

                    if (block == Blocks.STRUCTURE_VOID) {
                        Constants.LOGGER.debug("Player is holding STRUCTURE_VOID in creative mode, displaying Structure Voids");
                        cir.setReturnValue(block);
                    }
                }
            }
        }

    }
}
