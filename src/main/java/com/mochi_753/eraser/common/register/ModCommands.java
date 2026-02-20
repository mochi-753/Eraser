package com.mochi_753.eraser.common.register;

import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.util.EraserUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = Eraser.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCommands {
    @SubscribeEvent
    public static void onRegisterCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal(Eraser.MOD_ID)
                .then(Commands.literal("crash")
                        .requires(stack -> stack.hasPermission(3))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(context -> {
                                    Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
                                    for (ServerPlayer player : players) EraserUtils.crashPlayer(player);
                                    return players.size();
                                })
                        )
                )
                .then(Commands.literal("disconnect")
                        .requires(stack -> stack.hasPermission(3))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(context -> {
                                    Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
                                    for (ServerPlayer player : players) EraserUtils.disconnectPlayer(player);
                                    return players.size();
                                })
                        )
                )
                .then(Commands.literal("erase")
                        .requires(stack -> stack.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> {
                                    Collection<? extends Entity> entities = EntityArgument.getEntities(context, "targets");
                                    for (Entity entity : entities) EraserUtils.eraseNonPlayerEntity(entity);
                                    return entities.size();
                                })
                        )
                )
                .then(Commands.literal("kill")
                        .requires(stack -> stack.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> {
                                    Collection<? extends Entity> entities = EntityArgument.getEntities(context, "targets");
                                    for (Entity entity : entities) EraserUtils.setHealth(entity);
                                    return entities.size();
                                })
                        )
                )
                .then(Commands.literal("respawn")
                        .requires(stack -> stack.hasPermission(3))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(context -> {
                                    Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
                                    for (ServerPlayer player : players) EraserUtils.respawnPlayer(player);
                                    return players.size();
                                })
                        )
                )
        );
    }
}
