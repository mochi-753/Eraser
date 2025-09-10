package com.mochi_753.eraser;

import com.mochi_753.eraser.compat.tconstruct.EraserTConstruct;
import com.mochi_753.eraser.register.ModItems;
import com.mochi_753.eraser.register.ModNetwork;
import com.mochi_753.eraser.register.ModTabs;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
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
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus bus = context.getModEventBus();
        context.registerConfig(ModConfig.Type.COMMON, EraserConfig.COMMON_SPEC);

        ModItems.register(bus);
        ModNetwork.register();
        ModTabs.register(bus);

        new EraserTConstruct(bus, context);
    }
}
