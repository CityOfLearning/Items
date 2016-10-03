package com.dyn.item.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FurnitureTab extends CreativeTabs {
	
	Item tab_item;
	
	public FurnitureTab(String label) {
		super(label);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(tab_item);
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}
	
	public void setTabIconItem(Item item){
		tab_item = item;
	}
}