package com.mochi_753.eraser.common;

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
        public final ForgeConfigSpec.IntValue setHealthSpamCount;
        public final ForgeConfigSpec.IntValue novelizeSpamCount;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("EraserMOD config settings").push("general");

            allowDisconnectPlayer = builder
                    .comment("")
                    .comment("Should the Eraser be able to disconnect players?")
                    .define("allowDisconnectPlayer", false);

            allowCrashClient = builder
                    .comment("")
                    .comment("Should the Eraser be able to crash clients?")
                    .define("allowCrashClient", false);

            eraseRadius = builder
                    .comment("")
                    .comment("Radius for crouch-right-click erase.json (blocks)")
                    .defineInRange("eraseRadius", 4D, 1D, 64D);

            setHealthSpamCount = builder
                    .comment("")
                    .comment("Repetition count for setHealth(0) spam. For those enemies who think 0 HP still means \"I'm fine\".")
                    .defineInRange("setHealthSpamCount", 10, 1, Integer.MAX_VALUE);

            novelizeSpamCount = builder
                    .comment("")
                    .comment("Repetition count for novelize() spam. For those enemies who think 0 HP still means \"ブゥン!\".")
                    .comment("Configuration that only works when Hyperlink Mod is installed.")
                    .defineInRange("novelizeSpamCount", 1, 1, Integer.MAX_VALUE);

            builder.pop();
        }
    }
}
