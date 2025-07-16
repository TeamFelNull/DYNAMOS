package dev.felnull.dynamos;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;

public class DynamosBlocks {

    // モデル生成対象のブロック一覧
    private static final List<DeferredBlock<?>> TRIVIAL_BLOCKS = new ArrayList<>();

    //-------------------------------------------------------------
    // ここに機能なしのブロックを追加
    //-------------------------------------------------------------
    public static final DeferredBlock<Block> TEST_BLOCK = registerBlockWithItem(
            "test_block",
            BlockBehaviour.Properties.of().strength(1.0f)
    );

    //-------------------------------------------------------------

    // BlockとItemを同時登録して、モデル生成対象として登録
    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, BlockBehaviour.Properties properties) {
        DeferredBlock<T> block = Dynamos.BLOCKS.register(name, () -> (T) new Block(properties));
        Dynamos.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        TRIVIAL_BLOCKS.add(block); // モデル用リストに追加
        return block;
    }

    // モデルプロバイダ側から参照する用
    public static List<DeferredBlock<?>> getTrivialBlocks() {
        return TRIVIAL_BLOCKS;
    }
}