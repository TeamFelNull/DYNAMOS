package dev.felnull.dynamos.core.datagen.tag;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.enums.DynamosIngotEnum;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DynamosBlockTagProvider extends BlockTagsProvider {
    public DynamosBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Dynamos.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            if (ingot.getBlock() != null) {
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
                        .add(ingot.getRegisteredBlock());
                tag(BlockTags.NEEDS_STONE_TOOL)
                        .add(ingot.getRegisteredBlock());
            }
        }
    }
}
