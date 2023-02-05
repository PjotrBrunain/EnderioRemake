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
    public static final RegistryObject<Block> CONSTRUCTIONALLOYBLOCK = registerBlock("block_alloy_construction_alloy",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRUDESTEELBLOCK = registerBlock("block_alloy_crude_steel",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTALLINEALLOYBLOCK = registerBlock("block_alloy_crystalline_alloy",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTALLINEPINKSLIMEBLOCK = registerBlock("block_alloy_crystalline_pink_slime",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DARKSTEELBLOCK = registerBlock("block_alloy_dark_steel",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ELECTRICALSTEELBLOCK = registerBlock("block_alloy_electrical_steel",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ENDSTEELBLOCK = registerBlock("block_alloy_end_steel",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ENERGETICSILVERBLOCK = registerBlock("block_alloy_energetic_silver",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MELODICALLOYBLOCK = registerBlock("block_alloy_melodic_alloy",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PULSATINGIRONBLOCK = registerBlock("block_alloy_pulsating_iron",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> REDSTONEALLOYBLOCK = registerBlock("block_alloy_redstone_alloy",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SOULARIUMBLOCK = registerBlock("block_alloy_soularium",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STELLARALLOYBLOCK = registerBlock("block_alloy_stellar_alloy",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VIBRANTALLOYBLOCK = registerBlock("block_alloy_vibrant_alloy",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VIVIDALLOYBLOCK = registerBlock("block_alloy_vivid_alloy",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ALLOY_SMELTER_BLOCK = registerBlock("alloy_smelter",
            () -> new AlloySmelterBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(AlloySmelterBlock.LIT) ? 15 : 0)));

    //public static final RegistryObject<LiquidBlock> FIRE_WATER_BLOCK = BLOCKS.register("fire_water_block",
    //        () -> new LiquidBlock(ModFluids.SOURCE_FIRE_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));

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
            event.accept(CONSTRUCTIONALLOYBLOCK);
            event.accept(CRUDESTEELBLOCK);
            event.accept(CRYSTALLINEALLOYBLOCK);
            event.accept(CRYSTALLINEPINKSLIMEBLOCK);
            event.accept(DARKSTEELBLOCK);
            event.accept(ELECTRICALSTEELBLOCK);
            event.accept(ENDSTEELBLOCK);
            event.accept(ENERGETICSILVERBLOCK);
            event.accept(MELODICALLOYBLOCK);
            event.accept(PULSATINGIRONBLOCK);
            event.accept(REDSTONEALLOYBLOCK);
            event.accept(SOULARIUMBLOCK);
            event.accept(STELLARALLOYBLOCK);
            event.accept(VIBRANTALLOYBLOCK);
            event.accept(VIVIDALLOYBLOCK);
        }
    }
}
