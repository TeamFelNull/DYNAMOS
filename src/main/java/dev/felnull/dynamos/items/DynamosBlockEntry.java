package dev.felnull.dynamos.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DynamosBlockEntry<T extends Block, E extends BlockEntity> {
    public final String name;
    public final Supplier<T> blockSupplier;
    public final BlockBehaviour.Properties properties;
    public final BlockEntityFactory<E> blockEntityFactory;

    public DynamosBlockEntry(String name, Supplier<T> blockSupplier,
                             BlockBehaviour.Properties properties,
                             @Nullable BlockEntityFactory<E> blockEntityFactory) {
        this.name = name;
        this.blockSupplier = blockSupplier;
        this.properties = properties;
        this.blockEntityFactory = blockEntityFactory;
    }

    public boolean hasBlockEntity() {
        return blockEntityFactory != null;
    }

    @FunctionalInterface
    public interface BlockEntityFactory<E extends BlockEntity> {
        E create(BlockPos pos, BlockState state);
    }

    // ✅ Blockだけを使う簡易メソッド（型安全）
    public static DynamosBlockEntry<Block, BlockEntity> simple(String name, BlockBehaviour.Properties props) {
        return new DynamosBlockEntry<>(
                name,
                () -> new Block(props),
                props,
                null
        );
    }
}
