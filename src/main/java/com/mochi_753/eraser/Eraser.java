package com.mochi_753.eraser;

import com.mochi_753.eraser.compat.tconstruct.EraserTConstruct;
import com.mochi_753.eraser.network.EraserNetwork;
import com.mochi_753.eraser.register.EraserItems;
import com.mochi_753.eraser.register.EraserTabs;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Eraser.MOD_ID)
public class Eraser {
    public static final String MOD_ID = "eraser";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Eraser(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        context.registerConfig(ModConfig.Type.COMMON, EraserConfig.COMMON_SPEC);
        MinecraftForge.EVENT_BUS.addListener(this::commonSetup);
        EraserItems.register(bus);
        EraserTabs.register(bus);

        new EraserTConstruct(bus, context);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        EraserNetwork.init();
    }
}
