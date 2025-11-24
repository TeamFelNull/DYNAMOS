package dev.felnull.dynamos.listener;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.core.entry.DynamosBlockEntry;
import dev.felnull.dynamos.enums.DynamosIngotEnum;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Dynamos.MODID, value = Dist.CLIENT)
public class DynamosClientSetup {

    @SubscribeEvent
    public static void registerClientColors(RegisterColorHandlersEvent.Block event) {
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            DynamosBlockEntry<Block, ?> entry = ingot.getBlock();
            if (entry == null) continue;

            if (entry.blockCategory == DynamosBlockEntry.BlockCategory.MATERIAL_BLOCK) {
                int rgb = ingot.color.getRGB() & 0xFFFFFF;
                event.register(
                        (state, world, pos, tintIndex) -> tintIndex == 0 ? rgb : -1,
                        entry.getRegisteredBlock().get()
                );
            }
        }

        // event.register((blockState, blockAndTintGetter, blockPos, i) -> 0x114514, DynamosBlocksEnum.TEST2_BLOCK.getBlock());

    }
}