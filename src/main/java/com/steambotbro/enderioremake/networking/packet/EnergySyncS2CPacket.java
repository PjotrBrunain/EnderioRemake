package com.steambotbro.enderioremake.networking.packet;

import com.steambotbro.enderioremake.block.entity.AlloySmelterBlockEntity;
import com.steambotbro.enderioremake.screen.AlloySmelterMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket {
    private final int energy;
    private final BlockPos pos;

    public EnergySyncS2CPacket(int energy, BlockPos pos)
    {
        this.energy = energy;
        this.pos = pos;
    }

    public EnergySyncS2CPacket(FriendlyByteBuf buf)
    {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof AlloySmelterBlockEntity blockEntity)
            {
                blockEntity.setEnergyLevel(energy);

                if (Minecraft.getInstance().player.containerMenu instanceof AlloySmelterMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos))
                {
                    blockEntity.setEnergyLevel(energy);
                }
            }
        });
        return true;
    }
}
