package net.tiffit.tiffitlib.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkManager {
    private static int id = 0;

    public static SimpleNetworkWrapper NETWORK = null;
    
    public static final int BLOCK_RANGE = 192;
    
    public static int nextID() {
        return id++;
    }

    public static void registerMessages() {
    	NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("ChannelTiffit");
    	registerMessage(PacketCreateLightning.Handler.class, PacketCreateLightning.class, Side.CLIENT);
    	registerMessage(PacketUpdateContainer.Handler.class, PacketUpdateContainer.class, Side.CLIENT);
    	MinecraftForge.EVENT_BUS.post(new EventMessageRegister());
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side){
    	NETWORK.registerMessage(messageHandler, requestMessageType, nextID(), side);
    }
    
    public static TargetPoint getSyncTargetPoint(TileEntity entity){
    	return getSyncTargetPoint(entity.getPos(), entity.getWorld());
    }
    
    public static TargetPoint getSyncTargetPoint(BlockPos pos, World world){
    	return new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), NetworkManager.BLOCK_RANGE);
    }
}
