package dev.felnull.dynamos.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class HelloBlockEntity extends BlockEntity implements TickableBlockEntity {
    private int tickCounter = -1;

    public HelloBlockEntity(BlockPos pos, BlockState state) {
        super(DynamosBlockEntityType.HELLO_BLOCK.get(), pos, state); // BlockEntityTypeはあとで登録するにゃ
    }

    public void startDelayedMessage() {
        tickCounter = 5; // 5tickカウント開始
    }

    @Override
    public void tick() {
        if (!level.isClientSide && tickCounter >= 0) {
            tickCounter--;
            if (tickCounter == 0) {
                List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(getBlockPos()).inflate(4));
                for (Player player : players) {
                    player.displayClientMessage(Component.literal("やっほー☆"),true);
                }
                tickCounter = -1; // 1回限り
            }
        }
    }
}
