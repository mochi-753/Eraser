package com.mochi_753.eraser.common.compat.hyperdaimc;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

public class HyperlinkCompat {
    public HyperlinkCompat(IEventBus eventBus, FMLJavaModLoadingContext context) {
        if (FMLLoader.getLoadingModList().getModFileById("hyperdaimc") != null) {
            NovelEraserItem.Register.register(eventBus);
        }
    }
}
