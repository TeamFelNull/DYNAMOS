package dev.felnull.dynamos.datagen;

import dev.felnull.dynamos.datagen.loottable.DynamosBlockLootProvider;
import dev.felnull.dynamos.datagen.tag.DynamosBlockTagProvider;
import dev.felnull.dynamos.datagen.tag.DynamosItemTagProvider;
import dev.felnull.dynamos.datagen.texture.TintedTextureGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class DynamosDataGenerator {

    public static void gatherClientData(GatherDataEvent.Client event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();

        gen.addProvider(true, new DynamosLangProvider(output));
        gen.addProvider(true, new DynamosModelProvider(output, event.getLookupProvider()));
        gen.addProvider(true, new TintedTextureGenerator(output));
        event.createProvider(DynamosRecipeProvider.Runner::new);
        gen.addProvider(true, new DynamosItemTagProvider(output, event.getLookupProvider()));
        gen.addProvider(true, new DynamosBlockTagProvider(output, event.getLookupProvider()));
        event.createProvider((lootOutput, lookupProvider) -> new LootTableProvider(
                lootOutput,
                Set.of(), // チェック不要な場合空でOK
                List.of(
                        new LootTableProvider.SubProviderEntry(DynamosBlockLootProvider::new, LootContextParamSets.BLOCK)
                ),
                lookupProvider
        ));
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();

    }
}