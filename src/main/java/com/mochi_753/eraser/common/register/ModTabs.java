package com.mochi_753.eraser.common.register;

import com.mochi_753.eraser.common.Eraser;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Eraser.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ERASER_TAB = TABS.register("eraser_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.eraser"))
                    .icon(() -> ModItems.ERASER.get().getDefaultInstance())
                    .displayItems((pParam, pOutput) -> {
                        pOutput.accept(ModItems.ERASER_SHAVING.get());
                        pOutput.accept(ModItems.ERASER.get());
                        pOutput.accept(ModItems.SET_HEALTH_ERASER.get());
                        pOutput.accept(ModItems.DISCONNECT_ERASER.get());
                        pOutput.accept(ModItems.CRASH_ERASER.get());
                    })
                    .build()
    );

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
