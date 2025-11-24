package dev.felnull.dynamos.core.datagen.tag;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.enums.DynamosIngotEnum;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DynamosItemTagProvider extends ItemTagsProvider {

    public DynamosItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, Dynamos.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            String id = ingot.itemName;

            tag(tagRL("ingots/" + id))
                    .add(ingot.getRegisteredIngot());
            if (ingot.getNugget() != null) {
                tag(tagRL("nuggets/" + id))
                        .add(ingot.getRegisteredNugget());
            }
            if (ingot.getBlock() != null) {
                tag(tagRL("storage_blocks/" + id))
                        .add(ingot.getRegisteredBlock().asItem());
            }
        }

    }

    private static TagKey<Item> tagRL(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
