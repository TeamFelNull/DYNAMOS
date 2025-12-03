package dev.felnull.dynamos.menu.brokenchest;

import dev.felnull.dynamos.block.misc.brokenchest.BrokenChestEntity;
import dev.felnull.dynamos.menu.DynamosMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BrokenChestMenu extends AbstractContainerMenu {

    private final Container container;

    public BrokenChestMenu(int windowId, Inventory playerInv, FriendlyByteBuf buf) {
        this(windowId, playerInv, getContainerFromBuf(playerInv, buf));
    }

    public BrokenChestMenu(int windowId, Inventory playerInv, Container container) {
        super(DynamosMenu.BROKEN_CHEST_MENU.get(), windowId);
        this.container = container;

        // スロット数チェック（足りないとクラッシュさせてわかりやすくする）
        checkContainerSize(container, BrokenChestEntity.INVENTORY_SIZE);

        container.startOpen(playerInv.player);

        // ★ BrokenChest の 3x2 スロットをレイアウト
        int xStart = 62; // 好きな座標に
        int yStart = 17;

        int index = 0;
        for (int row = 0; row < 2; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.addSlot(new Slot(container, index++, xStart + col * 18, yStart + row * 18));
            }
        }

        // ★ プレイヤーインベントリ
        addPlayerInventory(playerInv, 8, 84);
    }

    private void addPlayerInventory(Inventory playerInv, int x, int y) {
        // 3 行
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, x + col * 18, y + row * 18));
            }
        }
        // ホットバー
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, x + col * 18, y + 58));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // shift右クリック移動ロジック
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            int containerSlotCount = BrokenChestEntity.INVENTORY_SIZE;
            // コンテナ側 → プレイヤー
            if (index < containerSlotCount) {
                if (!this.moveItemStackTo(stackInSlot, containerSlotCount, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else { // プレイヤー側 → コンテナ
                if (!this.moveItemStackTo(stackInSlot, 0, containerSlotCount, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    private static Container getContainerFromBuf(Inventory playerInv, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity be = playerInv.player.level().getBlockEntity(pos);
        if (be instanceof BrokenChestEntity broken) {
            return broken;
        }
        return new SimpleContainer(BrokenChestEntity.INVENTORY_SIZE);
    }


    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
}