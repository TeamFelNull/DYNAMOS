package dev.felnull.dynamos.items;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public enum DynamosItemEnum {
    BASIC_GEAR("basic_gear", new Item.Properties()),
    BASIC_GEAR2("basic_gear2", new Item.Properties());

    public final String itemName;
    public final Item.Properties properties;

    DynamosItemEnum(String name, Item.Properties properties) {
        this.itemName = name;
        this.properties = properties;
    }

    public String itemName() {
        return itemName;
    }

    public Item.Properties properties() {
        return properties;
    }
}
