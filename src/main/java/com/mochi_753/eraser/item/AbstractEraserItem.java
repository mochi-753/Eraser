package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import com.mochi_753.eraser.handler.EraserHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public abstract class AbstractEraserItem extends Item {
    public AbstractEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResultHolder.success(player.getItemInHand(hand));

        if (player.isCrouching()) {
            double eraseRadius = EraserConfig.COMMON.eraseRadius.get();
            List<LivingEntity> targets = player.level().getEntitiesOfClass(
                    LivingEntity.class, player.getBoundingBox().inflate(eraseRadius), e -> e != player
            );
            targets.forEach(livingEntity -> EraserHandler.eraseLivingEntity(livingEntity, player));
            player.getItemInHand(hand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide()) return InteractionResult.SUCCESS;
        EraserHandler.eraseLivingEntity(target, player);
        return InteractionResult.SUCCESS;
    }

    protected void eraseLivingEntity(LivingEntity target, Player player) {
        playEraseSound(target, player);
        if (target instanceof ServerPlayer serverPlayer) {
            EraserHandler.erasePlayer(serverPlayer, player);
        } else {
            eraseNonPlayerEntities(target, player);
        }
    }

    protected void playEraseSound(LivingEntity target, Player player) {
        player.level().playSound(null, target.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1F, 1F);
    }

    protected abstract void erasePlayer(ServerPlayer target, Player player);

    protected abstract void eraseNonPlayerEntities(LivingEntity target, Player player);
}
