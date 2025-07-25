package dev.felnull.dynamos.datagen;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosIngotEnum;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class DynamosItemTagProvider extends ItemTagsProvider {
    public DynamosItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, Dynamos.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            String id = ingot.itemName;

            // ingot 登録
            tag(tagRL("ingots/" + id))
                    .add(ingot.entry.registeredItem.get());

            // nugget が存在すれば登録
            if (ingot.getNugget() != null) {
                tag(tagRL("nuggets/" + id))
                        .add(ingot.getNugget().registeredItem.get());
            }

            // block が存在すれば登録（Block → Item 変換に注意）
            if (ingot.getBlock() != null) {
                tag(tagRL("storage_blocks/" + id))
                        .add(ingot.getBlock().registeredBlock.get().asItem());
            }
        }
    }

    private static TagKey<Item> tagRL(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
