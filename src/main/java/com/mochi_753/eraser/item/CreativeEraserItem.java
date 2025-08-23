package com.mochi_753.eraser.item;

import net.minecraft.world.item.ItemStack;

public class CreativeEraserItem extends EraserItemBase {
    public CreativeEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
