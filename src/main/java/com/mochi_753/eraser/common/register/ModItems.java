package com.mochi_753.eraser.common.register;

import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.item.CrashEraserItem;
import com.mochi_753.eraser.common.item.DisconnectEraserItem;
import com.mochi_753.eraser.common.item.EraserItem;
import com.mochi_753.eraser.common.item.SetHealthEraserItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Eraser.MOD_ID);

    public static final RegistryObject<Item> ERASER_SHAVING = ITEMS.register("eraser_shaving",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ERASER = ITEMS.register("eraser",
            () -> new EraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DISCONNECT_ERASER = ITEMS.register("disconnect_eraser",
            () -> new DisconnectEraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SET_HEALTH_ERASER = ITEMS.register("set_health_eraser",
            () -> new SetHealthEraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CRASH_ERASER = ITEMS.register("crash_eraser",
            () -> new CrashEraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
