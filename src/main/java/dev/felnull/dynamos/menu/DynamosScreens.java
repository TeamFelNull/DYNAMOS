package dev.felnull.dynamos.menu;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@net.neoforged.api.distmarker.OnlyIn(Dist.CLIENT)
public class DynamosScreens {
    public static void registerScreens() {
        ClientRegistry.registerMenuScreen(DynamosMenus.SAEF_MENU.get(), SAEFScreen::new);
    }
}