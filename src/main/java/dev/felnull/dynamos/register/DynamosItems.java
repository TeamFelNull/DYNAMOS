package dev.felnull.dynamos.register;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamosItems {
    private static final Map<String, DeferredItem<Item>> ITEM_MAP = new HashMap<>();
    private static final List<DeferredItem<?>> FLAT_ITEMS = new ArrayList<>();
    private static final Map<DynamosIngot, DeferredItem<Item>> INGOT_ITEMS = new HashMap<>();
    //-------------------------------------------------------------
    // ここに機能なしのアイテムを追加
    //-------------------------------------------------------------
    public static void init() {
        for (DynamosItemsEnum item : DynamosItemsEnum.values()) {
            item.entry.register();
        }
        for (DynamosIngot ingot : DynamosIngot.values()) {
            ingot.entry.register();
        }
    }
    //-------------------------------------------------------------
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

}
