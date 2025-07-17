package dev.felnull.dynamos.datagen;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.items.DynamosBlocks;
import dev.felnull.dynamos.items.DynamosItems;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Dynamos.MODID)
public class DynamosDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.getGenerator().addProvider(
                true, // クライアント向けのみ生成
                new DynamosModelProvider(
                        event.getGenerator().getPackOutput(),
                        event.getLookupProvider()
                )
        );
    }
}