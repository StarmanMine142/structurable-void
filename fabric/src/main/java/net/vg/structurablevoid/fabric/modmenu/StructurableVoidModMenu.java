package net.vg.structurablevoid.fabric.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.vg.structurablevoid.Constants;
import net.vg.structurablevoid.client.gui.screen.option.OptionScreen;

public class StructurableVoidModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        Constants.LOGGER.debug("Providing main configuration screen factory");
        return OptionScreen::new;
    }
}