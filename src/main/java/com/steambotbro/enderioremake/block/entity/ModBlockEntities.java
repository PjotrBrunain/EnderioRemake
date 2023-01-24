package com.steambotbro.enderioremake.block.entity;

import com.steambotbro.enderioremake.EnderioRemake;
import com.steambotbro.enderioremake.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EnderioRemake.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlloySmelterBlockEntity>> ALLOY_SMELTER =
            BLOCK_ENTITIES.register("alloy_smelter", () ->
                    BlockEntityType.Builder.of(AlloySmelterBlockEntity::new,
                            ModBlocks.ALLOY_SMELTER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
