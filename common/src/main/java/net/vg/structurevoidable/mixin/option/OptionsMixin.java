package net.vg.structurevoidable.mixin.option;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Options.class)
public class OptionsMixin {
    @Mutable
    @Shadow @Final private OptionInstance<Boolean> operatorItemsTab;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void modifyOperatorItemsTab(Minecraft minecraft, File file, CallbackInfo ci) {
        this.operatorItemsTab = OptionInstance.createBoolean("options.operatorItemsTab", true);

    }
}
