package com.dyn.fixins.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GenericItem extends Item {

	public GenericItem() {
		setCreativeTab(CreativeTabs.tabMisc);
	}

	public boolean consumeItem(EntityPlayer player, Item item) {
		return player.inventory.consumeInventoryItem(item);
	}

	@Override
	public int getItemEnchantability() {
		return super.getItemEnchantability();
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(itemIn, 1, 0));
	}

	public boolean hasItem(EntityPlayer player, Item item) {
		return player.inventory.hasItem(item);
	}
}
