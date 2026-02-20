package com.mochi_753.eraser.common.compat.hyperdaimc;

import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.EraserConfig;
import com.mochi_753.eraser.common.item.EraserItemBase;
import com.sakurafuld.hyperdaimc.content.hyper.novel.NovelHandler;
import com.sakurafuld.hyperdaimc.helper.Writes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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

public class NovelEraserItem extends EraserItemBase {
    public NovelEraserItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void erase(Player player, Entity victim) {
        if (victim.level() instanceof ServerLevel serverLevel) {
            NovelHandler.playSound(serverLevel, victim.position());
            for (int i = 0; i < EraserConfig.COMMON.novelizeSpamCount.get(); i++) {
                NovelHandler.novelize(player, victim, false);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Writes.gameOver(Component.translatable("tooltip.eraser.novel_eraser").getString()));
        super.appendHoverText(stack, level, components, flag);
    }

    public static class Register {
        private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Eraser.MOD_ID);

        public static final RegistryObject<Item> NOVEL_ERASER = ITEMS.register("novel_eraser",
                () -> new NovelEraserItem(new Properties().rarity(Rarity.EPIC).stacksTo(1)));

        public static void register(IEventBus eventBus) {
            ITEMS.register(eventBus);
        }
    }
}
