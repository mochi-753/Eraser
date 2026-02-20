package com.mochi_753.eraser.common.item;

import com.mochi_753.eraser.common.util.EraserUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrashEraserItem extends EraserItemBase {
    public CrashEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void erase(Player player, Entity victim) {
        if (victim instanceof Player victimPlayer) {
            EraserUtils.respawnPlayer(victimPlayer);
            EraserUtils.crashPlayer(player, victimPlayer);
        } else {
            EraserUtils.eraseNonPlayerEntity(victim);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltip.eraser.crash_eraser"));
        super.appendHoverText(stack, level, components, flag);
    }
}
