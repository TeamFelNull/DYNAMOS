package dev.felnull.dynamos.menu;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.menu.brokenchest.BrokenChestScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = Dynamos.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(
        modid = Dynamos.MODID,
        value = Dist.CLIENT
)
public class DynamosScreens {
    public static void registerScreens() {
      //  ClientRegistry.registerMenuScreen(DynamosMenus.SAEF_MENU.get(), SAEFScreen::new);
    }
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(DynamosMenu.BROKEN_CHEST_MENU.get(), BrokenChestScreen::new);
    }
}