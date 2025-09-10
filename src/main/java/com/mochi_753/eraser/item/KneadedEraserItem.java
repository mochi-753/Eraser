package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class KneadedEraserItem extends EraserItem {
    public KneadedEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void forceErase(LivingEntity target, Player player) {
        player.displayClientMessage(Component.translatable("message.eraser.disconnect"), true);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EraserConfig.COMMON.kneadedEraserDurability.get();
    }
}
