package com.mochi_753.eraser.common.network;

import com.mochi_753.eraser.common.Eraser;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraftforge.network.NetworkEvent;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public record ClientboundCrashPacket(String message, String throwableMessage) {
    public static void encode(ClientboundCrashPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.message());
        buf.writeUtf(packet.throwableMessage());
    }

    public static ClientboundCrashPacket decode(FriendlyByteBuf buf) {
        return new ClientboundCrashPacket(buf.readUtf(), buf.readUtf());
    }

    public static void handle(ClientboundCrashPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Eraser.LOGGER.info("Rest assured. This crash isn't a bug — it's Eraser's doing!");
            if (RandomSource.create().nextInt(100) == 0) {
                try {
                    Field field = Unsafe.class.getDeclaredField("theUnsafe"); // JVMごとクラッシュさせる
                    field.setAccessible(true); // 許されざるコード
                    Unsafe unsafe = (Unsafe) field.get(null); // もはやマルウェアである
                    unsafe.putAddress(0, 0); // 深夜テンションって怖いね
                } catch (Exception e) {
                    Eraser.LOGGER.error(e.getMessage());
                }
            } else {
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().delayCrash(new CrashReport(packet.message(), new Throwable(packet.throwableMessage())));
                }
            }
        });
        context.setPacketHandled(true);
    }
}
