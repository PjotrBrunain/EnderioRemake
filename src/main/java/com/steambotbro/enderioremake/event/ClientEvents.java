package com.steambotbro.enderioremake.event;

import com.steambotbro.enderioremake.EnderioRemake;
import com.steambotbro.enderioremake.networking.ModMessages;
import com.steambotbro.enderioremake.networking.packet.ExampleC2SPacket;
import com.steambotbro.enderioremake.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents
{
    @Mod.EventBusSubscriber(modid = EnderioRemake.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents
    {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            if (KeyBinding.SPAWN_COW_KEY.consumeClick())
            {
                ModMessages.sendToServer(new ExampleC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = EnderioRemake.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBinding.SPAWN_COW_KEY);
        }
    }
}
