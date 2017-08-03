package com.dyn.fixins.proxy;

import com.dyn.fixins.blocks.cmdblock.StudentCommandBlockLogic;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Server implements Proxy {

	@Override
	public void init() {

	}

	@Override
	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preInit() {

	}

	@Override
	public void registerBlockItem(Block block) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerItem(Item item, String name) {
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);
	}

	@Override
	public void registerItemModels(Item item, String name, int meta) {
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Actions on render GUI for the server (logging)

	}

}