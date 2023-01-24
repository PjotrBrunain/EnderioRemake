package com.steambotbro.enderioremake.fluid;

import com.steambotbro.enderioremake.EnderioRemake;
import com.steambotbro.enderioremake.block.ModBlocks;
import com.steambotbro.enderioremake.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    //TO-DO Revisit


    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, EnderioRemake.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_FIRE_WATER = FLUIDS.register("fire_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.FIRE_WATER_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_FIRE_WATER = FLUIDS.register("flowing_fire_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.FIRE_WATER_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties FIRE_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.FIRE_WATER_FLUID_TYPE, SOURCE_FIRE_WATER, FLOWING_FIRE_WATER)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.FIRE_WATER_BLOCK).bucket(ModItems.FIRE_WATER_BUCKET);

    public static void register(IEventBus eventBus)
    {
        FLUIDS.register(eventBus);
    }
}
