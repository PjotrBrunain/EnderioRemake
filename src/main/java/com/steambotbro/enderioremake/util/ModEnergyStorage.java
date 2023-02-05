package com.steambotbro.enderioremake.util;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {
    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if (extractedEnergy != 0)
        {
            onEnergyChange();
        }

        return extractedEnergy;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receivedEnergy = super.receiveEnergy(maxReceive, simulate);
        if (receivedEnergy != 0)
        {
            onEnergyChange();
        }

        return receivedEnergy;
    }

    public int setEnergy(int energy)
    {
        this.energy = energy;
        //onEnergyChange();
        return energy;
    }

    public abstract void onEnergyChange();
}
