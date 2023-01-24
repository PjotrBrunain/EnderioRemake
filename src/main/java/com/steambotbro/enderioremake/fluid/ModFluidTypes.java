package com.steambotbro.enderioremake.fluid;

import com.steambotbro.enderioremake.EnderioRemake;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {
    //TO-DO Revisit
    public static final ResourceLocation FIREWATER_STILL_RL = new ResourceLocation("block/fluid_fire_water_still");
    public static final ResourceLocation FIREWATER_FLOWING_RL = new ResourceLocation("block/fluid_fire_water_flow");

    public static final ResourceLocation FIREWATER_OVERLAY_RL = new ResourceLocation("block/fluid_fire_water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, EnderioRemake.MOD_ID);

    public static final RegistryObject<FluidType> FIRE_WATER_FLUID_TYPE = register("fire_water_fluid",
            FluidType.Properties.create().lightLevel(10).density(5).viscosity(1).sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK));

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(FIREWATER_STILL_RL, FIREWATER_FLOWING_RL, FIREWATER_OVERLAY_RL,
                0xA1FFFFFF, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
