package com.mochi_753.eraser;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class EraserConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue allowDisconnectPlayer;
        public final ForgeConfigSpec.BooleanValue allowCrashClient;
        public final ForgeConfigSpec.DoubleValue eraseRadius;
        public final ForgeConfigSpec.IntValue eraserDurability;
        public final ForgeConfigSpec.IntValue kneadedEraserDurability;
        public final ForgeConfigSpec.IntValue setHealthSpamCount;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("EraserMOD config settings").push("general");

            allowDisconnectPlayer = builder.
                    comment("Should the Eraser be able to disconnect players?")
                    .define("allowDisconnectPlayer", false);
            allowCrashClient = builder.
                    comment("Should the Eraser be able to crash clients?")
                    .define("allowCrashClient", false);
            eraseRadius = builder
                    .comment("Radius for crouch-right-click erase (blocks)")
                    .defineInRange("eraseRadius", 4D, 1D, 64D);
            eraserDurability = builder
                    .comment("Eraser durability")
                    .defineInRange("eraserDurability", 100, 1, Integer.MAX_VALUE);
            kneadedEraserDurability = builder
                    .comment("Kneaded Eraser durability")
                    .defineInRange("kneadedEraserDurability", 10, 1, Integer.MAX_VALUE);
            setHealthSpamCount = builder
                    .comment("Repetition count for setHealth(0) spam. For those enemies who think 0 HP still means \"I'm fine\".")
                    .defineInRange("setHealthSpamCount", 100, 1, Integer.MAX_VALUE);

            builder.pop();
        }
    }
}
