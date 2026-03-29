package net.vg.structurevoidable.fabric.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.vg.structurevoidable.Constants;
import net.vg.structurevoidable.client.gui.screen.option.MainOptionScreen;

public class StructureVoidableModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        Constants.LOGGER.debug("Providing main configuration screen factory");
        return MainOptionScreen::new;
    }


}