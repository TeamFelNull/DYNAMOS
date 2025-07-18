package dev.felnull.dynamos.items;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DynamosBlocks {

    private static final List<DeferredBlock<?>> TRIVIAL_BLOCKS = new ArrayList<>();
    private static final Map<String, DeferredBlock<?>> REGISTERED_BLOCKS = new HashMap<>();
    //-------------------------------------------------------------
    // ここに機能なしのブロックを追加
    //-------------------------------------------------------------
    private static final List<DynamosBlockEntry<?, ?>> ENTRIES = List.of(
            DynamosBlockEntry.simple(
                    "test_block",
                    BlockBehaviour.Properties.of().strength(1.0f).ignitedByLava().requiresCorrectToolForDrops().sound(SoundType.ANVIL)
            ),
            DynamosBlockEntry.simple(
                    "test_block2",
                    BlockBehaviour.Properties.of().strength(1.0f).ignitedByLava().requiresCorrectToolForDrops().sound(SoundType.ANVIL)
            )
    );


    public static void init() {
        for (DynamosBlockEntry<?, ?> block : ENTRIES) {
            registerBlockWithItem(block.name, block.properties);
        }
    }
    //-------------------------------------------------------------

    private static DeferredBlock<Block> registerBlockWithItem(String name, BlockBehaviour.Properties properties) {
        DeferredBlock<Block> block = Dynamos.BLOCKS.registerSimpleBlock(name, properties);
        Dynamos.ITEMS.registerSimpleBlockItem(name, block);

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