package com.builtbroken.snowpower;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Hennamann(Ole Henrik Stabell) on 03/02/2018.
 */
@Config(modid = Snowpower.DOMAIN, name = "bbm/[SBM] Snow Power")
@Config.LangKey("snowpower.config.title")
public class ConfigSnowpower {

    @Config.Name("Consume Snow Delay")
    @Config.Comment("Sets how long it takes to consume snow in the snow generator.")
    public static int CONSUME_SNOW_DELAY_RANDOM = 100;

    @Mod.EventBusSubscriber(modid = Snowpower.DOMAIN)
    private static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Snowpower.DOMAIN)) {
                ConfigManager.sync(Snowpower.DOMAIN, Config.Type.INSTANCE);
            }
        }
    }
}
