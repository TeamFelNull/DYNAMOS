package dev.felnull.dynamos.entry;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class DynamosBlockEntry<T extends Block, E extends BlockEntity> {
    public final String name;                                               //ブロックID
    public final Function<BlockBehaviour.Properties, T> blockFactory;       //T extends Block クラスの実装方法
    public final BlockBehaviour.Properties properties;                      //ブロック情報定義
    public final @Nullable BlockEntityFactory<E> blockEntityFactory;        //BlockEntityインスタンス化のラムダ
    public DeferredHolder<Block, T> registeredBlock;                        //登録済みのDeferredHolder<Block>
    public @Nullable BlockEntityType<E> registeredBlockEntityType;          //登録済みのBlockEntityType
    public final @Nullable Supplier<CreativeModeTab> creativeTab;                   //登録するクリエタブ

    public DynamosBlockEntry(String name,
                             Function<BlockBehaviour.Properties, T> blockFactory,
                             BlockBehaviour.Properties properties,
                             @Nullable BlockEntityFactory<E> blockEntityFactory,
                             @Nullable Supplier<CreativeModeTab> creativeTab) {
        this.name = name;
        this.blockFactory = blockFactory;
        this.properties = properties;
        this.blockEntityFactory = blockEntityFactory;
        this.creativeTab = creativeTab;
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
        return new DynamosBlockEntry<>(name, Block::new, props, null, null);
    }

    // タブあり用（追加）
    public static DynamosBlockEntry<Block, BlockEntity> simple(String name, BlockBehaviour.Properties props, Supplier<CreativeModeTab> tab) {
        return new DynamosBlockEntry<>(name, Block::new, props, null, tab);
    }
}
