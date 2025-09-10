package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import com.mochi_753.eraser.packet.EraserCrashPacket;
import com.mochi_753.eraser.register.ModNetwork;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class TestItem extends EraserItem {
    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResultHolder.success(player.getItemInHand(hand));

        if (player instanceof ServerPlayer serverPlayer) {
            if (player.isCrouching()) {
                crashPlayer(serverPlayer, player);
            } else {
                erasePlayer(serverPlayer, player);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private void crashPlayer(ServerPlayer target, Player player) {
        if (EraserConfig.COMMON.allowCrashClient.get()) {
            ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> target), new EraserCrashPacket("Everything has returned to nothing."));
        } else {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        }
    }
}
