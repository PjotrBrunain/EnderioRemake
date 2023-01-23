package com.steambotbro.enderioremake.item;

import com.steambotbro.enderioremake.EnderioRemake;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnderioRemake.MOD_ID);

    public static final RegistryObject<Item> BINDERCOMPOSITE = ITEMS.register("binder_composite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONDUITBINDER = ITEMS.register("conduit_binder", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONDUCTIVEIRON = ITEMS.register("conductive_iron", () -> new Item(new Item.Properties()));

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
            event.accept(CONDUCTIVEIRON);
        }
    }
}
