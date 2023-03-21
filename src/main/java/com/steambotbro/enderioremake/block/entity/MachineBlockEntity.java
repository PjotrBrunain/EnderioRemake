package com.steambotbro.enderioremake.block.entity;

import com.steambotbro.enderioremake.block.custom.AlloySmelterBlock;
import com.steambotbro.enderioremake.networking.ModMessages;
import com.steambotbro.enderioremake.networking.packet.EnergySyncS2CPacket;
import com.steambotbro.enderioremake.util.ModEnergyStorage;
import com.steambotbro.enderioremake.util.SlotType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.TreeMap;

public abstract class MachineBlockEntity extends BlockEntity
{
    protected ModItemStackHandler itemHandler = null;
    protected final ModEnergyStorage ENERGY_STORAGE;

    protected LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private int numOfInputSlots = 0;
    private int numOfOutputSlots = 0;
    private int numOfSpecialSlots = 0;

    protected Map<Direction, SlotType> directionSlotTypeMap =
            Map.of(Direction.UP, SlotType.INPUT,
                    Direction.DOWN, SlotType.OUTPUT,
                    Direction.NORTH, SlotType.NEUTRAL,
                    Direction.EAST, SlotType.NEUTRAL,
                    Direction.SOUTH, SlotType.NEUTRAL,
                    Direction.WEST, SlotType.NEUTRAL);



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

    public void  InitItemHandler(int numOfInputs, int numOfOutputs, int numOfSpecialSlots, BlockEntity pEntity)
    {
        int numOfSlots = numOfInputs + numOfOutputs + numOfSpecialSlots;
        itemHandler = new ModItemStackHandler(pEntity, numOfSlots);
        numOfInputSlots = numOfInputs;
        numOfOutputSlots = numOfOutputs;
        this.numOfSpecialSlots = numOfSpecialSlots;
    }

    public IEnergyStorage getEnergyStorage(){return ENERGY_STORAGE;}
    public void setEnergyLevel(int energy){this.ENERGY_STORAGE.setEnergy(energy);}

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY)
        {
            return lazyEnergyHandler.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            if (side == null)
            {
                return lazyItemHandler.cast();
            }

            Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = new TreeMap<>();

            for (Map.Entry<Direction, SlotType> entry : directionSlotTypeMap.entrySet()) {
                switch (entry.getValue())
                {
                    case INPUT ->
                            directionWrappedHandlerMap.put(entry.getKey(), LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i >= 0 && i < numOfInputSlots && itemHandler.isItemValid(0, s))));
                    case OUTPUT ->
                            directionWrappedHandlerMap.put(entry.getKey(), LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i >= numOfInputSlots && i < numOfOutputSlots+numOfInputSlots,(integer, itemStack) -> false)));
                    case SPECIAL ->
                            directionWrappedHandlerMap.put(entry.getKey(), LazyOptional.of(() -> new WrappedHandler(itemHandler, integer -> integer >= numOfInputSlots+numOfOutputSlots && integer < numOfOutputSlots+numOfInputSlots+numOfSpecialSlots, (integer, itemStack) -> integer >=numOfInputSlots+numOfOutputSlots && integer < numOfOutputSlots+numOfInputSlots+numOfSpecialSlots && itemHandler.isItemValid(0, itemStack))));
                    case NEUTRAL -> {}
                }
            }
                    //Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 3, (i, s) -> false)),
                    //        Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                    //                (index, stack) -> itemHandler.isItemValid(1, stack) && index == 1)),
                    //        Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 3, (i,s) -> false)),
                    //        Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2,
                    //                (index, stack) -> itemHandler.isItemValid(2, stack) && index == 2)),
                    //        Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0,
                    //                (index, stack) -> itemHandler.isItemValid(0, stack) && index == 0)));

            if (directionWrappedHandlerMap.containsKey(side))
            {
                Direction localDir = this.getBlockState().getValue(AlloySmelterBlock.FACING);

                if (side == Direction.UP || side == Direction.DOWN)
                {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir)
                        {
                            default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                            case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                            case SOUTH ->  directionWrappedHandlerMap.get(side).cast();
                            case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                        };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("machine.energy", this.ENERGY_STORAGE.getEnergyStored());
        pTag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(pTag.getInt("machine.energy"));
        super.load(pTag);
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}
