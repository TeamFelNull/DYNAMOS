package dev.felnull.dynamos.blocks.machine.SAEF;

import dev.felnull.dynamos.register.DynamosBlocks;
import dev.felnull.dynamos.register.DynamosBlocksEnum;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;

public class ElectricFurnaceMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;

    public ElectricFurnaceMenu(int id, Inventory playerInventory, StandAloneElectricFurnaceBE be) {
        super(YourMenus.ELECTRIC_FURNACE.get(), id);
        this.access = ContainerLevelAccess.create(be.getLevel(), be.getBlockPos());

        // 電力スロット、材料スロット、出力スロットなどを追加
        this.addSlot(new Slot(be.getItemHandler(), 0, 56, 17)); // Input
        this.addSlot(new Slot(be.getItemHandler(), 1, 56, 53)); // Fuel or power
        this.addSlot(new ResultSlot(playerInventory.player, be.getItemHandler(), 2, 116, 35)); // Output

        // プレイヤーインベントリ
        addPlayerInventory(playerInventory, 8, 84);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, DynamosBlocksEnum.SAEF.getBlock());
    }
}