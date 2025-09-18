package com.mochi_753.eraser.compat.hyperdaimc;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

public class EraserHyperlink {
    public EraserHyperlink(IEventBus bus, FMLJavaModLoadingContext context) {
        if (FMLLoader.getLoadingModList().getModFileById("hyperdaimc") != null) {
            EraserHyperlinkItems.register(bus);
        }
    }
}
