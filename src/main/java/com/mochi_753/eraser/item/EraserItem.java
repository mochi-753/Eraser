package com.mochi_753.eraser.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
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

    @SuppressWarnings("removal")
    private void removeLivingEntity(LivingEntity target, Player player, Level level) {
        if (target instanceof Player) {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        } else {
            level.playSound(null, target.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.PLAYERS, 1.0F, 1.0F);

            target.remove(Entity.RemovalReason.DISCARDED);

            // 対象にremove()が効かなかった場合
            if (target.isAlive()) {
                player.displayClientMessage(Component.literal("Erased by force"), true);
                if (target.level() instanceof ServerLevel serverLevel) {
                    MinecraftServer server = serverLevel.getServer();
                    ResourceKey<Level> erasedKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("eraser", "erased"));
                    ServerLevel erasedWorld = server.getLevel(erasedKey);
                    if (erasedWorld != null) {
                        // 見かけ上消えてるように見える
                        target.changeDimension(erasedWorld);
                        target.remove(Entity.RemovalReason.CHANGED_DIMENSION);
                    }
                    // サーバー / クライアント同期
                    serverLevel.getServer().getPlayerList().broadcastAll(
                            new ClientboundRemoveEntitiesPacket(target.getId())
                    );
                }
            }
        }
    }
}
