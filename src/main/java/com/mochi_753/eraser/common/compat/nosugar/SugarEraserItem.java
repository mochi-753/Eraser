package com.mochi_753.eraser.common.compat.nosugar;

import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.item.EraserItemBase;
import com.mochi_753.eraser.common.util.EraserUtils;
import com.test.nosugar.utils.item.Eraser_Utils;
import com.test.nosugar.utils.render.ColorUtils;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SugarEraserItem extends EraserItemBase {
    public SugarEraserItem(Properties properties) {
        super(properties);
    }

    private Component createWaveComponent(String str) {
        var result = Component.empty();
        long time = System.currentTimeMillis() / 50;//小さいほど早くなる

        for (int i = 0; i < str.length(); i++) {
            int color = ColorUtils.waveGrayWhiteColor(time, i, 5.0);
            result.append(Component.literal(String.valueOf(str.charAt(i)))
                    .withStyle(style -> style.withColor(color)));
        }
        return result;
    }

    @Override
    protected void erase(Player player, Entity victim) {
        if (victim instanceof Player victimPlayer) {
            EraserUtils.respawnPlayer(victimPlayer);
        }
        Eraser_Utils.killIfParentFound(victim, player, 16, true);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        String text = Component.translatable("tooltip.eraser.sugar_eraser").getString();
        components.add(createWaveComponent(text));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        String text = Component.translatable("item.eraser.sugar_eraser").getString();
        return createWaveComponent(text);
    }

    public static class Register {
        private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Eraser.MOD_ID);

        public static final RegistryObject<Item> SUGAR_ERASER = ITEMS.register("sugar_eraser",
                () -> new SugarEraserItem(new Properties().rarity(Rarity.EPIC).stacksTo(1)));

        public static void register(IEventBus eventBus) {
            ITEMS.register(eventBus);
        }
    }
}
