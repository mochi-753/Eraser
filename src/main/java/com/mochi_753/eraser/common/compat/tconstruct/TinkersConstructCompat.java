package com.mochi_753.eraser.common.compat.tconstruct;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

public class TinkersConstructCompat {
    public TinkersConstructCompat(IEventBus eventBus, FMLJavaModLoadingContext context) {
        if (FMLLoader.getLoadingModList().getModFileById("tconstruct") != null) {
            ModModifiers.register(eventBus);
            if (FMLLoader.getLoadingModList().getModFileById("hyperdaimc") != null) {
                NovelEraseModifier.Register.register(eventBus);
            }
        }
    }
}
