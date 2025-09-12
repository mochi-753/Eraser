package com.mochi_753.eraser.network;

import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundCrashPacket(String message) {
    public static void encode(ClientboundCrashPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.message());
    }

    public static ClientboundCrashPacket decode(FriendlyByteBuf buf) {
        return new ClientboundCrashPacket(buf.readUtf());
    }

    public static void handle(ClientboundCrashPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().delayCrash(new CrashReport("^^", new Throwable(packet.message())));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
