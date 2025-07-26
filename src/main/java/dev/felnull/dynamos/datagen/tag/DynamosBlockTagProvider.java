package dev.felnull.dynamos.datagen.tag;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosIngotEnum;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class DynamosBlockTagProvider extends BlockTagsProvider {
    public DynamosBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Dynamos.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            if (ingot.getBlock() != null) {
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
                        .add(ingot.getRegisteredBlock());
                tag(BlockTags.NEEDS_STONE_TOOL)
                        .add(ingot.getRegisteredBlock());
            }
        }
    }

    private static TagKey<Block> tagRL(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
