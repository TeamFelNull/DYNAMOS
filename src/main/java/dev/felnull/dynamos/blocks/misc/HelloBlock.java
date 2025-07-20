package dev.felnull.dynamos.blocks.misc;

import dev.felnull.dynamos.register.DynamosBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HelloBlock extends Block implements EntityBlock {

    public static final BooleanProperty TICKING = BooleanProperty.create("ticking");

    public HelloBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(TICKING, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HelloBlockEntity(pos, state);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult hit) {
        if (!level.isClientSide) {
            player.displayClientMessage(Component.literal("やっほー"), false);

            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof HelloBlockEntity helloBE) {
                // tickCounterをリセット
                helloBE.startDelayedMessage();
            }

            // TICKING = true にして ticker を再アクティブ化
            level.setBlock(pos, state.setValue(HelloBlock.TICKING, true), 3);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (!level.isClientSide && type == DynamosBlocks.getBlockEntityType("hello_block") && state.getValue(TICKING)) {
            return (lvl, pos, st, be) -> ((HelloBlockEntity) be).tick();
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TICKING);
    }
}