package com.mochi_753.eraser.item;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.EraserConfig;
import com.mochi_753.eraser.handler.EraserHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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
            EraserHandler.forceErase(target, player);
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EraserConfig.COMMON.eraserDurability.get();
    }
}