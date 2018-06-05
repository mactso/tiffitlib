package net.tiffit.tiffitlib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.tiffit.tiffitlib.generics.GenericContainer;
import net.tiffit.tiffitlib.generics.GenericGuiContainer;

public class GuiManager implements IGuiHandler{

	private List<GuiElement> GUI_REGISTRY = new ArrayList<GuiElement>();
	
	public void registerElement(GuiElement element){
		GUI_REGISTRY.add(element);
	}
	
	public static class GuiElement{
		private Class<? extends TileEntity> te;
		private Class<? extends GenericContainer> container;
		private Class<? extends GenericGuiContainer> gui;
		
		public GuiElement(Class<? extends TileEntity> te, Class<? extends GenericContainer> container, Class<? extends GenericGuiContainer> gui){
			this.te = te;
			this.container = container;
			this.gui = gui;
		}
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        for(GuiElement element : GUI_REGISTRY){
        	if(element.te.isInstance(te)){
        		try {
					return element.container.getConstructor(IInventory.class, TileEntity.class).newInstance(player.inventory, te);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        for(GuiElement element : GUI_REGISTRY){
        	if(element.te.isInstance(te)){
        		try {
        			GenericContainer cont = element.container.getConstructor(IInventory.class, TileEntity.class).newInstance(player.inventory, te);
					return element.gui.getConstructor(TileEntity.class, GenericContainer.class).newInstance(te, cont);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }
		return null;
	}

}
