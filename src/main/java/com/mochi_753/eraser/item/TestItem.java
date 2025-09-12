package com.mochi_753.eraser.item;

import com.mochi_753.eraser.util.EraserHandler;
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
            if (player instanceof ServerPlayer serverPlayer) {
                if (player.isCrouching()) {
                    EraserHandler.crashClient(serverPlayer, player);
                } else {
                    EraserHandler.disconnectPlayer(serverPlayer, player);
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
