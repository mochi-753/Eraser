package com.mochi_753.eraser;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Eraser.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ERASER_TAB = TABS.register("eraser_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.eraser.eraser_tab"))
                    .icon(ModItems.ERASER.get()::getDefaultInstance)
                    .displayItems(((pParam, pOutput) -> {
                        pOutput.accept(ModItems.ERASER.get());
                    }))
                    .build()
    );

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
