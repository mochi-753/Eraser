package com.mochi_753.eraser.common.compat.tconstruct;

import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.EraserConfig;
import com.sakurafuld.hyperdaimc.content.hyper.novel.NovelHandler;
import com.sakurafuld.hyperdaimc.helper.Writes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class NovelEraseModifier extends NoLevelsModifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    public @NotNull Component getDisplayName() {
        return Writes.gameOver(Component.translatable("modifier.eraser.novel_erase").getString());
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
            if (victim.level() instanceof ServerLevel serverLevel) {
                NovelHandler.playSound(serverLevel, victim.position());
                for (int i = 0; i < EraserConfig.COMMON.novelizeSpamCount.get(); i++) {
                    NovelHandler.novelize(context.getAttacker(), victim, false);
                }
            }
        }
        return MeleeHitModifierHook.super.beforeMeleeHit(tool, modifier, context, damage, baseKnockback, knockback);
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        Entity victim = hit.getEntity();
        if (!attacker.level().isClientSide() && !victim.level().isClientSide()) {
            if (victim.level() instanceof ServerLevel serverLevel) {
                NovelHandler.playSound(serverLevel, victim.position());
                for (int i = 0; i < EraserConfig.COMMON.novelizeSpamCount.get(); i++) {
                    NovelHandler.novelize(attacker, victim, false);
                }
            }
        }
        return ProjectileHitModifierHook.super.onProjectileHitEntity(modifiers, persistentData, modifier, projectile, hit, attacker, target);
    }

    public static class Register {
        public static final StaticModifier<NoLevelsModifier> NOVEL_ERASE;
        private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(Eraser.MOD_ID);

        static {
            NOVEL_ERASE = MODIFIERS.register("novel_erase", NovelEraseModifier::new);
        }

        public static void register(IEventBus eventBus) {
            MODIFIERS.register(eventBus);
        }
    }
}
