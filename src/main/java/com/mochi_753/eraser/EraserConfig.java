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
        public final ForgeConfigSpec.BooleanValue allowErasePlayer;
        public final ForgeConfigSpec.DoubleValue eraseRadius;
        public final ForgeConfigSpec.IntValue eraserDurability;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("EraserMOD config settings").push("general");

            allowErasePlayer = builder.
                    comment("Should the Eraser be able to disconnect players?")
                    .define("allowErasePlayer", false);
            eraseRadius = builder
                    .comment("Radius for crouch-right-click erase (blocks)")
                    .defineInRange("eraseRadius", 4D, 1D, 64D);
            eraserDurability = builder
                    .comment("Eraser durability")
                    .defineInRange("eraserDurability", 100, 1, Integer.MAX_VALUE);

            builder.pop();
        }
    }
}
