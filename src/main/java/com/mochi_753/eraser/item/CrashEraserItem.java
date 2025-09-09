package com.mochi_753.eraser.item;

import com.mochi_753.eraser.EraserConfig;
import com.mochi_753.eraser.packet.EraserCrashPacket;
import com.mochi_753.eraser.register.ModNetworks;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class CrashEraserItem extends EraserItemBase {
    public CrashEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void erasePlayer(Player player, Player targetPlayer, ServerPlayer targetServerPlayer) {
        if (EraserConfig.COMMON.allowCrashClient.get()) {
            ModNetworks.CHANNEL.send(PacketDistributor.PLAYER.with(() -> targetServerPlayer), new EraserCrashPacket(":("));
        } else {
            player.displayClientMessage(Component.translatable("message.eraser.cannot_use"), true);
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return EraserConfig.COMMON.eraserDurability.get();
    }
}
