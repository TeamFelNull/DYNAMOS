package dev.felnull.dynamos;

import dev.felnull.dynamos.items.DynamosBlockEnum;
import dev.felnull.dynamos.items.DynamosBlocks;
import dev.felnull.dynamos.items.DynamosItemEnum;
import dev.felnull.dynamos.items.DynamosItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DynamosCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Dynamos.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            CREATIVE_MODE_TABS.register("main", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.dynamos"))
                            .icon(() -> new ItemStack(DynamosItems.getItem(DynamosItemEnum.BASIC_GEAR.itemName).get()))
                            .displayItems((parameters, output) -> {
                              // output.accept(DynamosItems.getItem(DynamosItemEnum.BASIC_GEAR.itemName).get());
                              // output.accept(DynamosBlocks.getBlock(DynamosBlockEnum.TEST_BLOCK.itemName).get().asItem());
                            })
                            .build()
            );
}