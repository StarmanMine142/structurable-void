package net.vg.structurablevoid.neoforge;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.vg.structurablevoid.StructurableVoidClient;
import net.vg.structurablevoid.Structurevoidable;
import net.neoforged.fml.common.Mod;
import net.vg.structurablevoid.client.gui.screen.option.MainOptionScreen;

@Mod(Structurablevoid.MOD_ID)
public final class StructurableVoidNeoForge {
    public StructurablevoidNeoForge(IEventBus modEventBus) {
        Structurablevoid.init();

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            modEventBus.addListener(this::clientSetup);
        }

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> new IConfigScreenFactory() {
            @Override
            public Screen createScreen(ModContainer modContainer, Screen arg) {
                return new MainOptionScreen(arg);
            }
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(StructurableVoidClient::initializeClient);
    }
}
