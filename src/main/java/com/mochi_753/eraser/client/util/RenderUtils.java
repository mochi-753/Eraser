package com.mochi_753.eraser.client.util;

import com.mochi_753.eraser.common.Eraser;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class RenderUtils {
    private RenderUtils() {
    }

    public static void with(PoseStack poseStack, Runnable runnable) {
        poseStack.pushPose();
        runnable.run();
        poseStack.popPose();
    }

    public static void drawCube(Matrix4f matrix, VertexConsumer buffer, float startX, float startY, float startZ, float endX, float endY, float endZ, int color, Predicate<Direction> directionPredicate) {
        float alpha = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        if (directionPredicate.test(Direction.DOWN)) {
            buffer.vertex(matrix, startX, startY, startZ).color(r, g, b, alpha).normal(0, -1, 0).endVertex();
            buffer.vertex(matrix, endX, startY, startZ).color(r, g, b, alpha).normal(0, -1, 0).endVertex();
            buffer.vertex(matrix, endX, startY, endZ).color(r, g, b, alpha).normal(0, -1, 0).endVertex();
            buffer.vertex(matrix, startX, startY, endZ).color(r, g, b, alpha).normal(0, -1, 0).endVertex();
        }

        if (directionPredicate.test(Direction.UP)) {
            buffer.vertex(matrix, startX, endY, startZ).color(r, g, b, alpha).normal(0, 1, 0).endVertex();
            buffer.vertex(matrix, startX, endY, endZ).color(r, g, b, alpha).normal(0, 1, 0).endVertex();
            buffer.vertex(matrix, endX, endY, endZ).color(r, g, b, alpha).normal(0, 1, 0).endVertex();
            buffer.vertex(matrix, endX, endY, startZ).color(r, g, b, alpha).normal(0, 1, 0).endVertex();
        }

        if (directionPredicate.test(Direction.NORTH)) {
            buffer.vertex(matrix, startX, startY, startZ).color(r, g, b, alpha).normal(0, 0, -1).endVertex();
            buffer.vertex(matrix, startX, endY, startZ).color(r, g, b, alpha).normal(0, 0, -1).endVertex();
            buffer.vertex(matrix, endX, endY, startZ).color(r, g, b, alpha).normal(0, 0, -1).endVertex();
            buffer.vertex(matrix, endX, startY, startZ).color(r, g, b, alpha).normal(0, 0, -1).endVertex();
        }

        if (directionPredicate.test(Direction.SOUTH)) {
            buffer.vertex(matrix, startX, startY, endZ).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, endX, startY, endZ).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, endX, endY, endZ).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, startX, endY, endZ).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
        }

        if (directionPredicate.test(Direction.WEST)) {
            buffer.vertex(matrix, startX, startY, startZ).color(r, g, b, alpha).normal(-1, 0, 0).endVertex();
            buffer.vertex(matrix, startX, startY, endZ).color(r, g, b, alpha).normal(-1, 0, 0).endVertex();
            buffer.vertex(matrix, startX, endY, endZ).color(r, g, b, alpha).normal(-1, 0, 0).endVertex();
            buffer.vertex(matrix, startX, endY, startZ).color(r, g, b, alpha).normal(-1, 0, 0).endVertex();
        }

        if (directionPredicate.test(Direction.EAST)) {
            buffer.vertex(matrix, endX, startY, startZ).color(r, g, b, alpha).normal(1, 0, 0).endVertex();
            buffer.vertex(matrix, endX, endY, startZ).color(r, g, b, alpha).normal(1, 0, 0).endVertex();
            buffer.vertex(matrix, endX, endY, endZ).color(r, g, b, alpha).normal(1, 0, 0).endVertex();
            buffer.vertex(matrix, endX, startY, endZ).color(r, g, b, alpha).normal(1, 0, 0).endVertex();
        }
    }

    public static void drawCubeFromAABB(Matrix4f matrix, VertexConsumer buffer, AABB aabb, int color, Predicate<Direction> directionPredicate) {
        drawCube(matrix, buffer, (float) aabb.minX, (float) aabb.minY, (float) aabb.minZ, (float) aabb.maxX, (float) aabb.maxY, (float) aabb.maxZ, color, directionPredicate);
    }

    public static class Type extends RenderType {
        public static final RenderType NO_CULL = create(Eraser.MOD_ID + ":no_cull",
                DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, CompositeState.builder()
                        .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                        .setOutputState(WEATHER_TARGET)
                        .setCullState(RenderStateShard.NO_CULL)
                        .createCompositeState(false)
        );

        public Type(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
            super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
        }
    }
}
