package com.mochi_753.eraser.common.item;

import com.mochi_753.eraser.common.EraserConfig;
import com.mochi_753.eraser.common.util.EraserUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class EraserItemBase extends Item {
    public EraserItemBase(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.level().isClientSide() && !target.level().isClientSide()) {
            erase(player, target);
            EraserUtils.playSound(target, target.level());
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && !player.level().isClientSide()) {
            if (player.isShiftKeyDown()) {
                double eraseRadius = EraserConfig.COMMON.eraseRadius.get();
                List<Entity> entities = player.level().getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(eraseRadius), e -> e != player);
                entities.forEach(entity -> {
                    erase(player, entity);
                    EraserUtils.playSound(entity, entity.level());
                });
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    protected abstract void erase(Player player, Entity victim);
}
