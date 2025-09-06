package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class KneadedEraserItem extends EraserItemBase {
    public KneadedEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isCrouching()) {
            player.getItemInHand(hand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        eraseLivingEntity(target, player, player.level());
        stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void forceErase(LivingEntity target, Player player) {
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EraserConfig.COMMON.kneadedEraserDurability.get();
    }
}
