package dev.felnull.dynamos.block.machine;

import dev.felnull.dynamos.block.TickableBlockEntity;
import net.neoforged.neoforge.energy.EnergyStorage;

public interface IMachineBlock extends TickableBlockEntity {
    EnergyStorage getEnergyStorage();
    boolean isActive();
}