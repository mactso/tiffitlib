package net.tiffit.tiffitlib;

import java.text.DecimalFormat;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.tiffit.tiffitlib.network.NetworkManager;
import net.tiffit.tiffitlib.proxy.CommonProxy;

@Mod(modid = TiffitLib.MODID, name = TiffitLib.NAME, version = TiffitLib.VERSION)
public class TiffitLib
{
    public static final String MODID = "tiffitlib";
    public static final String NAME = "TiffitLib";
    public static final String VERSION = "1.0.2";

    public static Logger logger;

	public static DecimalFormat LARGE_NUMBER = new DecimalFormat("#,###");

	@SidedProxy(clientSide = "net.tiffit.tiffitlib.proxy.ClientProxy", serverSide = "net.tiffit.tiffitlib.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Instance(MODID)
	public static TiffitLib INSTANCE;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        logger = e.getModLog();
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
    	NetworkManager.registerMessages();
    	proxy.init(e);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    	proxy.postInit(e);
    }
}
