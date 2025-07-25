package dev.felnull.dynamos.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.felnull.dynamos.Dynamos;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Dynamos.MODID)
public class DynamosCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        DynamosCommands.registerCommands(
                event.getDispatcher(),
                event.getBuildContext(),
                event.getCommandSelection()
        );
    }
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection environment) {
        dispatcher.register(Commands.literal("dynamos")
                .then(Commands.literal("checktags")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayer();
                            ItemStack heldItem = player.getMainHandItem();

                            if (heldItem.isEmpty()) {
                                ctx.getSource().sendFailure(Component.literal("手に何も持ってないにゃ～"));
                                return 0;
                            }

                            // TagKey<Item> の Stream を取得
                            Stream<TagKey<Item>> tagStream = heldItem.getTags();

                            // ResourceLocation に変換して収集
                            Set<ResourceLocation> matchingTags = tagStream
                                    .map(TagKey::location) // TagKey<Item> → ResourceLocation
                                    .collect(Collectors.toSet());

                            if (matchingTags.isEmpty()) {
                                ctx.getSource().sendSuccess(() -> Component.literal("タグなしにゃ"), false);
                            } else {
                                ctx.getSource().sendSuccess(() -> Component.literal("タグ一覧にゃ:"), false);
                                for (ResourceLocation tag : matchingTags) {
                                    ctx.getSource().sendSuccess(() -> Component.literal("- #" + tag), false);
                                }
                            }

                            return 1;
                        })));
    }
}
