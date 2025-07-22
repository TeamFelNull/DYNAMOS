package dev.felnull.dynamos.listener;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.entry.DynamosBlockEntry;
import dev.felnull.dynamos.register.DynamosIngot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;

@EventBusSubscriber(modid = Dynamos.MODID, value = Dist.CLIENT)
public class DynamosClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // ←ここで呼ぶにゃ
        event.enqueueWork(DynamosClientSetup::setBlockColor);
    }

    public static void setBlockColor() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        for (DynamosIngot ingot : DynamosIngot.values()) {
            DynamosBlockEntry<Block, ?> entry = ingot.getBlock();
            if (entry == null) continue;

            if (entry.blockCategory == DynamosBlockEntry.BlockCategory.MATERIAL_BLOCK) {
                Color color = ingot.color;
                int rgb = color.getRGB() & 0xFFFFFF;

                blockColors.register(
                        (state, world, pos, tintIndex) -> tintIndex == 0 ? rgb : -1,
                        entry.getRegisteredBlock().get()
                );
            }
        }
    }
}