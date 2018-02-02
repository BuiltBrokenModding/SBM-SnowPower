package com.builtbroken.snowpower;

import com.builtbroken.snowpower.content.BlockSnowGenerator;
import com.builtbroken.snowpower.content.TileEntitySnowGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/2/2018.
 */
@Mod(modid = Snowpower.DOMAIN, name = "[SBM] Snow Power", version = Snowpower.VERSION)
@Mod.EventBusSubscriber
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

    @Mod.Instance(DOMAIN)
    public static Snowpower INSTANCE;

    @SidedProxy(clientSide = "com.builtbroken.snowpower.ClientProxy", serverSide = "com.builtbroken.snowpower.CommonProxy")
    public static CommonProxy proxy;

    /** Block instance for the snow generator */
    public static Block blockSnowGenerator;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(blockSnowGenerator = new BlockSnowGenerator());
        GameRegistry.registerTileEntity(TileEntitySnowGenerator.class, blockSnowGenerator.getRegistryName().toString());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(blockSnowGenerator).setRegistryName(blockSnowGenerator.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        proxy.doLoadModels();
    }
}
