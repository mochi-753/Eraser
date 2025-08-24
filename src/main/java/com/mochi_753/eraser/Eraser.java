package com.mochi_753.eraser;

import com.mochi_753.eraser.register.ModItems;
import com.mochi_753.eraser.register.ModTabs;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Eraser.MOD_ID)
public class Eraser {
    public static final String MOD_ID = "eraser";
    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public Eraser() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EraserConfig.COMMON_SPEC);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
        ModTabs.register(bus);
    }
}
