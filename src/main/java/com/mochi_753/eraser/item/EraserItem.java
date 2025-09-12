package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import com.mochi_753.eraser.util.EraserHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EraserItem extends Item {
    public EraserItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.level().isClientSide() && !target.level().isClientSide()) {
            eraseLivingEntity(target, player);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            if (player.isCrouching()) {
                double eraseRadius = EraserConfig.COMMON.eraseRadius.get();
                List<LivingEntity> targets = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(eraseRadius), p -> p != player);
                targets.forEach((target) -> eraseLivingEntity(target, player));
                player.getItemInHand(hand).hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    protected void eraseLivingEntity(LivingEntity target, Player player) {
        if (target instanceof ServerPlayer serverPlayer) {
            EraserHandler.disconnectPlayer(serverPlayer, player);
        } else {
            EraserHandler.eraseNonPlayerEntity(target, player, true);
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EraserConfig.COMMON.eraserDurability.get();
    }
}
