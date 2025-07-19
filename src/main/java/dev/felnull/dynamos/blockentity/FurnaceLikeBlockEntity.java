package dev.felnull.dynamos.blockentity;

import com.mojang.serialization.MapCodec;
import dev.felnull.dynamos.items.DynamosBlockEntry;
import dev.felnull.dynamos.items.DynamosBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;


public class FurnaceLikeBlockEntity extends AbstractFurnaceBlockEntity {
    public FurnaceLikeBlockEntity(BlockPos pos, BlockState state) {
        super(getBlockEntityType(), pos, state, RecipeType.SMELTING);
    }


    @SuppressWarnings("unchecked")
    private static BlockEntityType<FurnaceLikeBlockEntity> getBlockEntityType() {
        return (BlockEntityType<FurnaceLikeBlockEntity>)
                DynamosBlocks.getEntries().stream()
                        .filter(e -> e.name.equals("custom_furnace"))
                        .findFirst()
                        .flatMap(DynamosBlockEntry::getBlockEntityType)
                        .orElseThrow(() -> new IllegalStateException("BlockEntityType not registered for custom_furnace"));
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new FurnaceMenu(id, inv, this, this.dataAccess);
    }
}