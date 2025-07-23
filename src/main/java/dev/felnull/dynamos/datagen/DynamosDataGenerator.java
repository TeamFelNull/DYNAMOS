package dev.felnull.dynamos.datagen;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.datagen.recipe.DynamosRecipeDataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.neoforged.fml.common.EventBusSubscriber;

public class DynamosDataGenerator {

    public static void gatherClientData(GatherDataEvent.Client event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();

        gen.addProvider(true, new DynamosLangProvider(output));
        gen.addProvider(true, new DynamosModelProvider(output, event.getLookupProvider()));
        gen.addProvider(true, new DynamosRecipeDataProvider(output));
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();


    }
}