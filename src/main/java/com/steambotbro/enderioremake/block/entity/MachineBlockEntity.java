package com.steambotbro.enderioremake.block.entity;

import com.steambotbro.enderioremake.networking.ModMessages;
import com.steambotbro.enderioremake.networking.packet.EnergySyncS2CPacket;
import com.steambotbro.enderioremake.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MachineBlockEntity extends BlockEntity
{
    protected final ModEnergyStorage ENERGY_STORAGE;

    protected LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public MachineBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int energyCapacity, int energyTransfer)
    {
        super(pType, pPos, pBlockState);
        ENERGY_STORAGE = new ModEnergyStorage(energyCapacity, energyTransfer) {
            @Override
            public void onEnergyChange() {
                setChanged();
                ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
            }
        };
    }

    public IEnergyStorage getEnergyStorage(){return ENERGY_STORAGE;}
    public void setEnergyLevel(int energy){this.ENERGY_STORAGE.setEnergy(energy);}

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY)
        {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("machine.energy", this.ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        ENERGY_STORAGE.setEnergy(pTag.getInt("machine.energy"));
        super.load(pTag);
    }
}
