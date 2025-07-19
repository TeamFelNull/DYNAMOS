package dev.felnull.dynamos.items;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.blockentity.FurnaceLikeBlock;
import dev.felnull.dynamos.blockentity.FurnaceLikeBlockEntity;
import dev.felnull.dynamos.blockentity.HelloBlock;
import dev.felnull.dynamos.blockentity.HelloBlockEntity;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class DynamosBlocks {

    private static final List<DeferredBlock<?>> TRIVIAL_BLOCKS = new ArrayList<>();
    private static final Map<String, DeferredBlock<?>> REGISTERED_BLOCKS = new HashMap<>();
    //-------------------------------------------------------------
    // ここに機能なしのブロックを追加
    //-------------------------------------------------------------
    public static final DeferredBlock<HelloBlock> HELLO_BLOCK =
            registerBlockWithItem("hello_block", HelloBlock::new, BlockBehaviour.Properties.of().strength(1.0f));

    private static final List<DynamosBlockEntry<?, ?>> ENTRIES = List.of(
            DynamosBlockEntry.simple(
                    "test_block",
                    BlockBehaviour.Properties.of().strength(1.0f).ignitedByLava().requiresCorrectToolForDrops().sound(SoundType.ANVIL)
            ),
            DynamosBlockEntry.simple(
                    "test_block2",
                    BlockBehaviour.Properties.of().strength(1.0f).ignitedByLava().requiresCorrectToolForDrops().sound(SoundType.ANVIL)
            ),
            new DynamosBlockEntry<Block, BlockEntity>(
                    "custom_furnace",
                    () -> new FurnaceLikeBlock(BlockBehaviour.Properties.of().strength(1.0f)),
                    BlockBehaviour.Properties.of().strength(1.0f),
                    FurnaceLikeBlockEntity::new
            )/**,
            new DynamosBlockEntry<Block, BlockEntity>(
                    "hello_block",
                    () -> new HelloBlock(BlockBehaviour.Properties.of().strength(1.0f)),
                    BlockBehaviour.Properties.of().strength(1.0f),
                    HelloBlockEntity::new
            )
             **/
    );


    public static void init() {
        for (DynamosBlockEntry<?, ?> block : ENTRIES) {
            registerBlockWithItem(block.name, HelloBlock::new, block.properties);
        }
    }
    //-------------------------------------------------------------

    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> constructor, BlockBehaviour.Properties properties) {
        DeferredBlock<T> block = Dynamos.BLOCKS.registerBlock(name, constructor, properties);
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