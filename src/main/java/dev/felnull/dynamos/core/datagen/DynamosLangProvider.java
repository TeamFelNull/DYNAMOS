package dev.felnull.dynamos.core.datagen;

import dev.felnull.dynamos.enums.DynamosIngotEnum;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

public class DynamosLangProvider extends LanguageProvider {
    public DynamosLangProvider(PackOutput output) {
        super(output, "dynamos", "en_us");
    }

    @Override
    protected void addTranslations() {
        // アイテム名
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            Item ingotItem = ingot.entry.registeredItem.get();
            Item nugget = ingot.getNugget().registeredItem.get();
            Block materialBlock = ingot.getBlock().getRegisteredBlock().get();
            String name = WordUtils.capitalizeFully(ingot.itemName.replace("_", " "));
            add(ingotItem, name + " Ingot");
            add(nugget, name + " Nugget");
            add(materialBlock, name + " Block");
        }

        // タブ名など
        add("itemGroup.dynamos", "Dynamos");
    }
}