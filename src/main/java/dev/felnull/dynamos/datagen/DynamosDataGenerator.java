package dev.felnull.dynamos.datagen;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Dynamos.MODID)
public class DynamosDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                true, // client-only
                new DynamosModelProvider(
                        event.getGenerator().getPackOutput(),
                        event.getLookupProvider()
                )
        );
    }
}