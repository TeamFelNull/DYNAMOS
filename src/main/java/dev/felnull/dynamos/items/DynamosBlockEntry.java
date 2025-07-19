package dev.felnull.dynamos.items;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class DynamosBlockEntry<T extends Block, E extends BlockEntity> {
    public final String name;
    public final Function<BlockBehaviour.Properties, T> blockFactory;
    public final BlockBehaviour.Properties properties;
    public final @Nullable BlockEntityFactory<E> blockEntityFactory;

    public DeferredHolder<Block, T> registeredBlock;
    public @Nullable BlockEntityType<E> registeredBlockEntityType;

    public DynamosBlockEntry(String name,
                             Function<BlockBehaviour.Properties, T> blockFactory,
                             BlockBehaviour.Properties properties,
                             @Nullable BlockEntityFactory<E> blockEntityFactory) {
        this.name = name;
        this.blockFactory = blockFactory;
        this.properties = properties;
        this.blockEntityFactory = blockEntityFactory;
    }

    public void register() {
        this.registeredBlock = Dynamos.BLOCKS.registerBlock(name, blockFactory, properties);
        Dynamos.ITEMS.registerSimpleBlockItem(name, registeredBlock::get);
        DynamosBlocks.getTrivialBlocks().add((DeferredBlock<?>) registeredBlock);
    }


    public void setBlockEntityType(BlockEntityType<? extends BlockEntity> type) {
        // この書き方なら安全にキャストできる
        @SuppressWarnings("unchecked")
        BlockEntityType<E> casted = (BlockEntityType<E>) type;
        this.registeredBlockEntityType = casted;
    }

    public Optional<BlockEntityType<E>> getRegisteredBlockEntityType() {
        return Optional.ofNullable(registeredBlockEntityType);
    }

    public boolean hasBlockEntity() {
        return blockEntityFactory != null;
    }

    public DeferredHolder<Block, T> getRegisteredBlock() {
        return registeredBlock;
    }

    @FunctionalInterface
    public interface BlockEntityFactory<E extends BlockEntity> {
        E create(BlockPos pos, BlockState state);
    }

    public static DynamosBlockEntry<Block, BlockEntity> simple(String name, BlockBehaviour.Properties props) {
        return new DynamosBlockEntry<>(name, Block::new, props, null);
    }
}
