package com.mochi_753.eraser.item;

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
    private static final double ERASE_RADIUS = 4.0D;

    public EraserItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isCrouching()) {
            List<LivingEntity> targets = player.level().getEntitiesOfClass(
                    LivingEntity.class,
                    player.getBoundingBox().inflate(ERASE_RADIUS),
                    e -> e != player
            );
            targets.forEach(entity -> eraseLivingEntity(entity, player, level));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        eraseLivingEntity(target, player, player.level());
        return InteractionResult.SUCCESS;
    }

    private void eraseLivingEntity(LivingEntity target, Player player, Level level) {
        if (level.isClientSide()) return;

        playEraseSound(target, level);
        if (target instanceof Player targetPlayer) {
            if (targetPlayer instanceof ServerPlayer serverPlayer) {
                erasePlayer(targetPlayer, serverPlayer);
            }
        } else {
            target.remove(Entity.RemovalReason.DISCARDED);
            if (target.isAlive()) {
                forceErase(target, player);
            }
        }
    }

    private void erasePlayer(Player player, ServerPlayer serverPlayer) {
        player.setHealth(0F);
        serverPlayer.connection.disconnect(Component.translatable("message.eraser.disconnect"));
    }

    @SuppressWarnings("removal")
    private void forceErase(LivingEntity target, Player player) {
        player.displayClientMessage(Component.literal("Erased by force"), true);
        if (target.level() instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            ResourceKey<Level> erasedKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("eraser", "erased"));
            ServerLevel erasedWorld = server.getLevel(erasedKey);
            if (erasedWorld != null) {
                target.changeDimension(erasedWorld);
                target.remove(Entity.RemovalReason.CHANGED_DIMENSION);
            }
            serverLevel.getServer().getPlayerList().broadcastAll(
                    new ClientboundRemoveEntitiesPacket(target.getId())
            );
        }
    }

    private void playEraseSound(LivingEntity target, Level level) {
        level.playSound(null, target.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
