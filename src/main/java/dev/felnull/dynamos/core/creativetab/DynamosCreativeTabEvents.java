package dev.felnull.dynamos.core.creativetab;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.core.entry.DynamosBlockEntry;
import dev.felnull.dynamos.core.register.DynamosBlocks;
import dev.felnull.dynamos.core.entry.DynamosItemEntry;
import dev.felnull.dynamos.core.register.DynamosItems;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;


@EventBusSubscriber(modid = Dynamos.MODID)
public class DynamosCreativeTabEvents {
    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(DynamosCreativeTabs.MAIN_TAB.getKey())) {
            CreativeModeTab currentTab = event.getTab();
            //-------------------------------------------------------------
            //ここにメインクリエタブに追加したいアイテムを追加していってね
            //-------------------------------------------------------------
            for (DynamosItemEntry<?> entry : DynamosItems.getEntries()) {
                if (entry.creativeTab != null && entry.creativeTab.get().equals(currentTab)) {
                    event.accept(entry.getRegisteredItem().get().asItem());
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