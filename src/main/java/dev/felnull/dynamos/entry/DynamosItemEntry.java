package dev.felnull.dynamos.entry;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosIngot;
import dev.felnull.dynamos.register.DynamosItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public class DynamosItemEntry<T extends Item> {

    public enum Category {
        FLAT,
        INGOT,
        NUGGET
    }

    public final String name;
    public final Function<Item.Properties, T> factory;
    public final Item.Properties properties;
    public final @Nullable Supplier<CreativeModeTab> creativeTab;
    public final Category category;

    public DeferredItem<T> registeredItem;

    public DynamosItemEntry(String name, Function<Item.Properties, T> factory, Item.Properties properties, @Nullable Supplier<CreativeModeTab> creativeTab, Category category) {
        this.name = name;
        this.factory = factory;
        this.properties = properties;
        this.creativeTab = creativeTab;
        this.category = category;
    }
    @SuppressWarnings("unchecked")
    public void register() {
        registeredItem = Dynamos.ITEMS.registerItem(name, factory, properties);
        DynamosItems.addItemMap(name, (DeferredItem<Item>) registeredItem);
        // 分類追加
        switch (category) {
            case FLAT -> DynamosItems.addFlatItem(registeredItem);
            case INGOT -> {
                // 名前から逆引き or 登録時に ingot を渡す必要あり
                DynamosIngot matching = Arrays.stream(DynamosIngot.values())
                        .filter(i -> i.entry == this)
                        .findFirst()
                        .orElseThrow();
                DynamosItems.addIngotItem(matching, (DeferredItem<Item>) registeredItem);
            }
        }
    }

    public DeferredItem<T> getRegisteredItem() {
        return registeredItem;
    }

    public static DynamosItemEntry<Item> simple(String name, Item.Properties properties) {
        return new DynamosItemEntry<>(name, Item::new, properties, null, Category.FLAT);
    }

    public static DynamosItemEntry<Item> simple(String name, Item.Properties properties, Supplier<CreativeModeTab> tab) {
        return new DynamosItemEntry<>(name, Item::new, properties, tab, Category.FLAT);
    }

    public static DynamosItemEntry<Item> simpleIngot(String name, Item.Properties properties) {
        return new DynamosItemEntry<>(name, Item::new, properties, null, Category.INGOT);
    }

    public static DynamosItemEntry<Item> simpleIngot(String name, Item.Properties properties, Supplier<CreativeModeTab> tab) {
        return new DynamosItemEntry<>(name, Item::new, properties, tab, Category.INGOT);
    }
    public static DynamosItemEntry<Item> simpleNugget(String name, Item.Properties properties) {
        return new DynamosItemEntry<>(name, Item::new, properties, null, Category.NUGGET);
    }

    public static DynamosItemEntry<Item> simpleNugget(String name, Item.Properties properties, Supplier<CreativeModeTab> tab) {
        return new DynamosItemEntry<>(name, Item::new, properties, tab, Category.NUGGET);
    }
}
