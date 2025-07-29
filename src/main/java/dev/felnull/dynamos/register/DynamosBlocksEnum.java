package dev.felnull.dynamos.register;

import dev.felnull.dynamos.blocks.FurnaceLikeBlock;
import dev.felnull.dynamos.blocks.FurnaceLikeBlockEntity;
import dev.felnull.dynamos.blocks.machine.SAEF.StandAloneElectricFurnace;
import dev.felnull.dynamos.blocks.machine.SAEF.StandAloneElectricFurnaceBE;
import dev.felnull.dynamos.blocks.misc.HelloBlock;
import dev.felnull.dynamos.blocks.misc.HelloBlockEntity;
import dev.felnull.dynamos.creativetab.DynamosCreativeTabs;
import dev.felnull.dynamos.entry.DynamosBlockEntry;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public enum DynamosBlocksEnum {
    TEST_BLOCK(DynamosBlockEntry.simple("test_block",
            BlockBehaviour.Properties.of(),
            DynamosCreativeTabs.MAIN_TAB
    )),
    CUSTOM_FURNACE(new DynamosBlockEntry<>(
            "custom_furnace",
            FurnaceLikeBlock::new,
            (pos, state) -> new FurnaceLikeBlockEntity(pos, state, RecipeType.SMELTING),
            BlockBehaviour.Properties.of(),
            DynamosCreativeTabs.MAIN_TAB,
            DynamosBlockEntry.BlockCategory.NORMAL
    )),
    HELLO_BLOCK(new DynamosBlockEntry<Block, HelloBlockEntity>(
            "hello_block",
            HelloBlock::new,
            HelloBlockEntity::new,
            BlockBehaviour.Properties.of().strength(1.0f),
            DynamosCreativeTabs.MAIN_TAB,
            DynamosBlockEntry.BlockCategory.NORMAL
    )),
    SAEF(new DynamosBlockEntry<Block, StandAloneElectricFurnaceBE>(
            "sa_electric_furnace",
            StandAloneElectricFurnace::new,
            StandAloneElectricFurnaceBE::new,
            BlockBehaviour.Properties.of().strength(1.0f),
            DynamosCreativeTabs.MAIN_TAB,
            DynamosBlockEntry.BlockCategory.NORMAL
    ));

    public final DynamosBlockEntry<?, ?> entry;

    DynamosBlocksEnum(DynamosBlockEntry<?, ?> entry) {
        this.entry = entry;
    }

    @SuppressWarnings("unchecked")
    public BlockEntityType<FurnaceLikeBlockEntity> getBlockEntityType() {
        return (BlockEntityType<FurnaceLikeBlockEntity>) entry.getRegisteredBlockEntityType()
                .orElseThrow(() -> new IllegalStateException("Not registered: " + entry.name));
    }

    public Block getBlock() {
        return this.entry.registeredBlock.get();
    }

}
