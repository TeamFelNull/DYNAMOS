package dev.felnull.dynamos.items;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamosBlocks {

    private static final List<DeferredBlock<?>> TRIVIAL_BLOCKS = new ArrayList<>();
    private static final Map<String, DeferredBlock<?>> REGISTERED_BLOCKS = new HashMap<>();
    //-------------------------------------------------------------
    // ここに機能なしのブロックを追加
    //-------------------------------------------------------------
    public static void init() {
        for (DynamosBlockEnum block : DynamosBlockEnum.values()) {
            block.register();
        }
    }
    //-------------------------------------------------------------

    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, BlockBehaviour.Properties properties) {
        DeferredBlock<T> block = Dynamos.BLOCKS.register(name, () -> (T) new Block(properties));
        Dynamos.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        TRIVIAL_BLOCKS.add(block);
        REGISTERED_BLOCKS.put(name, block);
        return block;
    }

    public static List<DeferredBlock<?>> getTrivialBlocks() {
        return TRIVIAL_BLOCKS;
    }

    public static DeferredBlock<?> getBlock(String name) {
        return REGISTERED_BLOCKS.get(name);
    }
}