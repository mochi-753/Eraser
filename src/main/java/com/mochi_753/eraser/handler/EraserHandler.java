package com.mochi_753.eraser.handler;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.EraserConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EraserHandler {
    @SuppressWarnings("removal")
    private static ResourceLocation getResourceLocation(String p1, String p2) {
        return new ResourceLocation(p1, p2);
    }

    public static void erasePlayer(ServerPlayer target, Player player) {
        if (EraserConfig.COMMON.allowDisconnectPlayer.get()) {
            Eraser.LOGGER.info("{} was erased by {}", target.getName().getString(), player.getName().getString());
            target.connection.disconnect(Component.translatable("message.eraser.disconnect"));
        } else {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        }
    }

    public static void forceErase(LivingEntity target, Player player) {
        if (target == null || player == null) return;
        player.displayClientMessage(Component.literal("Erased by force."), true);
        player.displayClientMessage(Component.translatable("message.eraser.re_login"), false);
        if (target.level() instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            ResourceKey<Level> erasedKey = ResourceKey.create(Registries.DIMENSION, getResourceLocation("eraser", "erased"));
            ServerLevel erasedLevel = server.getLevel(erasedKey);
            if (erasedLevel != null) {
                target.changeDimension(erasedLevel);
                target.remove(Entity.RemovalReason.CHANGED_DIMENSION);
            }
        }
    }

    public static void eraseLivingEntity(LivingEntity target, Player player) {
        if (target instanceof ServerPlayer serverPlayer) {
            EraserHandler.erasePlayer(serverPlayer, player);
        } else {
            EraserHandler.forceErase(target, player);
        }
    }
}
