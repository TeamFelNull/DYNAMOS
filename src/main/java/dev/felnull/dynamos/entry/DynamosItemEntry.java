package dev.felnull.dynamos.entry;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class DynamosItemEntry<T extends Item> {

    public enum ItemCategory {
        FLAT,
        INGOT,
        NUGGET
    }

    public final String name;
    public final Function<Item.Properties, T> factory;
    public final Item.Properties properties;
    public final @Nullable Supplier<CreativeModeTab> creativeTab;
    public final ItemCategory itemCategory;

    public DeferredItem<T> registeredItem;

    public DynamosItemEntry(String name, Function<Item.Properties, T> factory, Item.Properties properties, @Nullable Supplier<CreativeModeTab> creativeTab, ItemCategory itemCategory) {
        this.name = name;
        this.factory = factory;
        this.properties = properties;
        this.creativeTab = creativeTab;
        this.itemCategory = itemCategory;
    }
    @SuppressWarnings("unchecked")
    public void register() {
        registeredItem = Dynamos.ITEMS.registerItem(name, factory, properties);
        DynamosItems.addItemMap(name, (DeferredItem<Item>) registeredItem);
        // 分類追加
        switch (itemCategory) {
            case FLAT -> DynamosItems.addFlatItem(registeredItem);
            case INGOT -> DynamosItems.addIngotItem((DeferredItem<Item>) registeredItem);
            case NUGGET -> DynamosItems.addNuggetItem((DeferredItem<Item>) registeredItem);
        }
    }

    public DeferredItem<T> getRegisteredItem() {
        return registeredItem;
    }

    public static DynamosItemEntry<Item> simple(String name, Item.Properties properties) {
        return new DynamosItemEntry<>(name, Item::new, properties, null, ItemCategory.FLAT);
    }

    public static DynamosItemEntry<Item> simple(String name, Item.Properties properties, Supplier<CreativeModeTab> tab) {
        return new DynamosItemEntry<>(name, Item::new, properties, tab, ItemCategory.FLAT);
    }

    public static DynamosItemEntry<Item> simpleIngot(String name, Item.Properties properties) {
        return new DynamosItemEntry<>(name, Item::new, properties, null, ItemCategory.INGOT);
    }

    public static DynamosItemEntry<Item> simpleIngot(String name, Item.Properties properties, Supplier<CreativeModeTab> tab) {
        return new DynamosItemEntry<>(name, Item::new, properties, tab, ItemCategory.INGOT);
    }
    public static DynamosItemEntry<Item> simpleNugget(String name, Item.Properties properties) {
        return new DynamosItemEntry<>(name, Item::new, properties, null, ItemCategory.NUGGET);
    }

    public static DynamosItemEntry<Item> simpleNugget(String name, Item.Properties properties, Supplier<CreativeModeTab> tab) {
        return new DynamosItemEntry<>(name, Item::new, properties, tab, ItemCategory.NUGGET);
    }
}
