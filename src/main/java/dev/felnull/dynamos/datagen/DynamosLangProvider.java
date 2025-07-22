package dev.felnull.dynamos.datagen;

import dev.felnull.dynamos.register.DynamosIngot;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

public class DynamosLangProvider extends LanguageProvider {
    public DynamosLangProvider(PackOutput output) {
        super(output, "dynamos", "en_us");
    }

    @Override
    protected void addTranslations() {
        // アイテム名
        for (DynamosIngot ingot : DynamosIngot.values()) {
            Item item = ingot.entry.registeredItem.get();
            String name = WordUtils.capitalizeFully(ingot.itemName.replace("_", " "));
            add(item, name + " Ingot");
        }

        // タブ名など
        add("itemGroup.dynamos", "Dynamos");
    }
}