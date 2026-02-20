package com.mochi_753.eraser.client.event;

import com.mochi_753.eraser.client.util.RenderUtils;
import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.EraserConfig;
import com.mochi_753.eraser.common.item.EraserItemBase;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Eraser.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        LocalPlayer player = minecraft.player;

        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof EraserItemBase && player.isShiftKeyDown()) {
            AABB aabb = player.getBoundingBox().inflate(EraserConfig.COMMON.eraseRadius.get());

            PoseStack poseStack = event.getPoseStack();
            Vec3 camera = event.getCamera().getPosition();

            RenderUtils.with(poseStack, () -> {
                MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
                poseStack.translate(-camera.x(), -camera.y(), -camera.z());

                LevelRenderer.renderLineBox(poseStack, bufferSource.getBuffer(RenderType.LINES), aabb, 1.0F, 1.0F, 1.0F, 1.0F);
                RenderUtils.drawCubeFromAABB(poseStack.last().pose(), bufferSource.getBuffer(RenderUtils.Type.NO_CULL), aabb, 0x407FFFD4, face -> true);
                bufferSource.endBatch();
            });
        }
    }
}
