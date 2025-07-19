package dev.felnull.dynamos;


import dev.felnull.dynamos.items.DynamosBlocks;
import dev.felnull.dynamos.items.DynamosItems;
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
    }
}
