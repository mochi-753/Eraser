package com.mochi_753.eraser.common.compat.section_entity_remover;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

public class SectionEntityRemoverCompat {
    public SectionEntityRemoverCompat(IEventBus eventBus, FMLJavaModLoadingContext context) {
        if (FMLLoader.getLoadingModList().getModFileById("section_entity_remover") != null) {
            SectionEntityEraserItem.Register.register(eventBus);
        }
    }
}
