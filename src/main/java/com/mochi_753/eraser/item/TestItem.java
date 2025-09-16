package com.mochi_753.eraser.item;

import com.mochi_753.eraser.util.EraserHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestItem extends Item {
    private static final String[] MODES = {"Crash", "Disconnect", "Respawn"};
    private static final String TAG_MODE = "ModeIndex";

    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            int modeIndex = getMode(stack);

            if (player.isCrouching()) {
                modeIndex = (modeIndex + 1) % MODES.length;
                setMode(stack, modeIndex);
                player.displayClientMessage(Component.literal("Mode changed to " + MODES[modeIndex]), false);
            } else {
                switch (modeIndex) {
                    case 0 -> EraserHandler.crashClient(serverPlayer, player);
                    case 1 -> EraserHandler.disconnectPlayer(serverPlayer, player);
                    case 2 -> EraserHandler.respawnPlayer(serverPlayer, player);
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Mode: " + MODES[getMode(stack)]));
    }

    private int getMode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains(TAG_MODE) ? tag.getInt(TAG_MODE) : 0;
    }

    private void setMode(ItemStack stack, int mode) {
        stack.getOrCreateTag().putInt(TAG_MODE, mode);
    }
}
