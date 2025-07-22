package dev.felnull.dynamos.register;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.entry.DynamosBlockEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.*;
import java.util.function.Function;

public class DynamosBlocks {

    private static final List<DeferredBlock<?>> TRIVIAL_BLOCKS = new ArrayList<>();
    private static final Map<String, DeferredHolder<Block, ? extends Block>> REGISTERED_BLOCK = new HashMap<>();
    private static final List<DynamosBlockEntry<?, ?>> ENTRIES = new ArrayList<>();
    //-------------------------------------------------------------
    // ここに追加したい特殊ブロック情報を追加
    //-------------------------------------------------------------
    static  {
        for(DynamosBlocksEnum enumEntry : DynamosBlocksEnum.values()){
            ENTRIES.add(enumEntry.entry);
        }
    }
    //-------------------------------------------------------------

    public static void init() {
        for (DynamosBlockEntry<?, ?> block : ENTRIES) {
            block.register();
            REGISTERED_BLOCK.put(block.name, block.getRegisteredBlock());
        }
    }

    @Deprecated
    private static <T extends Block> DeferredHolder<Block, T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> constructor, BlockBehaviour.Properties properties) {
        DeferredBlock<T> block = Dynamos.BLOCKS.registerBlock(name, constructor, properties);
        Dynamos.ITEMS.registerSimpleBlockItem(name, block);
        TRIVIAL_BLOCKS.add(block);
        return block;
    }

    public static List<DeferredBlock<?>> getTrivialBlocks() {
        return TRIVIAL_BLOCKS;
    }

    public static DeferredHolder<Block, ? extends Block> getBlock(String name) {
        return REGISTERED_BLOCK.get(name);
    }

    //BlockEntity登録はBlock登録後に行う必要があるのでRegisterEventで動かすこと!
    public static void initBlockEntityTypes(RegisterEvent event) {
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> {
            for (DynamosBlockEntry<?, ? extends BlockEntity> block : ENTRIES) {
                if (!block.hasBlockEntity())
                    continue;

                // 必ず registeredBlock が null じゃない状態で呼ばれるように注意
                Block blockInstance = block.getRegisteredBlock().get();

                BlockEntityType<? extends BlockEntity> type = new BlockEntityType<>(
                        Objects.requireNonNull(block.blockEntityFactory)::create,
                        Set.of(blockInstance),
                        false
                );

                helper.register(ResourceLocation.fromNamespaceAndPath(Dynamos.MODID, block.name), type);
                block.setBlockEntityType(type);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> BlockEntityType<T> getBlockEntityType(String name) {
        DynamosBlockEntry<?, ?> entry = ENTRIES.stream()
                .filter(e -> e.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BlockEntityEntry not found: " + name));

        return (BlockEntityType<T>) entry.getRegisteredBlockEntityType()
                .orElseThrow(() -> new IllegalStateException("BlockEntityType not registered: " + name));
    }

    public static List<DynamosBlockEntry<?, ?>> getEntries() {
        return ENTRIES;
    }

}