package dev.felnull.dynamos;


import dev.felnull.dynamos.core.creativetab.DynamosCreativeTabs;
import dev.felnull.dynamos.core.datagen.DynamosDataGenerator;
import dev.felnull.dynamos.core.register.DynamosBlocks;
import dev.felnull.dynamos.core.register.DynamosItems;
import dev.felnull.dynamos.menu.DynamosMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Dynamos.MODID);


    public Dynamos(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        DynamosCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        DynamosItems.init();
        DynamosBlocks.init();
        modEventBus.addListener(DynamosBlocks::initBlockEntityTypes);
        modEventBus.addListener(DynamosDataGenerator::gatherClientData);
        modEventBus.addListener(DynamosDataGenerator::gatherServerData);
        DynamosMenu.register(modEventBus);
    }

}
