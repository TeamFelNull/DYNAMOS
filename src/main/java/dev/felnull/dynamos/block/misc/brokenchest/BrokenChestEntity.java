package dev.felnull.dynamos.block.misc.brokenchest;

import dev.felnull.dynamos.block.TickableBlockEntity;
import dev.felnull.dynamos.enums.DynamosBlocksEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BrokenChestEntity extends BlockEntity implements Container {
    private static final int INVENTORY_SIZE = 3 * 2;
    private final NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

    public BrokenChestEntity(BlockPos pos, BlockState state) {
        super(DynamosBlocksEnum.BROKEN_CHEST.getBlockEntityType(), pos, state);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty); //😘🤣❤️😍🙌👍😁💕😂😊👌🍆
    }

    @Override
    public @NotNull ItemStack getItem(int i) {
        return items.get(i);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(this.items, slot, amount);
        this.setChanged();
        return stack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int i) {
        ItemStack stack = ContainerHelper.takeItem(this.items, i);
        this.setChanged();
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        stack.limitSize(this.getMaxStackSize(stack));
        this.items.set(slot, stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        // この世の終わりみたいな処理
        // items.replaceAll(ignored -> ItemStack.EMPTY);
        items.clear();
        this.setChanged();
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);

        // CampfireBlockEntityを見ろ

    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.items.clear();
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder p_338210_) {
        super.collectImplicitComponents(p_338210_);
    }

    @Override
    public void removeComponentsFromTag(ValueOutput p_422208_) {
        super.removeComponentsFromTag(p_422208_);
    }
}
