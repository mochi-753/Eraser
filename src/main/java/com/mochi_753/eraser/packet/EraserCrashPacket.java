package com.mochi_753.eraser.packet;

import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public record EraserCrashPacket(String message) {
    public static void encode(EraserCrashPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.message());
    }

    public static EraserCrashPacket decode(FriendlyByteBuf buf) {
        return new EraserCrashPacket(buf.readUtf());
    }

    public static void handle(EraserCrashPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().player != null) {
                Minecraft.crash(new CrashReport(":(", new Throwable(packet.message())));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
