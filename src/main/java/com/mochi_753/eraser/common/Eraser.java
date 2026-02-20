package com.mochi_753.eraser.common;

import com.mochi_753.eraser.common.compat.hyperdaimc.HyperlinkCompat;
import com.mochi_753.eraser.common.compat.hyperdaimc.NovelEraserItem;
import com.mochi_753.eraser.common.compat.nosugar.NoSugarCompat;
import com.mochi_753.eraser.common.compat.nosugar.SugarEraserItem;
import com.mochi_753.eraser.common.compat.section_entity_remover.SectionEntityEraserItem;
import com.mochi_753.eraser.common.compat.section_entity_remover.SectionEntityRemoverCompat;
import com.mochi_753.eraser.common.compat.tconstruct.TinkersConstructCompat;
import com.mochi_753.eraser.common.register.ModItems;
import com.mochi_753.eraser.common.register.ModNetworks;
import com.mochi_753.eraser.common.register.ModTabs;
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

    @SuppressWarnings("removal")
    public Eraser() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus eventBus = context.getModEventBus();
        context.registerConfig(ModConfig.Type.COMMON, EraserConfig.COMMON_SPEC);

        ModNetworks.init();
        ModItems.register(eventBus);
        ModTabs.register(eventBus);
        new NoSugarCompat(eventBus, context);
        new HyperlinkCompat(eventBus, context);
        new SectionEntityRemoverCompat(eventBus, context);
        new TinkersConstructCompat(eventBus, context);

        eventBus.addListener(this::buildCreativeModeTabContents);
    }

    private void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ModTabs.ERASER_TAB.get()) {
            if (FMLLoader.getLoadingModList().getModFileById("nosugar") != null) {
                event.accept(SugarEraserItem.Register.SUGAR_ERASER::get);
            }
            if (FMLLoader.getLoadingModList().getModFileById("hyperdaimc") != null) {
                event.accept(NovelEraserItem.Register.NOVEL_ERASER::get);
            }
            if (FMLLoader.getLoadingModList().getModFileById("section_entity_remover") != null) {
                event.accept(SectionEntityEraserItem.Register.SECTION_ENTITY_ERASER::get);
            }
        }
    }
}
