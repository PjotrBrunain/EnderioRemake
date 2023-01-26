package com.steambotbro.enderioremake.block.entity;

import com.steambotbro.enderioremake.block.custom.AlloySmelterBlock;
import com.steambotbro.enderioremake.recipe.AlloySmelterRecipe;
import com.steambotbro.enderioremake.screen.AlloySmelterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("removal")
public class AlloySmelterBlockEntity extends BlockEntity implements MenuProvider
{
//    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
//        @Override
//        protected void onContentsChanged(int slot) {
//            setChanged();
//        }
//
//        @Override
//        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
//            return switch (slot)
//                    {
//                        case 0 -> stack.getItem() == ModItems.CONDUCTIVEIRON.get();
//                        case 1 -> stack.getItem() == ModItems.CONDUCTIVEIRON.get();
//                        case 2 -> stack.getItem() == ModItems.CONDUCTIVEIRON.get();
//                        case 3 -> false;
//                        default -> super.isItemValid(slot, stack);
//                    };
//        }
//    };

    private final ModItemStackHandler itemHandler = new ModItemStackHandler(this, 4);

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 3, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> itemHandler.isItemValid(1, stack) && index == 1)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 3, (i,s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2,
                            (index, stack) -> itemHandler.isItemValid(2, stack) && index == 2)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0,
                            (index, stack) -> itemHandler.isItemValid(0, stack) && index == 0)));


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public AlloySmelterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALLOY_SMELTER.get(), pPos, pBlockState);
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
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("enderioremake.alloy_smelter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AlloySmelterMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (side == null)
            {
                return lazyItemHandler.cast();
            }

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
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("alloy_smelter.progress", this.progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag)
    {
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("alloy_smelter.progress");
        super.load(pTag);
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, AlloySmelterBlockEntity pEntity)
    {
        if (level.isClientSide())
        {
            return;
        }

        if (hasRecipe(pEntity))
        {
            pEntity.progress++;
            setChanged(level, blockPos, blockState);

            if (pEntity.progress >= pEntity.maxProgress)
            {
                craftItem(pEntity);
            }
        } else
        {
            pEntity.resetProgress();
            setChanged(level, blockPos, blockState);
        }
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
            pEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(3).getCount()+1));
            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(AlloySmelterBlockEntity pEntity)
    {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

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
