package com.mochi_753.eraser.common.compat.nosugar;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

public class NoSugarCompat {
    public NoSugarCompat(IEventBus eventBus, FMLJavaModLoadingContext context) {
        if (FMLLoader.getLoadingModList().getModFileById("nosugar") != null) {
            SugarEraserItem.Register.register(eventBus);
        }
    }
}
