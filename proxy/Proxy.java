package com.dyn.item.proxy;

import com.dyn.item.blocks.cmdblock.StudentCommandBlockLogic;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public interface Proxy {
	public void init();

	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic);

	public void registerBlock(final Block block, final String name, final int meta,
			final Class<? extends ItemBlock> itemclass);

	public void registerBlock(final Block block, final String name, final int meta,
			final Class<? extends ItemBlock> itemclass, final boolean seperateMetadata);

	public void registerBlockItem(Block block);

	public void registerItem(Item item, String name, int meta);

	public void renderGUI();
	
	public void preInit();
}