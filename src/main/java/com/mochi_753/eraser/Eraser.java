package com.mochi_753.eraser;

import com.mochi_753.eraser.compat.hyperdaimc.EraserHyperlink;
import com.mochi_753.eraser.compat.hyperdaimc.EraserHyperlinkItems;
import com.mochi_753.eraser.compat.tconstruct.EraserTConstruct;
import com.mochi_753.eraser.network.EraserNetwork;
import com.mochi_753.eraser.register.EraserItems;
import com.mochi_753.eraser.register.EraserTabs;
import com.mojang.logging.LogUtils;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

@Mod(Eraser.MOD_ID)
public class Eraser {
    public static final String MOD_ID = "eraser";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Eraser(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        context.registerConfig(ModConfig.Type.COMMON, EraserConfig.COMMON_SPEC);
        EraserNetwork.init();
        EraserItems.register(bus);
        EraserTabs.register(bus);

        new EraserHyperlink(bus, context);
        new EraserTConstruct(bus, context);

        bus.addListener(this::buildCreativeModeTabContents);
    }

    private void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == EraserTabs.ERASER_TAB.get()) {
            if (FMLLoader.getLoadingModList().getModFileById("hyperdaimc") != null) {
                event.accept(EraserHyperlinkItems.NOVEL_ERASER::get);
            }
        }
    }
}
