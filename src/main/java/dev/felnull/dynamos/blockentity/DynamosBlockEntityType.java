package dev.felnull.dynamos.blockentity;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.items.DynamosBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

import static dev.felnull.dynamos.Dynamos.BLOCK_ENTITY_TYPES;
import static dev.felnull.dynamos.Dynamos.MODID;

public class DynamosBlockEntityType {
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FurnaceLikeBlockEntity>> CUSTOM_FURNACE =
            BLOCK_ENTITY_TYPES.register("custom_furnace", () ->
                    new BlockEntityType<>(
                            FurnaceLikeBlockEntity::new,
                            DynamosBlocks.getBlock("custom_furnace").get(), // DeferredBlockの .get()
                            null)
                    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HelloBlockEntity>> HELLO_BLOCK =
            BLOCK_ENTITY_TYPES.register("hello_block", () ->
                    new BlockEntityType<>(HelloBlockEntity::new, Set.of(DynamosBlocks.getBlock("hello_block").get()))
            );
}