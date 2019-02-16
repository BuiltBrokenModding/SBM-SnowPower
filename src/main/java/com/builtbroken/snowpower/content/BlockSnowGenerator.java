package com.builtbroken.snowpower.content;

import javax.annotation.Nullable;

import com.builtbroken.snowpower.Snowpower;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/2/2018.
 */
public class BlockSnowGenerator extends BlockContainer
{
    public BlockSnowGenerator()
    {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F, 6.0F)); //resistance like stone
        setRegistryName(Snowpower.PREFIX + "snowgenerator");
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
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
                player.sendMessage(new TextComponentString(I18n.format("snowgenerator.chat.power") + " " + ((TileEntitySnowGenerator) tile).powerStorage.getEnergyStored()));
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
    public TileEntity createNewTileEntity(IBlockReader world)
    {
        return new TileEntitySnowGenerator();
    }
}
