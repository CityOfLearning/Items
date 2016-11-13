package com.dyn.fixins.items;

import java.awt.Color;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemOrb extends Item {
	public ItemOrb(final int par1) {
		super();
		setCreativeTab(CreativeTabs.tabMisc);
		setHasSubtypes(true);
	}

	@Override
	public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
		final float[] color = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(par1ItemStack.getItemDamage()));
		return new Color(color[0], color[1], color[2]).getRGB();
	}

	@Override
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
		for (int var4 = 0; var4 < 16; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}