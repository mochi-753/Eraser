package com.mochi_753.eraser;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
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
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isCrouching()) {
            double radius = 4.0D;
            List<LivingEntity> entities = player.level().getEntitiesOfClass(
                    LivingEntity.class,
                    player.getBoundingBox().inflate(radius),
                    e -> e != player
            );

            for (LivingEntity entity : entities) {
                removeLivingEntity(entity, player, player.level());
            }
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide) return InteractionResult.SUCCESS;
        removeLivingEntity(target, player, player.level());

        return InteractionResult.SUCCESS;
    }

    private void removeLivingEntity(LivingEntity target, Player player, Level level) {
        if (target instanceof Player) {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        } else {
            target.remove(Entity.RemovalReason.DISCARDED);
            level.playSound(null, target.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
