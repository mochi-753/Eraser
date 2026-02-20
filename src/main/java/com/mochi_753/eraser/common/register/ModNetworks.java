package com.mochi_753.eraser.common.register;

import com.mochi_753.eraser.common.Eraser;
import com.mochi_753.eraser.common.network.ClientboundCrashPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworks {
    private static final String PROTOCOL_VERSION = "1";

    @SuppressWarnings("removal")
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Eraser.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;
        CHANNEL.messageBuilder(ClientboundCrashPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundCrashPacket::encode)
                .decoder(ClientboundCrashPacket::decode)
                .consumerMainThread(ClientboundCrashPacket::handle)
                .add();
    }
}
