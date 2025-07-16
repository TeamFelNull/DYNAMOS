package dev.felnull.dynamos.datagen;


import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.DynamosBlocks;
import dev.felnull.dynamos.DynamosItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.concurrent.CompletableFuture;


public class DynamosModelProvider extends ModelProvider {

    public DynamosModelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Dynamos.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (DeferredItem<?> item : DynamosItems.getFlatItems()) {
            itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
        }
        for (DeferredBlock<?> block : DynamosBlocks.getTrivialBlocks()) {
            blockModels.createTrivialBlock(block.get(), TexturedModel.CUBE);
            itemModels.generateFlatItem(block.get().asItem(), ModelTemplates.FLAT_ITEM);
        }
    }

    @Override
    public String getName() {
        return "Dynamos Item & Block Models";
    }
}
