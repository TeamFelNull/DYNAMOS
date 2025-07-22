package dev.felnull.dynamos.register;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.creativetab.DynamosCreativeTabs;
import dev.felnull.dynamos.entry.DynamosBlockEntry;
import dev.felnull.dynamos.entry.DynamosItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamosItems {
    private static final Map<String, DeferredItem<Item>> ITEM_MAP = new HashMap<>();
    private static final List<DeferredItem<?>> FLAT_ITEMS = new ArrayList<>();
    private static final Map<DynamosIngot, DeferredItem<Item>> INGOT_ITEMS = new HashMap<>();

    private static final List<DynamosItemEntry<?>> ENTRIES = new ArrayList<>();
    //-------------------------------------------------------------
    // ここに機能なしのアイテムを追加
    //-------------------------------------------------------------
    static  {
        for(DynamosItemsEnum enumEntry : DynamosItemsEnum.values()){
            ENTRIES.add(enumEntry.entry);
        }
        for(DynamosIngot enumEntry : DynamosIngot.values()){
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

    public static DeferredItem<Item> getIngotItem(DynamosIngot ingot) {
        return INGOT_ITEMS.get(ingot);
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

    public static void addIngotItem(DynamosIngot ingotEnum , DeferredItem<Item> deferredItem) {
        INGOT_ITEMS.put(ingotEnum, deferredItem);
    }

    public static void autoGenerateIngotFamily(DynamosIngot ingot) {
        String base = ingot.itemName;

        // Nugget
        DynamosItemEntry<Item> nugget = DynamosItemEntry.simple(base + "_nugget", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB);
        ENTRIES.add(nugget);

        // Block
        DynamosBlockEntry<Block, ?> block = DynamosBlockEntry.simple(base + "_block", BlockBehaviour.Properties.of().strength(3f), DynamosCreativeTabs.MAIN_TAB);
        DynamosBlocks.getEntries().add(block);
    }

}
