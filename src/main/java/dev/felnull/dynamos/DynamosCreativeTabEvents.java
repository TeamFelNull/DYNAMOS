package dev.felnull.dynamos;

import dev.felnull.dynamos.items.DynamosBlocks;
import dev.felnull.dynamos.items.DynamosItems;
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
                System.out.println("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲｲ: " + item.get());
                event.accept(item.get().asItem());
            }
            for (DeferredBlock<?> block : DynamosBlocks.getTrivialBlocks()) {
                event.accept(block.get().asItem());
            }
            //-------------------------------------------------------------
        }
    }
}