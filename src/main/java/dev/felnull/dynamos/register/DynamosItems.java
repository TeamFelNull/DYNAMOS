package dev.felnull.dynamos.register;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.creativetab.DynamosCreativeTabs;
import dev.felnull.dynamos.entry.DynamosBlockEntry;
import dev.felnull.dynamos.entry.DynamosItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamosItems {
    private static final Map<String, DeferredItem<Item>> ITEM_MAP = new HashMap<>();
    private static final List<DeferredItem<?>> FLAT_ITEMS = new ArrayList<>();
    private static final List<DeferredItem<Item>> INGOT_ITEMS = new ArrayList<>();
    private static final List<DeferredItem<Item>> NUGGET_ITEMS = new ArrayList<>();
    private static final List<DynamosItemEntry<?>> ENTRIES = new ArrayList<>();
    //-------------------------------------------------------------
    // ここに機能なしのアイテムを追加
    //-------------------------------------------------------------
    static  {
        for(DynamosItemsEnum enumEntry : DynamosItemsEnum.values()){
            ENTRIES.add(enumEntry.entry);
        }
        for(DynamosIngotEnum enumEntry : DynamosIngotEnum.values()){
            ENTRIES.add(enumEntry.entry);
            autoGenerateIngotFamily(enumEntry);
        }
    }
    //-------------------------------------------------------------
    public static void init() {
        for (DynamosItemEntry<?> itemEntry : ENTRIES) {
            itemEntry.register();
        }
    }

    @Deprecated
    private static DeferredItem<Item> registerFlatItem(String name, Item.Properties props, boolean includeInFlat) {
        DeferredItem<Item> item = Dynamos.ITEMS.registerSimpleItem(name, props);
        ITEM_MAP.put(name, item);
        if (includeInFlat) {
            FLAT_ITEMS.add(item);
        }
        return item;
    }

    public static DeferredItem<Item> getItem(String name) {
        return ITEM_MAP.get(name);
    }

    public static List<DeferredItem<?>> getFlatItems() {
        return new ArrayList<>(FLAT_ITEMS);
    }

    public static List<DeferredItem<Item>> getIngotItem() {
        return new ArrayList<>(INGOT_ITEMS);
    }

    public static List<DeferredItem<Item>> getNuggetItem() {
        return new ArrayList<>(NUGGET_ITEMS);
    }

    public static List<DynamosItemEntry<?>> getEntries() {
        return ENTRIES;
    }

    public static void addItemMap(String key, DeferredItem<Item> deferredItem) {
        ITEM_MAP.put(key, deferredItem);
    }

    public static void addFlatItem(DeferredItem<?> deferredItem) {
        FLAT_ITEMS.add(deferredItem);
    }

    public static void addIngotItem(DeferredItem<Item> deferredItem) {
        INGOT_ITEMS.add(deferredItem);
    }
    public static void addNuggetItem(DeferredItem<Item> deferredItem) {
        NUGGET_ITEMS.add(deferredItem);
    }

    public static void autoGenerateIngotFamily(DynamosIngotEnum ingot) {
        String base = ingot.itemName;

        // Nugget
        DynamosItemEntry<Item> nugget = DynamosItemEntry.simpleNugget(base + "_nugget", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB);
        ENTRIES.add(nugget);
        ingot.setNugget(nugget);

        // Block
        DynamosBlockEntry<Block, ?> block = DynamosBlockEntry.simpleRGB(base + "_block", BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.IRON), DynamosCreativeTabs.MAIN_TAB);
        DynamosBlocks.getEntries().add(block);
        ingot.setBlock(block);
    }

}
