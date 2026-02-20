package com.mochi_753.eraser.common.compat.tconstruct;

import com.mochi_753.eraser.common.util.EraserUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class EraseModifier extends NoLevelsModifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("modifier.eraser.erase").withStyle(ChatFormatting.WHITE);
    }

    @Override
    public @NotNull Component getDisplayName(int level) {
        return this.getDisplayName();
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (!context.getLevel().isClientSide() && tool.hasTag(TinkerTags.Items.MELEE_PRIMARY)) {
            Entity victim = context.getTarget();
            if (victim instanceof Player victimPlayer) {
                EraserUtils.respawnPlayer(victimPlayer);
            } else {
                EraserUtils.eraseNonPlayerEntity(victim);
            }
            EraserUtils.playSound(victim, victim.level());
        }
        return MeleeHitModifierHook.super.beforeMeleeHit(tool, modifier, context, damage, baseKnockback, knockback);
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        Entity victim = hit.getEntity();
        if (!attacker.level().isClientSide() && !victim.level().isClientSide()) {
            if (victim instanceof Player victimPlayer) {
                EraserUtils.respawnPlayer(victimPlayer);
            } else {
                EraserUtils.eraseNonPlayerEntity(victim);
            }
            EraserUtils.playSound(victim, victim.level());
        }
        return ProjectileHitModifierHook.super.onProjectileHitEntity(modifiers, persistentData, modifier, projectile, hit, attacker, target);
    }
}
