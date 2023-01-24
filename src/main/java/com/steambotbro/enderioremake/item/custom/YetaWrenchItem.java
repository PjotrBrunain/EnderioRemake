package com.steambotbro.enderioremake.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class YetaWrenchItem extends Item
{

    public YetaWrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.isCrouching())
        {
            player.sendSystemMessage(Component.literal("Test!"));
        }
        return super.use(level, player, hand);
    }
}
