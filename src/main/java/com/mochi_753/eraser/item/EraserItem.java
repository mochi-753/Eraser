package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
            ItemStack stack = player.getItemInHand(hand);
            double eraseRadius = EraserConfig.COMMON.eraseRadius.get();
            List<LivingEntity> targets = player.level().getEntitiesOfClass(
                    LivingEntity.class,
                    player.getBoundingBox().inflate(eraseRadius),
                    e -> e != player
            );
            targets.forEach(livingEntity -> eraseLivingEntity(livingEntity, player, level));
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        eraseLivingEntity(target, player, player.level());
        return InteractionResult.SUCCESS;
    }

    protected void eraseLivingEntity(LivingEntity target, Player player, Level level) {
        if (level.isClientSide()) return;

        playEraseSound(target, level);
        if (target instanceof Player targetPlayer) {
            if (targetPlayer instanceof ServerPlayer targetServerPlayer) {
                erasePlayer(player, targetPlayer, targetServerPlayer);
            }
        } else {
            target.remove(Entity.RemovalReason.DISCARDED);
            if (target.isAlive()) {
                forceErase(target, player);
            }
        }
    }

    protected void erasePlayer(Player player, Player targetPlayer, ServerPlayer targetServerPlayer) {
        if (EraserConfig.COMMON.allowErasePlayer.get()) {
            targetPlayer.setHealth(0F);
            targetServerPlayer.connection.disconnect(Component.translatable("message.eraser.disconnect"));
        } else {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        }
    }

    @SuppressWarnings("removal")
    protected void forceErase(LivingEntity target, Player player) {
        player.displayClientMessage(Component.literal("Erased by force"), true);
        if (target.level() instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            ResourceKey<Level> erasedKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("eraser", "erased"));
            ServerLevel erasedWorld = server.getLevel(erasedKey);
            if (erasedWorld != null) {
                target.changeDimension(erasedWorld);
            }
            serverLevel.getServer().getPlayerList().broadcastAll(
                    new ClientboundRemoveEntitiesPacket(target.getId())
            );
        }
    }

    protected void playEraseSound(LivingEntity target, Level level) {
        level.playSound(null, target.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EraserConfig.COMMON.eraserDurability.get();
    }
}
