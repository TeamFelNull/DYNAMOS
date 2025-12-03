package dev.felnull.dynamos.block.misc.helloblock;

import dev.felnull.dynamos.block.TickableBlockEntity;
import dev.felnull.dynamos.enums.DynamosBlocksEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;

public class HelloBlockEntity extends BlockEntity implements TickableBlockEntity {
    private int tickCounter = -1;

    public HelloBlockEntity(BlockPos pos, BlockState state) {
        super(DynamosBlocksEnum.HELLO_BLOCK.getBlockEntityType(), pos, state);
    }

    public void startDelayedMessage() {
        tickCounter = 5; // 5tickカウント開始
    }

    @Override
    public void tick() {
        if (!Objects.requireNonNull(level).isClientSide && tickCounter >= 0) {
            tickCounter--;
            if (tickCounter == 0) {
                List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(getBlockPos()).inflate(4));
                for (Player player : players) {
                    player.displayClientMessage(Component.literal("やっほー☆"), false);
                }

                tickCounter = -1;

                // ここで TICKING を false にして自動停止
                BlockState state = level.getBlockState(worldPosition);
                if (state.getBlock() instanceof HelloBlock) {
                    level.setBlock(worldPosition, state.setValue(HelloBlock.TICKING, false), 3);
                }
            }
        }
    }
}
