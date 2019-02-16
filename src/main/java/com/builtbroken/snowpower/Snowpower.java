package com.builtbroken.snowpower;

import com.builtbroken.snowpower.content.BlockSnowGenerator;
import com.builtbroken.snowpower.content.TileEntitySnowGenerator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/2/2018.
 */
@Mod(Snowpower.DOMAIN)
@Mod.EventBusSubscriber(bus=Bus.MOD)
public class Snowpower
{
    public static final String DOMAIN = "snowpower";
    public static final String PREFIX = DOMAIN + ":";

    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String MC_VERSION = "@MC@";
    public static final String VERSION = MC_VERSION + "-" + MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD_VERSION;

    public static Snowpower INSTANCE;

    /** Block instance for the snow generator */
    public static Block blockSnowGenerator;

    public static TileEntityType<TileEntitySnowGenerator> SNOW_GEN_TE_TYPE;

    public Snowpower()
    {
        INSTANCE = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigSnowpower.CONFIG_SPEC);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(blockSnowGenerator = new BlockSnowGenerator());
        SNOW_GEN_TE_TYPE = TileEntityType.register(blockSnowGenerator.getRegistryName().toString(), TileEntityType.Builder.create(TileEntitySnowGenerator::new));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(blockSnowGenerator, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(blockSnowGenerator.getRegistryName()));
    }
}
