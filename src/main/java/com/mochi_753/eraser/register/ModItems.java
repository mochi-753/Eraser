package com.mochi_753.eraser.register;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Eraser.MOD_ID);

    public static final RegistryObject<Item> ERASER_SHAVING = ITEMS.register("eraser_shaving",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> KNEADED_ERASER = ITEMS.register("kneaded_eraser",
            () -> new KneadedEraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(1)));
    public static final RegistryObject<Item> ERASER = ITEMS.register("eraser",
            () -> new EraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).durability(1)));
    public static final RegistryObject<Item> ULTRA_ERASER = ITEMS.register("ultra_eraser",
            () -> new UltraEraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).durability(1)));
    public static final RegistryObject<Item> CREATIVE_ERASER = ITEMS.register("creative_eraser",
            () -> new CreativeEraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> TEST = ITEMS.register("test",
            () -> new TestItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
