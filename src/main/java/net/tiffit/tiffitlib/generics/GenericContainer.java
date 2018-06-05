package net.tiffit.tiffitlib.generics;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.tiffit.tiffitlib.network.NetworkManager;
import net.tiffit.tiffitlib.network.PacketUpdateContainer;

public abstract class GenericContainer extends Container {

	protected IInventory playerInventory;
	protected TileEntity te;
	protected NBTTagCompound oldNBT = new NBTTagCompound();

	public GenericContainer(IInventory playerInventory, TileEntity te) {
		this.playerInventory = playerInventory;
		this.te = te;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (!oldNBT.equals(getNBT())) {
			oldNBT = getNBT();
			for (int i = 0; i < this.listeners.size(); ++i) {
				IContainerListener icontainerlistener = this.listeners.get(i);
				EntityPlayerMP mp = (EntityPlayerMP) icontainerlistener;
				NetworkManager.NETWORK.sendTo(new PacketUpdateContainer(getNBT()), mp);
			}
		}
	}

	protected void addPlayerSlots(IInventory playerInventory) {
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				int x = 8 + col * 18;
				int y = row * 18 + 84;
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
			}
		}
		for (int row = 0; row < 9; ++row) {
			int x = 8 + row * 18;
			int y = 58 + 84;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

	public NBTTagCompound getNBT() {
		return new NBTTagCompound();
	}

	public void readNBT(NBTTagCompound tag) {

	}

}
