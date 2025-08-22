package com.mochi_753.eraser.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class UltraEraserItem extends EraserItem {
    public UltraEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void eraseLivingEntity(LivingEntity target, Player player, Level level) {
        if (level.isClientSide()) return;

        playEraseSound(target, level);
        if (target instanceof Player targetPlayer) {
            if (targetPlayer instanceof ServerPlayer targetServerPlayer) {
                erasePlayer(player, targetPlayer, targetServerPlayer);
            }
        } else {
            forceErase(target, player);
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }
}
