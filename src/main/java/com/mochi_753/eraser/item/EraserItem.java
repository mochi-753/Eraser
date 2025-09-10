package com.mochi_753.eraser.item;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.EraserConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EraserItem extends AbstractEraserItem {
    public EraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void erasePlayer(ServerPlayer target, Player player) {
        if (EraserConfig.COMMON.allowDisconnectPlayer.get()) {
            Eraser.LOGGER.info("{} was erased by {}", target.getName().getString(), player.getName().getString());
            target.connection.disconnect(Component.translatable("message.eraser.disconnect"));
        } else {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        }
    }

    @Override
    protected void eraseNonPlayerEntities(LivingEntity target, Player player) {
        target.discard();
        if (target.isAlive()) {
            forceErase(target, player);
        }
    }

    @Override
    protected void forceErase(LivingEntity target, Player player) {
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

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EraserConfig.COMMON.eraserDurability.get();
    }
}