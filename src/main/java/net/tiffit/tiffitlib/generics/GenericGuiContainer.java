package net.tiffit.tiffitlib.generics;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;

public abstract class GenericGuiContainer extends GuiContainer {

	private TileEntity te;
	
	public GenericGuiContainer(TileEntity te, GenericContainer container) {
		super(container);
		this.te = te;
	}

}
