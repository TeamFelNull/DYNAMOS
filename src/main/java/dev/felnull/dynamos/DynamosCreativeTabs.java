package dev.felnull.dynamos;

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
                            .icon(() -> new ItemStack(DynamosItems.BASIC_GEAR.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(DynamosItems.BASIC_GEAR.get());
                                output.accept(DynamosBlocks.TEST_BLOCK.get().asItem());
                            })
                            .build()
            );
}