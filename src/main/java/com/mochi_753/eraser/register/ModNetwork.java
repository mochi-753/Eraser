package com.mochi_753.eraser.register;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.packet.EraserCrashPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;


public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";

    @SuppressWarnings("removal")
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Eraser.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, EraserCrashPacket.class, EraserCrashPacket::encode, EraserCrashPacket::decode, EraserCrashPacket::handle);
    }
}
