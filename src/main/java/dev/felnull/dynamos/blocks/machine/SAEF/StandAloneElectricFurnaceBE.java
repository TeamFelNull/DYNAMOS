package dev.felnull.dynamos.blocks.machine.SAEF;

import dev.felnull.dynamos.blocks.machine.IMachineBlock;
import dev.felnull.dynamos.register.DynamosBlocksEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

import java.util.Objects;

public class StandAloneElectricFurnaceBE extends BlockEntity implements IMachineBlock, MenuProvider {
    private final Container inventory = new SimpleContainer(3); // input, fuel, output
    private final EnergyStorage energy = new EnergyStorage(10000);

    private int burnTime = 0;
    private int cookTime = 0;
    private final int cookTimeTotal = 200; // バニラかまどと同じ初期値
    private float speed = 0.05f; // 1tickあたり最大20個処理（1 / 0.05 = 20）

    public StandAloneElectricFurnaceBE(BlockPos pos, BlockState state) {
        super(DynamosBlocksEnum.SAEF.getBlockEntityType(), pos, state);
    }

    @Override
    public void tick() {
        if (hasEnergy()) {
            processWithEnergy();
        } else if (hasFuel()) {
            processWithFuel();
        } else {
            cookTime = 0;
        }
    }

    private void processWithEnergy() {
        int maxItems = Math.min((int)(1.0f / speed), 64); // スピードに応じて1tickあたり処理可能な数
        for (int i = 0; i < maxItems; i++) {
            if (canSmelt() && energy.extractEnergy(10, true) >= 10) {
                smelt();
                energy.extractEnergy(10, false);
            } else break;
        }
    }

    private void processWithFuel() {
        if (burnTime <= 0 && hasFuel()) {
            burnTime = getFuelBurnTime(inventory.getItem(1)); // 燃料スロット
            inventory.getItem(1).shrink(1);
        }

        if (burnTime > 0) {
            burnTime--;
            cookTime++;
            if (cookTime >= cookTimeTotal) {
                cookTime = 0;
                smelt();
            }
        }
    }

    private boolean hasFuel() {
        return !inventory.getItem(1).isEmpty();
    }

    private boolean hasEnergy() {
        return energy.getEnergyStored() >= 10;
    }

    private boolean canSmelt() {
        // 入力があり、出力が空 or 同じアイテム かつ スタック上限未満
        return true; // 実装略
    }

    private void smelt() {
        // 入力→出力変換（バニラ参照）
    }

    public int getFuelBurnTime(ItemStack stack) {
        FuelValues fuelValues = Objects.requireNonNull(this.level).fuelValues();
        return stack.getBurnTime(RecipeType.SMELTING, fuelValues);
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return energy;
    }

    @Override
    public boolean isActive() {
        return hasEnergy() || burnTime > 0;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.dynamos.electric_furnace");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
      //  return new ElectricFurnaceMenu(syncId, playerInventory, this);
        return null;
    }
}
