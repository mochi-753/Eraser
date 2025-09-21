package com.mochi_753.eraser.util;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.EraserConfig;
import com.mochi_753.eraser.network.ClientboundCrashPacket;
import com.mochi_753.eraser.network.EraserNetwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public class EraserHandler {
    public static void crashClient(ServerPlayer target, Player player) {
        if (target.level().isClientSide() || player.level().isClientSide()) return;

        if (EraserConfig.COMMON.allowCrashClient.get()) {
            Eraser.LOGGER.info("{} was crashed by {}", target.getName().getString(), player.getName().getString());
            EraserNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> target), new ClientboundCrashPacket("All returns to nothing..."));
        } else {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        }
    }

    public static void disconnectPlayer(ServerPlayer target, Player player) {
        if (target.level().isClientSide() || player.level().isClientSide()) return;

        if (EraserConfig.COMMON.allowDisconnectPlayer.get()) {
            Eraser.LOGGER.info("{} was erased by {}", target.getName().getString(), player.getName().getString());
            target.connection.disconnect(Component.translatable("message.eraser.disconnect"));
        } else {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        }
    }

    public static void eraseNonPlayerEntity(LivingEntity target, Player player, boolean showMessage) {
        if (target.level().isClientSide() || player.level().isClientSide()) return;

        target.discard();
        if (target.isAlive() || !target.isDeadOrDying()) {
            forceErase(target, player, showMessage);
        }
    }

    public static void forceErase(LivingEntity target, Player player, boolean showMessage) {
        if (target.level().isClientSide() || player.level().isClientSide()) return;

        player.displayClientMessage(Component.literal("Erased by force."), true);
        if (showMessage) {
            player.displayClientMessage(Component.translatable("message.eraser.re_login"), false);
        }

        if (target.level() instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            ResourceKey<Level> erasedKey = ResourceKey.create(Registries.DIMENSION, GetResourceLocation.getResourceLocation(Eraser.MOD_ID, "erased"));
            ServerLevel erasedLevel = server.getLevel(erasedKey);
            if (erasedLevel != null) {
                target.changeDimension(erasedLevel);
                target.remove(Entity.RemovalReason.CHANGED_DIMENSION);
            }
        }
    }

    public static void respawnPlayer(ServerPlayer target, Player player) {
        if (target.level().isClientSide() || player.level().isClientSide()) return;

        target.connection.player = target.server.getPlayerList().respawn(target, false);
    }

    public static void setHealth(LivingEntity target, Player player) {
        if (target.level().isClientSide() || player.level().isClientSide()) return;

        for (int i = 0; i < EraserConfig.COMMON.setHealthSpamCount.get(); i++) {
            target.setHealth(0F);
        }

        if (target.isAlive() || !target.isDeadOrDying()) {
            target.hurt(target.damageSources().generic(), Float.MAX_VALUE);
            target.die(target.damageSources().generic());
        }
    }

    public static void playSound(LivingEntity target, Level level) {
        if (target.level().isClientSide() || level.isClientSide()) return;

        level.playSound(null, target.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS,
                1.0F, 1.0F);
    }
}
