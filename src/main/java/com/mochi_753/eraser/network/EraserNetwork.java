package com.mochi_753.eraser.network;

import com.mochi_753.eraser.Eraser;
import com.mochi_753.eraser.util.GetResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class EraserNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            GetResourceLocation.getResourceLocation(Eraser.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;
        CHANNEL.registerMessage(id++, ClientboundCrashPacket.class, ClientboundCrashPacket::encode, ClientboundCrashPacket::decode, ClientboundCrashPacket::handle);
    }
}
