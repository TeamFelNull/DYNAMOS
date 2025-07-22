package dev.felnull.dynamos.datagen;


import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosBlocks;
import dev.felnull.dynamos.register.DynamosItems;
import dev.felnull.dynamos.register.DynamosIngot;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Map;
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
        }

        // 色付きIngot対応モデル
        for (DynamosIngot ingot : DynamosIngot.values()) {
            DeferredItem<Item> item = DynamosItems.getIngotItem(ingot);
            generateColoredItemModel(itemModels, item.get(), "ingot_base", ingot.color.getRGB());
            generateColoredItemModel();
        }

    }

    @Override
    public String getName() {
        return "Dynamos Item & Block Models";
    }


    public void generateColoredItemModel(ItemModelGenerators itemModels, Item item, String textureName, int rgbColor) {
        // テクスチャの場所
        String modId = BuiltInRegistries.ITEM.getKey(item).getNamespace();

        // LAYER0: ベース画像
        ResourceLocation base = ResourceLocation.fromNamespaceAndPath(modId, "item/" + textureName);

        // LAYER1: オーバーレイ画像（例: textureName_overlay.png）
        ResourceLocation overlay = ResourceLocation.fromNamespaceAndPath(modId, "item/" + textureName + "_overlay");

        // モデル作成
        ResourceLocation modelLoc = itemModels.generateLayeredItem(item, base, overlay);

        // カラー指定（デフォルトカラー）
        itemModels.itemModelOutput.accept(item,
                ItemModelUtils.tintedModel(modelLoc, ItemModelUtils.constantTint(-1), // レイヤー0（ベース）は無色
                        new Dye(rgbColor)                // レイヤー1（オーバーレイ）に色をつける
                )
        );
    }
}
