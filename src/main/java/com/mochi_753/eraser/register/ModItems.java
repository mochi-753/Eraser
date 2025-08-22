package com.mochi_753.eraser.register;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.item.EraserItem;
import com.mochi_753.eraser.item.TestItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Eraser.MOD_ID);

    public static final RegistryObject<Item> ERASER = ITEMS.register("eraser",
            () -> new EraserItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TEST = ITEMS.register("test",
            () -> new TestItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
