package com.mochi_753.eraser;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Eraser.MOD_ID)
public class Eraser {
    public static final String MOD_ID = "eraser";

    @SuppressWarnings("removal")
    public Eraser() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
    }
}
