package com.mochi_753.eraser.common.util;

import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.EraserConfig;
import com.mochi_753.eraser.common.network.ClientboundCrashPacket;
import com.mochi_753.eraser.common.register.ModNetworks;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EraserUtils {
    public static void crashPlayer(@NotNull Player victim) {
        if (victim.level().isClientSide()) return;
        if (victim instanceof ServerPlayer player) {
            if (EraserConfig.COMMON.allowCrashClient.get()) {
                ModNetworks.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ClientboundCrashPacket("^^", "ERASER POWERRRRRRR!!!!!"));
            }
        }
    }

    public static void crashPlayer(@NotNull Player attacker, @NotNull Player victim) {
        if (attacker.level().isClientSide() || victim.level().isClientSide()) return;
        if (victim instanceof ServerPlayer player) {
            if (EraserConfig.COMMON.allowCrashClient.get()) {
                Eraser.LOGGER.info("{} was erased by {}", player.getName().getString(), attacker.getName().getString());
                ModNetworks.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ClientboundCrashPacket("^^", "ERASER POWERRRRRRR!!!"));
            } else {
                attacker.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
            }
        }
    }

    public static void disconnectPlayer(@NotNull Player victim) {
        if (victim.level().isClientSide()) return;
        if (victim instanceof ServerPlayer player) {
            if (EraserConfig.COMMON.allowDisconnectPlayer.get()) {
                player.connection.disconnect(Component.translatable("message.eraser.disconnect"));
            }
        }
    }

    public static void disconnectPlayer(@NotNull Player attacker, @NotNull Player victim) {
        if (attacker.level().isClientSide() || victim.level().isClientSide()) return;
        if (victim instanceof ServerPlayer player) {
            if (EraserConfig.COMMON.allowDisconnectPlayer.get()) {
                Eraser.LOGGER.info("{} was erased by {}", player.getName().getString(), attacker.getName().getString());
                player.connection.disconnect(Component.translatable("message.eraser.disconnect"));
            } else {
                attacker.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
            }
        }
    }

    public static void respawnPlayer(@NotNull Player victim) {
        if (!victim.level().isClientSide() && victim instanceof ServerPlayer player) {
            player.connection.player = Objects.requireNonNull(player.server).getPlayerList().respawn(player, false);
        }
    }

    public static void setHealth(@NotNull Entity victim) {
        if (victim.level().isClientSide()) return;

        if (victim instanceof PartEntity<?> part) {
            setHealth(part.getParent());
            return;
        }

        if (victim instanceof LivingEntity living) {
            for (int i = 0; i < EraserConfig.COMMON.setHealthSpamCount.get(); i++) living.setHealth(0F);
            if (living.isAlive() || !living.isDeadOrDying()) {
                living.hurt(living.damageSources().generic(), Float.MAX_VALUE);
                living.die(living.damageSources().generic());
            }
        }
    }

    public static void eraseNonPlayerEntity(@NotNull Entity victim) {
        if (victim.level().isClientSide()) return;

        if (victim instanceof Player) return;
        if (victim instanceof PartEntity<?> part) {
            eraseNonPlayerEntity(part.getParent());
            return;
        }

        for (Entity.RemovalReason reason : Entity.RemovalReason.values()) {
            victim.remove(reason);
            victim.setRemoved(reason);
        }
        victim.stopRiding();
        victim.onRemovedFromWorld();
    }

    public static void playSound(@NotNull Entity victim, Level level) {
        if (!victim.level().isClientSide() && !level.isClientSide()) {
            level.playSound(null, victim.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
