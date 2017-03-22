package com.dyn.fixins.proxy;

import com.dyn.fixins.blocks.cmdblock.StudentCommandBlockLogic;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface Proxy {
	public void init();

	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic);

	public void preInit();

	public void registerBlockItem(Block block);

	public void registerItem(Item item, String name);

	public void registerItem(Item item, String name, int meta);

	public void renderGUI();
}