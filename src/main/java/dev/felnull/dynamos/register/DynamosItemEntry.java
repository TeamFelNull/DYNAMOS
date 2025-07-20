package dev.felnull.dynamos.register;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class DynamosItemEntry<T extends Item> {
    public final String name;
    public final Function<Item.Properties, T> factory;
    public final Item.Properties properties;
    public final @Nullable Supplier<CreativeModeTab> creativeTab;

    public DeferredItem<T> registeredItem;

    public DynamosItemEntry(String name, Function<Item.Properties, T> factory, Item.Properties properties, @Nullable Supplier<CreativeModeTab> creativeTab) {
        this.name = name;
        this.factory = factory;
        this.properties = properties;
        this.creativeTab = creativeTab;
    }

    public void register() {
        registeredItem = Dynamos.ITEMS.registerItem(name, factory, properties);
    }

    public DeferredItem<T> getRegisteredItem() {
        return registeredItem;
    }

    public static DynamosItemEntry<Item> simple(String name, Item.Properties properties) {
        return new DynamosItemEntry<>(name, Item::new, properties, null);
    }

    public static DynamosItemEntry<Item> simple(String name, Item.Properties properties, Supplier<CreativeModeTab> tab) {
        return new DynamosItemEntry<>(name, Item::new, properties, tab);
    }
}
