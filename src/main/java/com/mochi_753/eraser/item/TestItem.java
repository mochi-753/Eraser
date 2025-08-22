package com.mochi_753.eraser.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TestItem extends Item {
    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            player.setHealth(0F);
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.disconnect(Component.translatable("message.eraser.disconnect"));
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
