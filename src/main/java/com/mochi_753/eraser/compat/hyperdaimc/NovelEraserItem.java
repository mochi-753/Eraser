package com.mochi_753.eraser.compat.hyperdaimc;

import com.mochi_753.eraser.item.EraserItem;
import com.mochi_753.eraser.util.EraserHandler;
import com.sakurafuld.hyperdaimc.content.hyper.novel.NovelHandler;
import com.sakurafuld.hyperdaimc.helper.Writes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class NovelEraserItem extends EraserItem {
    public NovelEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void eraseLivingEntity(LivingEntity target, Player player) {
        if (target.level().isClientSide() || player.level().isClientSide()) return;

        EraserHandler.playSound(target, target.level());
        if (target instanceof ServerPlayer serverPlayer) {
            EraserHandler.disconnectPlayer(serverPlayer, player);
        } else {
            if (target.level() instanceof ServerLevel serverLevel) {
                NovelHandler.novelize(player, target, true);
                NovelHandler.playSound(serverLevel, target.position());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag isAdvanced) {
        components.add(Writes.gameOver(Component.translatable("tooltip.eraser.novel_eraser").getString()));
        super.appendHoverText(stack, level, components, isAdvanced);
    }
}
