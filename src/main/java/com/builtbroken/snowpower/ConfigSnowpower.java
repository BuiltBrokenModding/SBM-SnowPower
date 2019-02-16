package com.builtbroken.snowpower;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Hennamann(Ole Henrik Stabell) on 03/02/2018.
 */
public class ConfigSnowpower {
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ConfigSnowpower CONFIG;

    public final IntValue consumeSnowDelayRandom;

    static
    {
        Pair<ConfigSnowpower,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigSnowpower::new);

        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public ConfigSnowpower(ForgeConfigSpec.Builder builder)
    {
        consumeSnowDelayRandom = builder
                .comment("Sets how long it takes to consume snow in the snow generator.")
                .defineInRange("Consume Snow Delay", 100, 0, Integer.MAX_VALUE);
    }
}
