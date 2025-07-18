package dev.felnull.dynamos.datagen;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.items.DynamosBlocks;
import dev.felnull.dynamos.items.DynamosItems;
import dev.felnull.dynamos.items.DynamosIngot;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DynamosModelProvider extends ModelProvider {
    public DynamosModelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Dynamos.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // 無地のFlatアイテム
        for (DeferredItem<?> item : DynamosItems.getFlatItems()) {
            itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
        }

        // Block（トリビアル）用モデル
        for (DeferredBlock<?> block : DynamosBlocks.getTrivialBlocks()) {
            blockModels.createTrivialBlock(block.get(), TexturedModel.CUBE);
            // itemModels.generateFlatItem(block.get().asItem(), ModelTemplates.FLAT_ITEM);
        }

        // 色付きIngot対応モデル
        for (DynamosIngot ingot : DynamosIngot.values()) {
            DeferredItem<Item> item = DynamosItems.getIngotItem(ingot);
            generateColoredItemModel(itemModels, item.get(), "ingot_base", ingot.color.getRGB());
        }

    }

    @Override
    public String getName() {
        return "Dynamos Item & Block Models";
    }

    public void generateColoredItemModel(ItemModelGenerators itemModels, Item item, String textureName, int rgbColor) {
        itemModels.generateDyedItem(item,rgbColor );
    }
}
