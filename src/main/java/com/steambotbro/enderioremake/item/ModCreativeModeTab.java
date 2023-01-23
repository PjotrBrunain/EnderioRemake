package com.steambotbro.enderioremake.item;

import com.steambotbro.enderioremake.EnderioRemake;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;

public class ModCreativeModeTab {
    public static CreativeModeTab ENDERIOREMAKE_TAB = null;
    public void register(CreativeModeTabEvent.Register eventBus)
    {
        ENDERIOREMAKE_TAB = eventBus.registerCreativeModeTab(
                new ResourceLocation(EnderioRemake.MOD_ID, "enderioremake_tab"),
                builder -> builder.title(Component
                        .translatable("itemGroup.enderioremake"))
                        .icon(()-> new ItemStack(ModItems.CONDUCTIVEIRON.get()))
                );
    }
}
