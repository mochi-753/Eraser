package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ZeroHealthEraserItem extends EraserItem {
    public ZeroHealthEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void eraseNonPlayerEntities(LivingEntity target, Player player) {
        for (int i = 0; i < EraserConfig.COMMON.setHealthSpamCount.get(); i++) {
            target.setHealth(0);
        }
    }
}
