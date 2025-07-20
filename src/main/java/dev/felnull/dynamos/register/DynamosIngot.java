package dev.felnull.dynamos.register;

import net.minecraft.world.item.Item;

import java.awt.*;

public enum DynamosIngot {
    IRIDIUM("iridium", new Color(0xF8F8F8));

    public final String itemName;
    public final Color color;
    public final DynamosItemEntry<Item> entry;

    DynamosIngot(String name, Color color) {
        this.itemName = name;
        this.color = color;
        this.entry = DynamosItemEntry.simple(name + "_ingot", new Item.Properties());
    }
}