package com.steambotbro.enderioremake.block.entity;

import com.steambotbro.enderioremake.recipe.AlloySmelterRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModItemStackHandler extends ItemStackHandler
{
    private final BlockEntity pEntity;

    ModItemStackHandler(BlockEntity pEntity)
    {
        super(1);
        this.pEntity = pEntity;
    }
    ModItemStackHandler(BlockEntity pEntity, int size)
    {
        super(size);
        this.pEntity = pEntity;
    }
    ModItemStackHandler(BlockEntity pEntity, NonNullList<ItemStack> stacks)
    {
        super(stacks);
        this.pEntity = pEntity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        pEntity.setChanged();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        Level level = pEntity.getLevel();
        if (!level.isClientSide()) {
            SimpleContainer inventory = new SimpleContainer(getSlots());
            for (int i = 0; i < getSlots(); i++) {
                inventory.setItem(i, getStackInSlot(i));
            }

            if (inventory.isEmpty())
            {
                List<AlloySmelterRecipe> recipes = level.getRecipeManager()
                        .getAllRecipesFor(AlloySmelterRecipe.Type.INSTANCE);


                switch (slot)
                {
                    case 0, 1, 2 ->
                    {
                        boolean accepted = false;
                        for (AlloySmelterRecipe recipe : recipes) {
                            NonNullList<Ingredient> ingredients = recipe.getIngredients();
                            for (Ingredient ingredient : ingredients) {
                                if (ingredient.test(stack)) {
                                    accepted = true;
                                    break;
                                }
                            }
                            //accepted = accepted || recipe.getIngredients().get(0).test(stack);
                        }
                        return accepted;
                    }
                    //case 1 ->
                    //{
                    //    boolean accepted = false;
                    //    for (AlloySmelterRecipe recipe : recipes)
                    //    {
                    //        NonNullList<Ingredient> ingredients = recipe.getIngredients();
                    //        for (Ingredient ingredient : ingredients)
                    //        {
                    //            if (ingredient.test(stack))
                    //            {
                    //                accepted = true;
                    //                break;
                    //            }
                    //        }
                    //        //accepted = accepted || recipe.getIngredients().get(0).test(stack);
                    //    }
                    //    return accepted;
                    //}
                    //case 2 ->
                    //{
                    //    boolean accepted = false;
                    //    for (AlloySmelterRecipe recipe : recipes)
                    //    {
                    //        NonNullList<Ingredient> ingredients = recipe.getIngredients();
                    //        for (Ingredient ingredient : ingredients)
                    //        {
                    //            if (ingredient.test(stack))
                    //            {
                    //                accepted = true;
                    //                break;
                    //            }
                    //        }
                    //        //accepted = accepted || recipe.getIngredients().get(0).test(stack);
                    //    }
                    //    return accepted;
                    //}
                    case 3 -> {
                        return false;
                    }
                    default -> {
                        return super.isItemValid(slot, stack);
                    }
                }
            }
            else
            {
                List<AlloySmelterRecipe> recipes = level.getRecipeManager()
                        .getAllRecipesFor(AlloySmelterRecipe.Type.INSTANCE);

                boolean accepted = false;
                for (AlloySmelterRecipe recipe : recipes)
                {
                    NonNullList<Ingredient> ingredients = recipe.getIngredients();
                    for (Ingredient ingredient : ingredients)
                    {
                        int invIndex = 0;
                        boolean isInInv = false;
                        for (int i = 0; i < inventory.getContainerSize(); i++) {
                            if (ingredient.test(inventory.getItem(i)))
                            {
                                invIndex = i;
                                isInInv = true;
                                break;
                            }
                        }
                        if (!isInInv)
                        {
                            if (ingredient.test(stack)) {
                                accepted = true;
                                break;
                            }
                        }
                        else if (invIndex == slot && inventory.getItem(invIndex).getCount() < inventory.getItem(invIndex).getMaxStackSize() && inventory.getItem(invIndex).sameItem(stack))
                        {
                            accepted = true;
                            break;
                        }
                    }
                }
                return accepted;
            }
        }
        return false;
    }
}
