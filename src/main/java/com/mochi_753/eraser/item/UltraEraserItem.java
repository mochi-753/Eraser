package com.mochi_753.eraser.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class UltraEraserItem extends EraserItem {
    public UltraEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void eraseNonPlayerEntities(LivingEntity target, Player player) {
        forceErase(target, player);
    }
}
