package net.tiffit.tiffitlib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.tiffit.tiffitlib.client.ClientRegistryHelper;

public class RegistryHelper {

	private final String modid;
	private IForgeRegistry<Block> regB;
	private IForgeRegistry<Item> regI;

	private List<Block> registeredBlocks = new ArrayList<Block>();
	private List<Item> registeredItems = new ArrayList<Item>();

	public RegistryHelper(String modid) {
		this.modid = modid;
	}

	public void setBlockRegistry(IForgeRegistry<Block> reg) {
		this.regB = reg;
	}

	public void setItemRegistry(IForgeRegistry<Item> reg) {
		this.regI = reg;
	}

	public void register(Block block) {
		regB.register(block);
		registeredBlocks.add(block);
	}

	public void register(Item item) {
		regI.register(item);
		registeredItems.add(item);
	}

	public void registerItemBlocks() {
		for (Block b : registeredBlocks) {
			int maxMeta = 0;
			if (b instanceof IMultiMeta)
				maxMeta = ((IMultiMeta) b).getMaxMeta();

			if (maxMeta > 0) {
				register(new ItemMultiTexture(b, b, new ItemMultiTexture.Mapper() {
					public String apply(ItemStack stack) {
						return "" + stack.getMetadata();
					}
				}).setRegistryName(b.getRegistryName()));
			} else {
				register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
			}
		}
	}

	public void registerModels() {
		ClientRegistryHelper.registerModels(registeredItems, registeredBlocks);
	}

	@Deprecated
	public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String key) {
		GameRegistry.registerTileEntity(tileEntityClass, key);
	}
	
	public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, ResourceLocation key) {
		GameRegistry.registerTileEntity(tileEntityClass, key);
	}

	public static interface IMultiMeta {
		public int getMaxMeta();
	}

}
