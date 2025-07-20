package dev.felnull.dynamos.register;

import net.minecraft.world.item.Item;

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
