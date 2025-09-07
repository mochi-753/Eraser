package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ZeroHealthEraserItem extends EraserItemBase {
    public ZeroHealthEraserItem(Properties properties) {
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
            for (int i = 0; i < EraserConfig.COMMON.setHealthSpamCount.get(); i++) {
                target.setHealth(0);
            }
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EraserConfig.COMMON.eraserDurability.get();
    }
}
