package com.mochi_753.eraser.register;

import com.mochi_753.eraser.Eraser;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EraserTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Eraser.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ERASER_TAB = TABS.register("eraser_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.eraser.eraser_tab"))
                    .icon(() -> EraserItems.ERASER.get().getDefaultInstance())
                    .displayItems((pParam, pOutput) -> {
                        pOutput.accept(EraserItems.ERASER_SHAVING.get());
                        pOutput.accept(EraserItems.ERASER.get());
                        pOutput.accept(EraserItems.ULTRA_ERASER.get());
                        pOutput.accept(EraserItems.SET_HEALTH_ERASER.get());
                        pOutput.accept(EraserItems.CRASH_ERASER.get());
                        pOutput.accept(EraserItems.RESPAWN_ERASER.get());
                        pOutput.accept(EraserItems.CREATIVE_ERASER.get());
                    })
                    .build()
    );

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
