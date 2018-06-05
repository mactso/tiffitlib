package net.tiffit.tiffitlib;

import java.util.ArrayList;
import java.util.List;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.render.item.IItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

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
		for (Item i : registeredItems) {
			int maxMeta = 0;
			if (i instanceof IMultiMeta)
				maxMeta = ((IMultiMeta) i).getMaxMeta();
			for (int m = 0; m <= maxMeta; m++) {
				registerItemModel(i, m);
			}
		}
		for (Block b : registeredBlocks) {
			if (b instanceof ISpecialItemRender) {
				ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(b), ((ISpecialItemRender) b).getItemRender());
			} else {
				int maxMeta = 0;
				if (b instanceof IMultiMeta)
					maxMeta = ((IMultiMeta) b).getMaxMeta();
				ModelLoader.setCustomStateMapper(b, new StateMapperBase() {
					@Override
					protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
						int m = state.getBlock().getMetaFromState(state);
						return new ModelResourceLocation(state.getBlock().getRegistryName(), "inventory" + (m == 0 ? "" : m));
					}
				});
				for (int m = 0; m <= maxMeta; m++) {
					registerItemModel(Item.getItemFromBlock(b), m);
				}
			}
		}
	}

	private void registerItemModel(Item i, int m) {
		String variant = "inventory" + (m == 0 ? "" : m);
		ModelLoader.setCustomModelResourceLocation(i, m, new ModelResourceLocation(i.getRegistryName(), variant));
	}

	public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String key) {
		GameRegistry.registerTileEntity(tileEntityClass, modid + "_" + key);
	}

	public static interface ISpecialItemRender {
		public IItemRenderer getItemRender();
	}

	public static interface IMultiMeta {
		public int getMaxMeta();
	}

}
