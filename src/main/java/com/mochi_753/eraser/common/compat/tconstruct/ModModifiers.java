package com.mochi_753.eraser.common.compat.tconstruct;

import com.mochi_753.eraser.common.Eraser;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class ModModifiers {
    public static final StaticModifier<NoLevelsModifier> ERASE;
    public static final StaticModifier<NoLevelsModifier> CRASH_ERASE;
    public static final StaticModifier<NoLevelsModifier> DISCONNECT_ERASE;
    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(Eraser.MOD_ID);

    static {
        ERASE = MODIFIERS.register("erase", EraseModifier::new);
        CRASH_ERASE = MODIFIERS.register("crash_erase", CrashEraseModifier::new);
        DISCONNECT_ERASE = MODIFIERS.register("disconnect_erase", DisconnectEraseModifier::new);
    }

    public static void register(IEventBus eventBus) {
        MODIFIERS.register(eventBus);
    }
}
