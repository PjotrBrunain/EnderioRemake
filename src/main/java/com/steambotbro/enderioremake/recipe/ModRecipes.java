package com.steambotbro.enderioremake.recipe;

import com.steambotbro.enderioremake.EnderioRemake;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EnderioRemake.MOD_ID);

    public static final RegistryObject<RecipeSerializer<AlloySmelterRecipe>> ALLOY_SMELTING_SERIALIZER =
            SERIALIZERS.register("alloy_smelting", () -> AlloySmelterRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus)
    {
        SERIALIZERS.register(eventBus);
    }
}
