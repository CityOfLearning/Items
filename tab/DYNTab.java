package com.dyn.fixins.tab;

import com.dyn.fixins.items.DynItemManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class DYNTab extends CreativeTabs {

	public DYNTab() {
		super("dynit");
	}

	@Override
	public Item getTabIconItem() {
		return DynItemManager.dynLogo;
	}

}
