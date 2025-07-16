package dev.felnull.dynamos;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;

public class DynamosItems {
    private static final List<DeferredItem<?>> FLAT_ITEMS = new ArrayList<>();
    //-------------------------------------------------------------
    //ここに機能なしのアイテムを追加
    //-------------------------------------------------------------
    /**
     * public static final DeferredItem<Item> BASIC_GEAR = Dynamos.ITEMS.registerSimpleItem(
     *             "basic_gear", new Item.Properties()
     *     );
     */
    public static final DeferredItem<Item> BASIC_GEAR = registerFlatItem("basic_gear", new Item.Properties());
    public static final DeferredItem<Item> BASIC_GEAR2 = registerFlatItem("basic_gear2", new Item.Properties());
    //-------------------------------------------------------------
    public static DeferredItem<Item> registerFlatItem(String name, Item.Properties props) {
        DeferredItem<Item> item = Dynamos.ITEMS.registerSimpleItem(name, props);
        FLAT_ITEMS.add(item);
        return item;
    }

    public static List<DeferredItem<?>> getFlatItems(){
        return FLAT_ITEMS;
    }
}