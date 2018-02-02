package com.builtbroken.snowpower;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/2/2018.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void doLoadModels()
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Snowpower.blockSnowGenerator), 0, new ModelResourceLocation(Snowpower.blockSnowGenerator.getRegistryName(), "inventory"));
    }
}
