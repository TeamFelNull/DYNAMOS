package dev.felnull.dynamos.menu;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SAEFMenu extends AbstractContainerMenu {
    private final Container container;

    public SAEFMenu(int windowId, Inventory playerInventory) {
        this(windowId, playerInventory, new SimpleContainer(3));
    }

    public SAEFMenu(int windowId, Inventory playerInventory, Container container) {
        super(DynamosMenu.SAEF_MENU.get(), windowId);
        this.container = container;

        // 機械スロット（例：インプット、燃料、出力）
        this.addSlot(new Slot(container, 0, 56, 17)); // input
        this.addSlot(new Slot(container, 1, 56, 53)); // fuel or energy
        this.addSlot(new Slot(container, 2, 116, 35)); // output

        // プレイヤーインベントリ
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // ホットバー
        for (int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}