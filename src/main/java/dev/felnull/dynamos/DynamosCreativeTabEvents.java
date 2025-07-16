package dev.felnull.dynamos;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.bus.api.SubscribeEvent;


@EventBusSubscriber(modid = Dynamos.MODID)
public class DynamosCreativeTabEvents {
    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(DynamosCreativeTabs.MAIN_TAB.getKey())) {
            //-------------------------------------------------------------
            //ここにメインクリエタブに追加したいアイテムを追加していってね
            //-------------------------------------------------------------
            event.accept(DynamosItems.BASIC_GEAR.get());
            event.accept(DynamosBlocks.TEST_BLOCK.get().asItem());
            //-------------------------------------------------------------
        }
    }
}