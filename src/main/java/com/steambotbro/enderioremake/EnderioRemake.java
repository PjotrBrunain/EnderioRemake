package com.steambotbro.enderioremake;

import com.mojang.logging.LogUtils;
import com.steambotbro.enderioremake.block.ModBlocks;
import com.steambotbro.enderioremake.block.entity.ModBlockEntities;
import com.steambotbro.enderioremake.fluid.ModFluidTypes;
import com.steambotbro.enderioremake.fluid.ModFluids;
import com.steambotbro.enderioremake.item.ModCreativeModeTab;
import com.steambotbro.enderioremake.item.ModItems;
import com.steambotbro.enderioremake.networking.ModMessages;
import com.steambotbro.enderioremake.recipe.ModRecipes;
import com.steambotbro.enderioremake.screen.AlloySmelterScreen;
import com.steambotbro.enderioremake.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EnderioRemake.MOD_ID)
public class EnderioRemake
{
    public static final String MOD_ID = "enderioremake";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModCreativeModeTab MOD_CREATIVE_MODE_TAB = new ModCreativeModeTab();
    public EnderioRemake()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCreativeTab);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerCreativeTab(CreativeModeTabEvent.Register ev)
    {
        MOD_CREATIVE_MODE_TAB.register(ev);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            ModMessages.register();
        });
        // Some common setup code

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
           // ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_FIRE_WATER.get(), RenderType.translucent());
           // ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_FIRE_WATER.get(), RenderType.translucent());

            MenuScreens.register(ModMenuTypes.ALLOY_SMELTER_MENU.get(), AlloySmelterScreen::new);
        }
    }
}
