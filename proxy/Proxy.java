package com.dyn.item.proxy;

import com.dyn.item.blocks.cmdblock.StudentCommandBlockLogic;

import net.minecraft.item.Item;

public interface Proxy {
	public void init();

	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic);

	public void registerItem(Item item, String name, int meta);

	public void renderGUI();
}