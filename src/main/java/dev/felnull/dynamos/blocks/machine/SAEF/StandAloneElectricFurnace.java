package dev.felnull.dynamos.blocks.machine.SAEF;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandAloneElectricFurnace extends Block implements EntityBlock {

    public StandAloneElectricFurnace(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StandAloneElectricFurnaceBE(pos, state);
    }

    // 任意：右クリック時に燃焼時間をチェックする処理の例
    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult hit){
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StandAloneElectricFurnaceBE furnaceBE) {
                ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);
                int burnTime = furnaceBE.getFuelBurnTime(held);
                player.displayClientMessage(Component.literal("燃焼時間: " + burnTime + " tick"), true);
            }
        }
        return InteractionResult.SUCCESS;
    }
}

