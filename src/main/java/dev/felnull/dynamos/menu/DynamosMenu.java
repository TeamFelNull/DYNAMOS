package dev.felnull.dynamos.menu;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DynamosMenu {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Dynamos.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<SAEFMenu>> SAEF_MENU =
            MENUS.register("saef_menu", () -> new MenuType<>(
                    SAEFMenu::new,
                    FeatureFlags.VANILLA_SET // これが必要にゃ！
            ));

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}