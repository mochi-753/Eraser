package com.mochi_753.eraser.register;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EraserItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Eraser.MOD_ID);

    public static final RegistryObject<Item> ERASER_SHAVING = ITEMS.register("eraser_shaving",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ERASER = ITEMS.register("eraser",
            () -> new EraserItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).durability(1)));
    public static final RegistryObject<Item> CREATIVE_ERASER = ITEMS.register("creative_eraser",
            () -> new EraserItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> ULTRA_ERASER = ITEMS.register("ultra_eraser",
            () -> new UltraEraserItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).durability(1)));
    public static final RegistryObject<Item> SET_HEALTH_ERASER = ITEMS.register("zero_health_eraser",
            () -> new ZeroHealthEraserItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).durability(1)));
    public static final RegistryObject<Item> CRASH_ERASER = ITEMS.register("crash_eraser",
            () -> new CrashEraserItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).durability(1)));
    public static final RegistryObject<Item> RESPAWN_ERASER = ITEMS.register("respawn_eraser",
            () -> new RespawnEraserItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).durability(1)));
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test",
            () -> new TestItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
