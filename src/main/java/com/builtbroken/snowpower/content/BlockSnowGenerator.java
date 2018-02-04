package com.builtbroken.snowpower.content;

import com.builtbroken.snowpower.Snowpower;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/2/2018.
 */
public class BlockSnowGenerator extends BlockContainer
{
    public BlockSnowGenerator()
    {
        super(Material.ROCK);
        setUnlocalizedName(Snowpower.PREFIX + "generator.snow");
        setRegistryName(Snowpower.PREFIX + "snowgenerator");
        setCreativeTab(CreativeTabs.MISC);
        setHardness(1);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!player.getHeldItem(hand).isEmpty())
        {
            return false;
        }
        if (!world.isRemote)
        {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntitySnowGenerator)
            {
                player.sendMessage(new TextComponentString(I18n.translateToLocal("snowgenerator.chat.power") + " " + ((TileEntitySnowGenerator) tile).powerStorage.getEnergyStored()));
            }
        }
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntitySnowGenerator();
    }
}
