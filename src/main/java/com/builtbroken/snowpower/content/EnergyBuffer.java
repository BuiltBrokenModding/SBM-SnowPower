package com.builtbroken.snowpower.content;

import net.minecraftforge.energy.EnergyStorage;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/2/2018.
 */
public class EnergyBuffer extends EnergyStorage
{
    public EnergyBuffer(int capacity, int maxReceive, int maxExtract, int energy)
    {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setEnergy(int energy)
    {
        this.energy = energy;
    }
}
