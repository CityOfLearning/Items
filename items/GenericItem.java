package com.dyn.item.items;

import java.util.ArrayList;
import java.util.List;

import com.dyn.item.ItemMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GenericItem extends Item {

	public GenericItem() {
		setCreativeTab(CreativeTabs.tabMisc);
	}

	public GenericItem(int par1) {
		this();
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

	@Override
	public Item setUnlocalizedName(String name) {
		super.setUnlocalizedName(name);
		GameRegistry.registerItem(this, name);
		if (hasSubtypes) {
			List<ItemStack> list = new ArrayList<ItemStack>();
			getSubItems(this, null, list);
			for (ItemStack stack : list) {
				ItemMod.proxy.registerItem(this, name, stack.getItemDamage());
			}
		} else {
			ItemMod.proxy.registerItem(this, name, 0);
		}
		return this;
	}
}
