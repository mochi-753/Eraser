package com.mochi_753.eraser.compat.tconstruct;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

public class EraserTConstruct {
    public EraserTConstruct(IEventBus bus, FMLJavaModLoadingContext context) {
        if (FMLLoader.getLoadingModList().getModFileById("tconstruct") != null) {
            EraserModifiers.register(bus);
        }
    }
}
