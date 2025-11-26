package dev.felnull.dynamos.item;

import dev.felnull.dynamos.enums.DynamosItemsEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class TestItem extends Item {
    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos clickedPos = ctx.getClickedPos();

        if (level.getBlockState(clickedPos).is(Blocks.DIRT)) {
            if (!ctx.getLevel().isClientSide) {
                level.explode(ctx.getPlayer(), clickedPos.getX(), clickedPos.getY(), clickedPos.getZ(), 10f, Level.ExplosionInteraction.TNT);
            }
            return InteractionResult.SUCCESS;
        }

        return super.useOn(ctx);
    }
}
