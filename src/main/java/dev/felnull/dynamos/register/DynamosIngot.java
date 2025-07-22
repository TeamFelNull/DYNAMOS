package dev.felnull.dynamos.register;

import dev.felnull.dynamos.creativetab.DynamosCreativeTabs;
import dev.felnull.dynamos.entry.DynamosBlockEntry;
import dev.felnull.dynamos.entry.DynamosItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.awt.*;

public enum DynamosIngot {
    IRIDIUM("iridium", new Color(0xFF0000)),
    TIN("tin", new Color(0xC2C2C2));

    public final String itemName;
    public final Color color;
    public final DynamosItemEntry<Item> entry;
    private DynamosItemEntry<Item> nugget;
    private DynamosBlockEntry<Block, ?> block;

    DynamosIngot(String name, Color color) {
        this.itemName = name;
        this.color = color;
        this.entry = DynamosItemEntry.simpleIngot(name + "_ingot", new Item.Properties(), DynamosCreativeTabs.MAIN_TAB);
    }

    public void setBlock(DynamosBlockEntry<Block,?> block) {
        this.block = block;
    }

    public void setNugget(DynamosItemEntry<Item> nugget) {
        this.nugget = nugget;
    }

    public DynamosBlockEntry<Block,?> getBlock() {
        return block;
    }

    public DynamosItemEntry<Item> getNugget() {
        return nugget;
    }
}