package dev.felnull.dynamos;

import dev.felnull.dynamos.items.DynamosBlocks;
import dev.felnull.dynamos.items.DynamosItems;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.*;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Dynamos.MODID)
public class Dynamos {
    public static final String MODID = "dynamos";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public Dynamos(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        modEventBus.addListener(Dynamos::onRegister);
        DynamosCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        DynamosItems.init();
        DynamosBlocks.init();
    }

    private static void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.ITEM)) {
            //DynamosItems.init(); // DeferredItem<Item> の登録
        }

        if (event.getRegistryKey().equals(Registries.BLOCK)) {
            //DynamosBlocks.init(); // DeferredBlock<Block> の登録（必要なら）
        }
    }
}
