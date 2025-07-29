package dev.felnull.dynamos.blocks.machine;

import dev.felnull.dynamos.blocks.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

public interface IMachineBlock extends TickableBlockEntity {
    EnergyStorage getEnergyStorage();
    boolean isActive();
}