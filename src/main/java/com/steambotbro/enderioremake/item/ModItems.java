package com.steambotbro.enderioremake.item;

import com.steambotbro.enderioremake.EnderioRemake;
import com.steambotbro.enderioremake.item.custom.YetaWrenchItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnderioRemake.MOD_ID);

    //crafting Items
    public static final RegistryObject<Item> BINDERCOMPOSITE = ITEMS.register("binder_composite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONDUITBINDER = ITEMS.register("conduit_binder", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONDUCTIVEIRONINGOT = ITEMS.register("conductive_iron", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONDUCTIVEIRONNUGGET = ITEMS.register("item_alloy_nugget_conductive_iron", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VIVIDALLOYNUGGET = ITEMS.register("item_alloy_nugget_vivid_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VIVIDALLOYINGOT = ITEMS.register("item_alloy_ingot_vivid_alloy", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VIBRANTALLOYNUGGET = ITEMS.register("item_alloy_nugget_vibrant_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VIBRANTALLOYINGOT = ITEMS.register("item_alloy_ingot_vibrant_alloy", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOULARIUMNUGGET = ITEMS.register("item_alloy_nugget_soularium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOULARIUMINGOT = ITEMS.register("item_alloy_ingot_soularium", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STELLARALLOYNUGGET = ITEMS.register("item_alloy_nugget_stellar_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STELLARALLOYINGOT = ITEMS.register("item_alloy_ingot_stellar_alloy", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> REDSTONEALLOYNUGGET = ITEMS.register("item_alloy_nugget_redstone_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REDSTONEALLOYINGOT = ITEMS.register("item_alloy_ingot_redstone_alloy", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PULSATINGIRONNUGGET = ITEMS.register("item_alloy_nugget_pulsating_iron", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PULSATINGIRONINGOT = ITEMS.register("item_alloy_ingot_pulsating_iron", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENERGETICSILVERNUGGET = ITEMS.register("item_alloy_nugget_energetic_silver", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENERGETICSILVERINGOT = ITEMS.register("item_alloy_ingot_energetic_silver", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MELODICALLOYNUGGET = ITEMS.register("item_alloy_nugget_melodic_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MELODICALLOYINGOT = ITEMS.register("item_alloy_ingot_melodic_alloy", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENDSTEELNUGGET = ITEMS.register("item_alloy_nugget_end_steel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENDSTEELINGOT = ITEMS.register("item_alloy_ingot_end_steel", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELECTRICALSTEELNUGGET = ITEMS.register("item_alloy_nugget_electrical_steel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ELECTRICALSTEELINGOT = ITEMS.register("item_alloy_ingot_electrical_steel", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DARKSTEELNUGGET = ITEMS.register("item_alloy_nugget_dark_steel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DARKSTEELINGOT = ITEMS.register("item_alloy_ingot_dark_steel", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CRYSTALLINEPINKSLIMENUGGET = ITEMS.register("item_alloy_nugget_crystalline_pink_slime", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALLINEPINKSLIMEINGOT = ITEMS.register("item_alloy_ingot_crystalline_pink_slime", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CRYSTALLINEALLOYNUGGET = ITEMS.register("item_alloy_nugget_crystalline_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALLINEALLOYINGOT = ITEMS.register("item_alloy_ingot_crystalline_alloy", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CRUDESTEELNUGGET = ITEMS.register("item_alloy_nugget_crude_steel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRUDESTEELINGOT = ITEMS.register("item_alloy_ingot_crude_steel", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CONSTRUCTIONALLOYNUGGET = ITEMS.register("item_alloy_nugget_construction_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONSTRUCTIONALLOYINGOT = ITEMS.register("item_alloy_ingot_construction_alloy", () -> new Item(new Item.Properties()));

    //Custom Items
    public static final RegistryObject<Item> YETA_WRENCH = ITEMS.register("yeta_wrench", () -> new YetaWrenchItem(new Item.Properties()));
    //public static final RegistryObject<Item> FIRE_WATER_BUCKET = ITEMS.register("fire_water_bucket", () -> new BucketItem(ModFluids.SOURCE_FIRE_WATER ,new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);

        eventBus.addListener(ModItems::addCreative);
    }

    private static void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        if (event.getTab() == ModCreativeModeTab.ENDERIOREMAKE_TAB)
        {
            event.accept(BINDERCOMPOSITE);
            event.accept(CONDUITBINDER);
            event.accept(CONDUCTIVEIRONINGOT);
            event.accept(YETA_WRENCH);
            event.accept(CONDUCTIVEIRONNUGGET);
            event.accept(VIVIDALLOYNUGGET);
            event.accept(VIVIDALLOYINGOT);
            event.accept(VIBRANTALLOYNUGGET);
            event.accept(VIBRANTALLOYINGOT);
            event.accept(SOULARIUMNUGGET);
            event.accept(SOULARIUMINGOT);
            event.accept(STELLARALLOYNUGGET);
            event.accept(STELLARALLOYINGOT);
            event.accept(REDSTONEALLOYNUGGET);
            event.accept(REDSTONEALLOYINGOT);
            event.accept(PULSATINGIRONNUGGET);
            event.accept(PULSATINGIRONINGOT);
            event.accept(ENERGETICSILVERNUGGET);
            event.accept(ENERGETICSILVERINGOT);
            event.accept(MELODICALLOYNUGGET);
            event.accept(MELODICALLOYINGOT);
            event.accept(ENDSTEELNUGGET);
            event.accept(ENDSTEELINGOT);
            event.accept(ELECTRICALSTEELNUGGET);
            event.accept(ELECTRICALSTEELINGOT);
            event.accept(DARKSTEELNUGGET);
            event.accept(DARKSTEELINGOT);
            event.accept(CRYSTALLINEPINKSLIMENUGGET);
            event.accept(CRYSTALLINEPINKSLIMEINGOT);
            event.accept(CRYSTALLINEALLOYNUGGET);
            event.accept(CRYSTALLINEALLOYINGOT);
            event.accept(CRUDESTEELNUGGET);
            event.accept(CRUDESTEELINGOT);
            event.accept(CONSTRUCTIONALLOYNUGGET);
            event.accept(CONSTRUCTIONALLOYINGOT);
        }
    }
}
