package com.mochi_753.eraser.item;

import com.mochi_753.eraser.util.EraserHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CrashEraserItem extends EraserItem {
    public CrashEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void eraseLivingEntity(LivingEntity target, Player player) {
        if (target instanceof ServerPlayer serverPlayer) {
            EraserHandler.crashClient(serverPlayer, player);
        } else {
            EraserHandler.eraseNonPlayerEntity(target, player, true);
        }
    }
}
