package dev.felnull.dynamos.creativetab;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.entry.DynamosBlockEntry;
import dev.felnull.dynamos.register.DynamosBlocks;
import dev.felnull.dynamos.register.DynamosItemEntry;
import dev.felnull.dynamos.register.DynamosItems;
import dev.felnull.dynamos.register.DynamosItemsEnum;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;


@EventBusSubscriber(modid = Dynamos.MODID)
public class DynamosCreativeTabEvents {
    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(DynamosCreativeTabs.MAIN_TAB.getKey())) {
            CreativeModeTab currentTab = event.getTab();
            //-------------------------------------------------------------
            //ここにメインクリエタブに追加したいアイテムを追加していってね
            //-------------------------------------------------------------
            for (DynamosItemEntry<?, ?> entry : DynamosItems.getEntries()) {
                if (entry.creativeTab != null && entry.creativeTab.get().equals(currentTab)) {
                    event.accept(entry.getRegisteredBlock().get().asItem());
                }
            }
            // ブロック登録（DynamosBlockEntryベース）
            for (DynamosBlockEntry<?, ?> entry : DynamosBlocks.getEntries()) {
                if (entry.creativeTab != null && entry.creativeTab.get().equals(currentTab)) {
                    event.accept(entry.getRegisteredBlock().get().asItem());
                }
            }
            //-------------------------------------------------------------
        }
    }
}