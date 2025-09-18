package com.mochi_753.eraser.item;

import com.mochi_753.eraser.util.EraserHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ZeroHealthEraserItem extends EraserItem {
    public ZeroHealthEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void eraseLivingEntity(LivingEntity target, Player player) {
        EraserHandler.playSound(target, target.level());
        if (target instanceof ServerPlayer serverPlayer) {
            EraserHandler.disconnectPlayer(serverPlayer, player);
        } else {
            EraserHandler.setHealth(target, player);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag isAdvanced) {
        components.add(Component.translatable("tooltip.eraser.zero_health_eraser"));
        super.appendHoverText(stack, level, components, isAdvanced);
    }
}
