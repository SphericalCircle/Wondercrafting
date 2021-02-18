package com.ball.wondercrafting.core.util.packets;

import com.ball.wondercrafting.Wondercrafting;
import com.ball.wondercrafting.common.tileentity.WonderTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSendHungry {

    private static boolean hungry;
    private static int senderX;
    private static int senderY;
    private static int senderZ;

    public PacketSendHungry(boolean hungry, int senderX, int senderY, int senderZ) {
        this.hungry = hungry;
        this.senderX = senderX;
        this.senderY = senderY;
        this.senderZ = senderZ;
    }

    public void write(PacketBuffer buffer)
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("Hungry", hungry);
        nbt.putInt("SenderX", senderX);
        nbt.putInt("SenderY", senderY);
        nbt.putInt("SenderZ", senderZ);
        buffer.writeCompoundTag(nbt);
    }

    public static PacketSendHungry read(PacketBuffer buffer) {
        CompoundNBT nbt = buffer.readCompoundTag();
        if (nbt == null) {
            return new PacketSendHungry(hungry, senderX, senderY, senderZ);
        } else {
            boolean hungry = nbt.getBoolean("hungry");
            int senderX = nbt.getInt("senderX");
            int senderY = nbt.getInt("senderY");
            int senderZ = nbt.getInt("senderZ");

            return new PacketSendHungry(hungry, senderX, senderY, senderZ);
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        Wondercrafting.LOGGER.debug("Sending...");
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            ClientWorld world = mc.world;

            if (world != null)
            {
                BlockPos point = new BlockPos(senderX,senderY,senderZ);
                WonderTileEntity wonder = (WonderTileEntity) world.getTileEntity(point);
                if(wonder==null) Wondercrafting.LOGGER.debug("Nothing found");
                else Wondercrafting.LOGGER.debug("Found");
            }
            else Wondercrafting.LOGGER.debug("World is null");
        });
        Wondercrafting.LOGGER.debug("Sent!");
        context.setPacketHandled(true);
    }


}