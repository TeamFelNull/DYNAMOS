package dev.felnull.dynamos.items;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

public enum DynamosBlockEnum {
    TEST_BLOCK("test_block", BlockBehaviour.Properties.of()
            .strength(1.0f)
            .requiresCorrectToolForDrops());

    public final String itemName;
    public final BlockBehaviour.Properties properties;
    private DeferredBlock<Block> block;

    DynamosBlockEnum(String name, BlockBehaviour.Properties properties) {
        this.itemName = name;
        this.properties = properties;
    }

    public void register() {
        this.block = Dynamos.BLOCKS.register(itemName, () -> new Block(properties));
        Dynamos.ITEMS.register(itemName, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public DeferredBlock<Block> getBlock() {
        return block;
    }
}