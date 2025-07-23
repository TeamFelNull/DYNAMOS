package dev.felnull.dynamos.datagen;


import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosBlocks;
import dev.felnull.dynamos.register.DynamosItems;
import dev.felnull.dynamos.register.DynamosIngotEnum;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DynamosModelProvider extends ModelProvider {

    public DynamosModelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Dynamos.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        TintedTextureGenerator.generateAll();
        // 無地のFlatアイテム
        for (DeferredItem<?> item : DynamosItems.getFlatItems()) {
            itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
        }

        // Block（トリビアル）用モデル
        for (DeferredBlock<?> block : DynamosBlocks.getTrivialBlocks()) {
            blockModels.createTrivialBlock(block.get(), TexturedModel.CUBE);
        }

        // 色付きIngot対応モデル
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            generateColoredItemModel(itemModels, ingot.entry.registeredItem.get(), "ingot_base", ingot.color.getRGB());
            generateColoredItemModel(itemModels, ingot.getNugget().registeredItem.get(), "nugget_base", ingot.color.getRGB());
            generateColoredBlockModel(blockModels,ingot.getBlock().registeredBlock.get(),  ingot.itemName + "_block_tinted");
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
    /**
    public void generateColoredBlockModel(BlockModelGenerators gen, Block block, String textureName, int rgbColor) {
        String modid = BuiltInRegistries.BLOCK.getKey(block).getNamespace();

        // テクスチャマッピング（共通）
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(modid, "block/" + textureName));

        // ティント付きモデルを生成（TINTED_CUBE_ALL を使う！）
        ResourceLocation modelLoc = DynamosModelTemplates.TINTED_CUBE_ALL.create(
                block,
                mapping,
                gen.modelOutput
        );

        // BlockState 出力（単一モデル）
        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelLoc))
        );

        // アイテムモデルにも tint を適用（LAYER1 に色つけ）
        gen.itemModelOutput.accept(
                block.asItem(),
                ItemModelUtils.tintedModel(
                        modelLoc,
                        ItemModelUtils.constantTint(-1), // LAYER0: 無色
                        new Dye(rgbColor)                // LAYER1: 指定色
                )
        );
    }
     GUI上でtintが反映されない問題のため未使用
     **/

    public void generateColoredBlockModel(BlockModelGenerators gen, Block block, String textureName) {
        ResourceLocation blockLoc = BuiltInRegistries.BLOCK.getKey(block);
        String modid = blockLoc.getNamespace();
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(modid, "block/" + textureName);
        // ブロックモデル登録（cube_all）
        ResourceLocation modelLoc = new ModelTemplate(
                Optional.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/cube_all")), // 親にバニラのcube_all
                Optional.empty(),                                                 // transformなし
                TextureSlot.ALL                                                   // テクスチャスロット ALL に対応
        ).create(blockLoc, TextureMapping.cube(texture), gen.modelOutput);

        // BlockState 登録（モデルを1つ指定）
        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelLoc))
        );

        // Itemモデル登録（Unbaked として plainModel を使う）
        gen.itemModelOutput.accept(
                block.asItem(),
                ItemModelUtils.plainModel(modelLoc) // block model を親にした普通の item model
        );
    }

}
