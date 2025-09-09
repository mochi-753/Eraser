package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import com.mochi_753.eraser.packet.EraserCrashPacket;
import com.mochi_753.eraser.register.ModNetworks;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class TestItem extends Item {
    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            if (player.isCrouching()) {
                if (player instanceof ServerPlayer targetServerPlayer) {
                    if (EraserConfig.COMMON.allowCrashClient.get()) {
                        ModNetworks.CHANNEL.send(PacketDistributor.PLAYER.with(() -> targetServerPlayer), new EraserCrashPacket(":("));
                    } else {
                        player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
                    }
                }
            } else {
                if (EraserConfig.COMMON.allowErasePlayer.get()) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.disconnect(Component.translatable("message.eraser.disconnect"));
                    }
                } else {
                    player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
