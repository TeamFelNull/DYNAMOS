package dev.felnull.dynamos.creativetab;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosBlocks;
import dev.felnull.dynamos.register.DynamosItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;


@EventBusSubscriber(modid = Dynamos.MODID)
public class DynamosCreativeTabEvents {
    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(DynamosCreativeTabs.MAIN_TAB.getKey())) {
            //-------------------------------------------------------------
            //ここにメインクリエタブに追加したいアイテムを追加していってね
            //-------------------------------------------------------------
            for (DeferredItem<?> item : DynamosItems.getFlatItems()) {
                event.accept(item.get().asItem());
            }
            for (DeferredBlock<?> block : DynamosBlocks.getTrivialBlocks()) {
                event.accept(block.get().asItem());
            }
            //-------------------------------------------------------------
        }
    }
}