package com.dyn.item.items;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Flags extends Item {
	// public static String[] types = new String[] { "red", "green",
	// "blue", "yellow" };

	public Flags() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("flags");
		// this.setTextureName("dyn:ctf_flags");
	}

	/**
	 * Gets an icon index based on an item's damage value
	 *//*
		 * @SideOnly(Side.CLIENT) public IIcon getIconFromDamage(int dmg) { int
		 * j = MathHelper.clamp_int(dmg, 0, this.types.length-1); return
		 * this.icons[j]; }
		 */

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		int i = MathHelper.clamp_int(itemStack.getItemDamage(), 0, FlagTypes.values().length - 1);
		// return WordUtils.capitalize(this.types[i] + " flag");
		return WordUtils.capitalize(FlagTypes.byFlagDamage(i).getName() + " flag");
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		for (int i = 0; i < FlagTypes.values().length; ++i) {
			itemList.add(new ItemStack(item, 1, i));
		}
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		int i = MathHelper.clamp_int(itemStack.getMetadata(), 0, FlagTypes.values().length - 1);
		return super.getUnlocalizedName() + "_" + FlagTypes.byFlagDamage(i).getUnlocalizedName();
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		return itemStack;
	}

	/*
	 * @Override
	 *
	 * @SideOnly(Side.CLIENT) public void registerIcons(IIconRegister
	 * iconRegistry) { this.icons = new IIcon[this.types.length];
	 *
	 * for (int i = 0; i < this.types.length; ++i) { this.icons[i] =
	 * iconRegistry.registerIcon(this.getIconString() + "_" + this.types[i]); }
	 * }
	 */
}
