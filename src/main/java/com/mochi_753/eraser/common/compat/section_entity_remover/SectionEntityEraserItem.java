package com.mochi_753.eraser.common.compat.section_entity_remover;

import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.item.EraserItemBase;
import com.sectionentityremover.RemoverUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SectionEntityEraserItem extends EraserItemBase {
    public SectionEntityEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void erase(Player player, Entity victim) {
        RemoverUtil.removeEntity(victim);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltip.eraser.section_entity_eraser"));
        super.appendHoverText(stack, level, components, flag);
    }

    public static class Register {
        private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Eraser.MOD_ID);

        public static final RegistryObject<Item> SECTION_ENTITY_ERASER = ITEMS.register("section_entity_eraser",
                () -> new SectionEntityEraserItem(new Properties().rarity(Rarity.EPIC).stacksTo(1)));

        public static void register(IEventBus eventBus) {
            ITEMS.register(eventBus);
        }
    }
}
