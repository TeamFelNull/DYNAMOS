package dev.felnull.dynamos.datagen.loottable;

import dev.felnull.dynamos.register.DynamosBlocksEnum;
import dev.felnull.dynamos.register.DynamosIngotEnum;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DynamosBlockLootProvider extends BlockLootSubProvider {
    public DynamosBlockLootProvider(HolderLookup.Provider lookProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookProvider);
    }

    @Override
    protected void generate() {
        for (DynamosBlocksEnum block : DynamosBlocksEnum.values()){
            this.dropSelf(block.getBlock());
        }
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            this.dropSelf(ingot.getRegisteredBlock());
        }
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blockIterable = new ArrayList<>();
        for (DynamosBlocksEnum block : DynamosBlocksEnum.values()){
            blockIterable.add(block.getBlock());
        }
        for (DynamosIngotEnum ingotEnum : DynamosIngotEnum.values()){
            blockIterable.add(ingotEnum.getRegisteredBlock());
        }
        return blockIterable;
    }
}