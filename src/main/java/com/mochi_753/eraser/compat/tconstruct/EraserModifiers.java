package com.mochi_753.eraser.compat.tconstruct;

import com.mochi_753.eraser.Eraser;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class EraserModifiers {
    public static final StaticModifier<NoLevelsModifier> ERASE;
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(Eraser.MOD_ID);

    static {
        ERASE = MODIFIERS.register("erase", EraseModifier::new);
    }

    public static void register(IEventBus bus) {
        MODIFIERS.register(bus);
    }
}
