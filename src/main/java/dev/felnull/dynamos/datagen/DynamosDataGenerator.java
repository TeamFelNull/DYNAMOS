package dev.felnull.dynamos.datagen;

import dev.felnull.dynamos.datagen.texture.TintedTextureGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DynamosDataGenerator {

    public static void gatherClientData(GatherDataEvent.Client event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();

        gen.addProvider(true, new DynamosLangProvider(output));
        gen.addProvider(true, new DynamosModelProvider(output, event.getLookupProvider()));
        gen.addProvider(true, new TintedTextureGenerator(output));
        event.createProvider(DynamosRecipeProvider.Runner::new);
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();
        gen.addProvider(true, new DynamosItemTagProvider(output, event.getLookupProvider()));
    }
}