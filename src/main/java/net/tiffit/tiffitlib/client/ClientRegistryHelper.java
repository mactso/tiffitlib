package net.tiffit.tiffitlib.client;

import java.util.HashMap;
import java.util.List;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.render.item.IItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.tiffit.tiffitlib.RegistryHelper.IMultiMeta;

public class ClientRegistryHelper {

	public static HashMap<Block, IItemRenderer> SPECIAL_RENDERS = new HashMap<Block, IItemRenderer>();
	
	public static void registerModels(List<Item> registeredItems, List<Block> registeredBlocks) {
		for (Item i : registeredItems) {
			int maxMeta = 0;
			if (i instanceof IMultiMeta)
				maxMeta = ((IMultiMeta) i).getMaxMeta();
			for (int m = 0; m <= maxMeta; m++) {
				registerItemModel(i, m);
			}
		}
		for (Block b : registeredBlocks) {
			if (SPECIAL_RENDERS.containsKey(b)) {
				ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(b), SPECIAL_RENDERS.get(b));
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
	
	private static void registerItemModel(Item i, int m) {
		String variant = "inventory" + (m == 0 ? "" : m);
		ModelLoader.setCustomModelResourceLocation(i, m, new ModelResourceLocation(i.getRegistryName(), variant));
	}
}
