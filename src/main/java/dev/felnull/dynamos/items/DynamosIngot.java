package dev.felnull.dynamos.items;

import java.awt.*;

public enum DynamosIngot {

    IRIDIUM("iridium", new Color(0xF8F8F8));

    public final String itemName;
    public final Color color;

    DynamosIngot(String name, Color color) {
        this.itemName = name;
        this.color = color;
    }
}