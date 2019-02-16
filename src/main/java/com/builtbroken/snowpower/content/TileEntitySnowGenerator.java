package com.builtbroken.snowpower.content;

import com.builtbroken.snowpower.ConfigSnowpower;
import com.builtbroken.snowpower.Snowpower;

import net.minecraft.block.BlockSnowLayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidTank;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/2/2018.
 */
public class TileEntitySnowGenerator extends TileEntity implements ITickable
{
    public TileEntitySnowGenerator()
    {
        super(Snowpower.SNOW_GEN_TE_TYPE);
    }

    public static int POWER_CAP = 1000;
    public static int POWER_TRANSFER = 10;
    public static int POWER_CREATION = 1;

    public static int CHECK_SNOW_DELAY = 100;
    public static int CONSUME_SNOW_DELAY = 20 * 60;

    public static final String NBT_ENERGY = "energy";

    private int snowCheckTick = -1;
    private int snowConsumeTick = -1;

    public EnergyBuffer powerStorage;
    public FluidTank fluidTank;

    boolean hasSnow = false;

    @Override
    public void onLoad()
    {
        powerStorage = new EnergyBuffer(POWER_CAP, POWER_TRANSFER, POWER_TRANSFER, 0);
    }

    @Override
    public void tick()
    {
        if(!world.isRemote)
        {
            //Check that we have snow
            if (snowCheckTick-- <= 0)
            {
                //Reset tick
                snowCheckTick = CHECK_SNOW_DELAY + world.rand.nextInt(10);

                //Get block
                BlockPos pos = getPos().up();
                IBlockState state = world.getBlockState(pos);

                //Do check
                hasSnow = state.getBlock() == Blocks.SNOW;
            }

            if (hasSnow)
            {
                //Generate power if snow is above
                createPower();
            }

            //Output
            if (powerStorage.getEnergyStored() > 0)
            {
                outputPower();
            }
        }
    }

    /**
     * Creates power, and does consume snow check
     */
    protected void createPower()
    {
        //Consume snow
        if (snowCheckTick-- <= 0)
        {
            //Reset delay
            snowConsumeTick = CONSUME_SNOW_DELAY + world.rand.nextInt(ConfigSnowpower.CONFIG.consumeSnowDelayRandom.get());

            //Get state
            BlockPos pos = getPos().up();
            IBlockState state = world.getBlockState(pos);

            //Make sure is snow
            if (state.getBlock() == Blocks.SNOW)
            {
                //update layers
                int layers = state.get(BlockSnowLayer.LAYERS);
                layers -= 1;

                if (layers <= 0)
                {
                    //Kill block
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
                else
                {
                    //Output block state
                    state = state.with(BlockSnowLayer.LAYERS, layers);
                    world.setBlockState(pos, state);
                }
            }
            else
            {
                return; //don't output power
            }
        }

        //Output power
        powerStorage.receiveEnergy(POWER_CREATION, false);
    }

    /**
     * Outputs power to all 6 sides
     */
    protected void outputPower()
    {
        //Loop all 6 sides of our block
        for (EnumFacing facing : EnumFacing.values())
        {
            //Get position with offset
            BlockPos pos = getPos().offset(facing);

            //Check the block is loaded to avoid loading extra chunks
            if (world.isBlockLoaded(pos))
            {
                //Get tile from the world
                TileEntity tile = world.getTileEntity(pos);

                //Check tile exists
                if (tile != null)
                {
                    LazyOptional<IEnergyStorage> storage = tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());

                    //Check to make sure the tile has the energy capability
                    if(storage.isPresent())
                    {
                        //Get power
                        IEnergyStorage ies = storage.orElse(null); //TODO: this might be wonky, check back later when more examples of power caps are available

                        if(ies != null)
                        {
                            //Figure out the power we can give, simulate
                            int powerToGive = powerStorage.extractEnergy(Integer.MAX_VALUE, true);

                            //Give power to the tile, get power actually added
                            int powerGiven = ies.receiveEnergy(powerToGive, false);

                            //Drain power given from our tile
                            powerStorage.extractEnergy(powerGiven, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void read(NBTTagCompound compound)
    {
        super.read(compound);
        compound.putInt(NBT_ENERGY, powerStorage.getEnergyStored());
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound)
    {
        powerStorage.setEnergy(compound.getInt(NBT_ENERGY));
        return super.write(compound);
    }
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, EnumFacing side)
    {
        if(cap == CapabilityEnergy.ENERGY)
            return LazyOptional.of(() -> powerStorage).cast();
        else return LazyOptional.empty();
    }
}
