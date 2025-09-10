package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import com.mochi_753.eraser.packet.EraserCrashPacket;
import com.mochi_753.eraser.register.ModNetwork;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class CrashEraserItem extends EraserItem {
    public CrashEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void erasePlayer(ServerPlayer target, Player player) {
        if (EraserConfig.COMMON.allowCrashClient.get()) {
            ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> target), new EraserCrashPacket("Everything has returned to nothing."));
        } else {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        }
    }
}
