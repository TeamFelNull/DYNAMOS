package dev.felnull.dynamos.enums;

import dev.felnull.dynamos.core.creativetab.DynamosCreativeTabs;
import dev.felnull.dynamos.core.entry.DynamosItemEntry;
import net.minecraft.world.item.Item;

public enum DynamosItemsEnum {
    BASIC_GEAR(DynamosItemEntry.simple("basic_gear", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB));

    public final DynamosItemEntry<?> entry;

    DynamosItemsEnum(DynamosItemEntry<?> entry) {
        this.entry = entry;
    }
}
