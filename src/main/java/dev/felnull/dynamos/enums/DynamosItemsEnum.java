package dev.felnull.dynamos.enums;

import dev.felnull.dynamos.core.creativetab.DynamosCreativeTabs;
import dev.felnull.dynamos.core.entry.DynamosItemEntry;
import dev.felnull.dynamos.item.TestItem;
import net.minecraft.world.item.Item;

public enum DynamosItemsEnum {
    BASIC_GEAR(DynamosItemEntry.simple("basic_gear", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB)),
    ADVANCED_GEAR(DynamosItemEntry.simple("advanced_gear", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB)),
    ELITE_GEAR(DynamosItemEntry.simple("elite_gear", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB)),
    METAL_GEAR(DynamosItemEntry.simple("metal_gear", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB)),
    TEST_ITEM(DynamosItemEntry.customItem("test_item", TestItem::new, new Item.Properties(), DynamosCreativeTabs.MAIN_TAB));

    public final DynamosItemEntry<?> entry;

    DynamosItemsEnum(DynamosItemEntry<?> entry) {
        this.entry = entry;
    }

    public Item getRegisteredItem() {
        return entry.getRegisteredItem().get();
    }

}
