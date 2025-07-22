package dev.felnull.dynamos.register;

import dev.felnull.dynamos.creativetab.DynamosCreativeTabs;
import dev.felnull.dynamos.entry.DynamosItemEntry;
import net.minecraft.world.item.Item;

import java.awt.*;

public enum DynamosIngot {
    IRIDIUM("iridium", new Color(0xFFFFFF)),
    TIN("tin", new Color(0xC2C2C2));

    public final String itemName;
    public final Color color;
    public final DynamosItemEntry<Item> entry;

    DynamosIngot(String name, Color color) {
        this.itemName = name;
        this.color = color;
        this.entry = DynamosItemEntry.simpleIngot(name + "_ingot", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB);
    }
}