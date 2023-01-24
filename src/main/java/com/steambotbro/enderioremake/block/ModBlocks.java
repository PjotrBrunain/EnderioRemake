package com.steambotbro.enderioremake.block;

import com.steambotbro.enderioremake.EnderioRemake;
import com.steambotbro.enderioremake.block.custom.AlloySmelterBlock;
import com.steambotbro.enderioremake.fluid.ModFluids;
import com.steambotbro.enderioremake.item.ModCreativeModeTab;
import com.steambotbro.enderioremake.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EnderioRemake.MOD_ID);

    public static final RegistryObject<Block> CONDUCTIVEIRONBLOCK = registerBlock("conductive_iron_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ALLOY_SMELTER_BLOCK = registerBlock("alloy_smelter",
            () -> new AlloySmelterBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(AlloySmelterBlock.LIT) ? 15 : 0)));

    public static final RegistryObject<LiquidBlock> FIRE_WATER_BLOCK = BLOCKS.register("fire_water_block",
            () -> new LiquidBlock(ModFluids.SOURCE_FIRE_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

        eventBus.addListener(ModBlocks::addCreative);
    }

    private static void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        if (event.getTab() == ModCreativeModeTab.ENDERIOREMAKE_TAB)
        {
            event.accept(CONDUCTIVEIRONBLOCK);
            event.accept(ALLOY_SMELTER_BLOCK);
        }
    }
}
