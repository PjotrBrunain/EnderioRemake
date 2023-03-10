package com.steambotbro.enderioremake.block.entity;

import com.steambotbro.enderioremake.recipe.AlloySmelterRecipe;
import com.steambotbro.enderioremake.screen.AlloySmelterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlloySmelterBlockEntity extends MachineBlockEntity implements MenuProvider
{
    private static final int ENERGY_REQ = 32;
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public AlloySmelterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALLOY_SMELTER.get(), pPos, pBlockState,60000,1000);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex)
                        {
                            case 0 -> AlloySmelterBlockEntity.this.progress;
                            case 1 -> AlloySmelterBlockEntity.this.maxProgress;
                            default -> 0;
                        };
            }

            @Override
            public void set(int pIndex, int pValue)
            {
                switch (pIndex)
                {
                    case 0 -> AlloySmelterBlockEntity.this.progress = pValue;
                    case 1 -> AlloySmelterBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        InitItemHandler(4, this);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("enderioremake.alloy_smelter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new AlloySmelterMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
        //pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("alloy_smelter.progress", this.progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag)
    {
//        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("alloy_smelter.progress");

        //ENERGY_STORAGE.setEnergy(pTag.getInt("alloy_smelter.energy"));
        super.load(pTag);
    }

    //public void drops()
    //{
    //    SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
    //    for (int i = 0; i < itemHandler.getSlots(); i++) {
    //        inventory.setItem(i, itemHandler.getStackInSlot(i));
    //    }

    //    Containers.dropContents(this.level, this.worldPosition, inventory);
    //}

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, AlloySmelterBlockEntity pEntity)
    {
        if (level.isClientSide())
        {
            return;
        }



        if (hasRecipe(pEntity) && hasEnoughEnergy(pEntity))
        {
            pEntity.progress++;
            extractEnergy(pEntity);
            setChanged(level, blockPos, blockState);

            if (pEntity.progress >= pEntity.maxProgress)
            {
                craftItem(pEntity);
            }
        }
        else if (!hasEnoughEnergy(pEntity))
        {
            pEntity.progress--;
            setChanged(level, blockPos, blockState);
        }
        else
        {
            pEntity.resetProgress();
            setChanged(level, blockPos, blockState);
        }
    }

    private static void extractEnergy(AlloySmelterBlockEntity pEntity)
    {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(AlloySmelterBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private void resetProgress()
    {
        this.progress = 0;
    }

    private static void craftItem(AlloySmelterBlockEntity pEntity)
    {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        assert level != null;
        Optional<AlloySmelterRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlloySmelterRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(pEntity))
        {
            for (int i = 0; i < 3; i++) {
                if (!pEntity.itemHandler.getStackInSlot(i).isEmpty())
                {
                    pEntity.itemHandler.extractItem(i,1,false);
                }
            }
            if(recipe.isPresent()) {
                pEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultItem().getItem(),
                        pEntity.itemHandler.getStackInSlot(3).getCount() + 1));
                pEntity.resetProgress();
            }
        }
    }

    private static boolean hasRecipe(AlloySmelterBlockEntity pEntity)
    {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        assert level != null;
        Optional<AlloySmelterRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlloySmelterRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory,recipe.get().getResultItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(3).getItem() == itemStack.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }
}
