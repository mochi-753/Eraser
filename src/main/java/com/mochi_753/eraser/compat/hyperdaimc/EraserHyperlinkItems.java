package com.mochi_753.eraser.compat.hyperdaimc;

import com.mochi_753.eraser.Eraser;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EraserHyperlinkItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Eraser.MOD_ID);

    public static final RegistryObject<Item> NOVEL_ERASER = ITEMS.register("novel_eraser",
            () -> new NovelEraserItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).durability(1)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
